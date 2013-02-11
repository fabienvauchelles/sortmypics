/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.vaushell.smp.model.MFile;
import com.vaushell.smp.model.MLatitudeCache;
import com.vaushell.smp.model.MPlace;
import com.vaushell.smp.model.MPlaceCache;
import com.vaushell.tools.geo.GeoReverse;
import com.vaushell.tools.latitude.GLatitude;
import com.vaushell.tools.md5.MD5tools;
import com.vaushell.tools.mediainfo.MediaInfoExtract;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.h2.jdbc.JdbcSQLException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class FilesControllerDAO
{
    // PUBLIC
    public final static double NEAR_IN_METERS = 50.0;

    public FilesControllerDAO()
    {
        init();
    }

    public void setDatabaseFile( File databaseFile )
    {
        this.databaseFile = databaseFile;
    }

    public void setDatabaseFileTemplate( File databaseFileTemplate )
    {
        this.databaseFileTemplate = databaseFileTemplate;
    }

    public void start()
            throws IOException
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[FilesControllerDAO] Start database connection" );
        }

        File databaseFileDest = new File( databaseFile ,
                                          "/base.h2.db" );
        if ( !databaseFileDest.exists() )
        {
            File databaseFileSource = new File( databaseFileTemplate ,
                                                "/base.h2.db" );
            FileUtils.copyFile( databaseFileSource ,
                                databaseFileDest );
        }

        // Create the SessionFactory from standard (hibernate.cfg.xml) 
        // config file.

        Configuration config = new Configuration();

        Properties props = new Properties();
        props.put( "hibernate.connection.url" ,
                   "jdbc:h2:" + databaseFile.getAbsolutePath() + "/base" );

        config.addProperties( props );

        factory = config.configure().buildSessionFactory();

        try
        {
            factory.getCurrentSession().beginTransaction();
            factory.getCurrentSession().getTransaction().rollback();
        }
        catch( GenericJDBCException ex )
        {
            if ( ex.getCause() instanceof JdbcSQLException )
            {
                logger.error( "Database already connected" );
            }
        }
    }

    public void stop()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[FilesControllerDAO] Stop database connection" );
        }

        if ( factory != null )
        {
            factory.close();
        }
    }

    public void clear()
    {
        this.tmpFiles.clear();
        this.lastFile = null;
    }

    public void createPlaceIfNecessary( String location ,
                                        double latitude ,
                                        double longitude )
    {
        if ( location == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.
                    debug(
                    "[FilesControllerDAO] createPlaceIfNecessary() : location=" + location + " / latitude=" + latitude + " / longitude=" + longitude );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {

            Query q = factory.getCurrentSession().createQuery(
                    "select count(p) from MPlace p where p.latitude=:latitude and p.longitude=:longitude" );
            q.setDouble( "latitude" ,
                         latitude );
            q.setDouble( "longitude" ,
                         longitude );

            Long count = (Long) q.uniqueResult();
            if ( count <= 0 )
            {
                MPlace p = new MPlace( UUID.randomUUID().toString() ,
                                       location ,
                                       latitude ,
                                       longitude );

                factory.getCurrentSession().save( p );
            }

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    public void createFileIfNecessary( File file ,
                                       boolean update )
            throws IOException , NoSuchAlgorithmException
    {
        if ( file == null )
        {
            throw new NullPointerException();
        }

        if ( !file.exists() )
        {
            throw new FileNotFoundException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] createFileIfNecessary() : file=" + file + " / update=" + update );
        }

        MFile mf = getFile( file );
        if ( mf != null )
        {
            lastFile = mf;
            return;
        }

        // MD5
        String md5hash = MD5tools.md5sum( file );

        if ( update )
        {
            mf = getFileByMD5( md5hash ,
                               file );
            if ( mf != null )
            {
                lastFile = mf;
                return;
            }
        }
        else
        {
            mf = getFileByMD5( md5hash ,
                               null );
            if ( mf != null )
            {
                lastFile = mf;

                logger.info( "'" + file.getName() + "' already exists" );

                return;
            }
        }

        // Create
        mf = new MFile();
        mf.setMd5hash( md5hash );
        mf.setFile( file );
        mf.setAdded( Calendar.getInstance() );

        switch( mf.getType() )
        {
            case MFile.FILETYPE_PICTURE:
            {
                updatePicture( mf );
                break;
            }

            case MFile.FILETYPE_MOVIE:
            {
                updateMovie( mf );
                break;
            }

            case MFile.FILETYPE_OTHER:
            {
                break;
            }

            default:
            {
                throw new UnsupportedOperationException();
            }
        }

        if ( mf.getCreated() == null )
        {
            if ( lastFile != null )
            {
                mf.setCreated( lastFile.getCreated() );
                mf.setLatitude( lastFile.getLatitude() );
                mf.setLongitude( lastFile.getLongitude() );

                saveFile( mf );
                lastFile = mf;
            }
            else
            {
                tmpFiles.add( mf );

                logger.info( "Stacking '" + file.getName() + "'" );
            }
        }
        else
        {
            for ( MFile oFile : tmpFiles )
            {
                oFile.setCreated( mf.getCreated() );
                oFile.setLatitude( mf.getLatitude() );
                oFile.setLongitude( mf.getLongitude() );

                saveFile( oFile );
            }
            tmpFiles.clear();

            saveFile( mf );
            lastFile = mf;
        }
    }

    public void update( List<MFile> updated )
    {
        if ( updated == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] update() : updated.size=" + updated.size() );
        }

        if ( updated.isEmpty() )
        {
            return;
        }

        factory.getCurrentSession().beginTransaction();

        try
        {
            for ( MFile file : updated )
            {
                factory.getCurrentSession().update( file );
            }

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    public boolean hasStack()
    {
        return tmpFiles.size() > 0;
    }

    public void clean()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] clean()" );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {
            factory.getCurrentSession().createSQLQuery( "DELETE FROM MGROUP" ).executeUpdate();
            factory.getCurrentSession().createSQLQuery( "DELETE FROM MFILE" ).executeUpdate();
            factory.getCurrentSession().createSQLQuery( "DELETE FROM MPLACE" ).executeUpdate();
            factory.getCurrentSession().createSQLQuery( "DELETE FROM MPLACE_CACHE" ).executeUpdate();
            factory.getCurrentSession().createSQLQuery( "DELETE FROM MLATITUDE_CACHE" ).executeUpdate();

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    public List<MFile> getAllLocation()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] getAllLocation()" );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {

            Query q = factory.getCurrentSession().createQuery(
                    "select f from MFile f where f.latitude is not null and f.longitude is not null order by f.created" );

            List<MFile> result = q.list();

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return result;
        }
        catch( NonUniqueResultException ex )
        {
            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return null;
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    public GeoReverse.GeoData convertAddressToGeo( String address )
            throws IOException , org.json.simple.parser.ParseException
    {
        if ( address == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.
                    debug(
                    "[FilesControllerDAO] convertAddressToGeo() : address=" + address );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {

            Query q = factory.getCurrentSession().createQuery(
                    "select p from MPlaceCache p where p.location=:location" );
            q.setString( "location" ,
                         address );

            GeoReverse.GeoData gd;

            List<MPlaceCache> places = q.list();
            if ( places.size() > 0 )
            {
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "[FilesControllerDAO] return cached data" );
                }

                MPlaceCache place = places.get( 0 );
                gd = new GeoReverse.GeoData( place.getLatitude() ,
                                             place.getLongitude() );
            }
            else
            {
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "[FilesControllerDAO] request google map" );
                }

                gd = GeoReverse.getInstance().convertAddressToGeo( address );
                if ( gd != null )
                {
                    MPlaceCache place = new MPlaceCache( UUID.randomUUID().toString() ,
                                                         address ,
                                                         gd.getLatitude() ,
                                                         gd.getLongitude() );

                    factory.getCurrentSession().save( place );
                }
            }

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return gd;
        }
        catch( org.json.simple.parser.ParseException ex )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw ex;
        }
        catch( IOException ex )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw ex;
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    public String convertGeoToAddress( GeoReverse.GeoData gd )
            throws org.json.simple.parser.ParseException , IOException
    {
        if ( gd == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.
                    debug(
                    "[FilesControllerDAO] convertGeoToAddress() : gd=" + gd );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {

            Query q = factory.getCurrentSession().createQuery(
                    "select p from MPlaceCache p where p.latitude=:latitude and p.longitude=:longitude" );
            q.setDouble( "latitude" ,
                         gd.getLatitude() );
            q.setDouble( "longitude" ,
                         gd.getLongitude() );

            String location;

            List<MPlaceCache> places = q.list();
            if ( places.size() > 0 )
            {
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "[FilesControllerDAO] return cached data" );
                }

                MPlaceCache place = places.get( 0 );
                location = place.getLocation();
            }
            else
            {
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "[FilesControllerDAO] request google map" );
                }

                location = GeoReverse.getInstance().convertGeoToAddress( gd );
                if ( location != null )
                {
                    MPlaceCache place = new MPlaceCache( UUID.randomUUID().toString() ,
                                                         location ,
                                                         gd.getLatitude() ,
                                                         gd.getLongitude() );

                    factory.getCurrentSession().save( place );
                }
            }

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return location;
        }
        catch( org.json.simple.parser.ParseException ex )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw ex;
        }
        catch( IOException ex )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw ex;
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    public List<GLatitude.GLocation> getAllLocations( Calendar minCal )
            throws IOException
    {
        if ( minCal == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.
                    debug(
                    "[FilesControllerDAO] getAllLocations() : minCal=" + minCal );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {
            // Create bound
            Calendar maxCal = (Calendar) minCal.clone();
            maxCal.add( Calendar.MINUTE ,
                        GLatitude.MINMAX_DURATION_IN_MINUTES );

            Query qML = factory.getCurrentSession().createQuery(
                    "select l from MLatitudeCache l where l.calendar>=:mincal and l.calendar<:maxcal" );
            qML.setCalendar( "mincal" ,
                             minCal );
            qML.setCalendar( "maxcal" ,
                             maxCal );
            List<MLatitudeCache> mls = qML.list();

            List<GLatitude.GLocation> locations;
            if ( mls.size() > 0 )
            {
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "[FilesControllerDAO] return cached data" );
                }

                locations = new ArrayList<GLatitude.GLocation>();

                for ( MLatitudeCache ml : mls )
                {
                    if ( ml.getLatitude() != null && ml.getLongitude() != null )
                    {
                        locations.add( new GLatitude.GLocation( ml.getLatitude() ,
                                                                ml.getLongitude() ,
                                                                ml.getAccuracy() ,
                                                                ml.getCalendar() ) );
                    }
                }
            }
            else
            {
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "[FilesControllerDAO] request google latitude" );
                }

                locations = GLatitude.getInstance().getAllLocations( null ,
                                                                     minCal ,
                                                                     maxCal );

                if ( locations.size() > 0 )
                {
                    for ( GLatitude.GLocation location : locations )
                    {
                        Query qMLadd = factory.getCurrentSession().createQuery(
                                "select count(l) from MLatitudeCache l where l.calendar=:calendar" );
                        qMLadd.setCalendar( "calendar" ,
                                            location.getCalendar() );
                        Long count = (Long) qMLadd.uniqueResult();
                        if ( count <= 0 )
                        {
                            factory.getCurrentSession().save( new MLatitudeCache( UUID.randomUUID().toString() ,
                                                                                  location.getCalendar() ,
                                                                                  location.getLatitude() ,
                                                                                  location.getLongitude() ,
                                                                                  location.getAccuracy() ) );
                        }
                    }
                }
                else
                {
                    factory.getCurrentSession().save( new MLatitudeCache( UUID.randomUUID().toString() ,
                                                                          minCal ,
                                                                          null ,
                                                                          null ,
                                                                          null ) );

                    factory.getCurrentSession().save( new MLatitudeCache( UUID.randomUUID().toString() ,
                                                                          maxCal ,
                                                                          null ,
                                                                          null ,
                                                                          null ) );
                }
            }

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return locations;
        }
        catch( IOException ex )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw ex;
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    public List<MFile> getAllFilesOrderbyCalendar()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] getAllFilesOrderbyCalendar()" );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {

            Query q = factory.getCurrentSession().createQuery(
                    "select f from MFile f order by f.created" );

            List<MFile> files = q.list();

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return files;
        }
        catch( NonUniqueResultException ex )
        {
            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return null;
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    public void matchPlaces()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] matchPlace()" );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {
            Query qPlaces = factory.getCurrentSession().createQuery( "from MPlace p" );
            List<MPlace> places = qPlaces.list();

            Query qFiles = factory.getCurrentSession().createQuery(
                    "from MFile f where f.place is null and f.latitude is not null and f.longitude is not null" );
            List<MFile> files = qFiles.list();

            for ( MFile file : files )
            {
                MPlace best = null;
                double bestDistance = Double.MAX_VALUE;

                for ( MPlace place : places )
                {
                    double distance = GeoReverse.distance( place.getLatitude() ,
                                                           place.getLongitude() ,
                                                           file.getLatitude() ,
                                                           file.getLongitude() );
                    if ( distance <= NEAR_IN_METERS )
                    {
                        if ( distance < bestDistance )
                        {
                            best = place;
                            bestDistance = distance;
                        }
                    }
                }

                if ( best != null )
                {
                    file.setPlace( best );
                    factory.getCurrentSession().save( best );
                }
            }

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }
    public void addPlaceForFiles( MPlace place ,
                                  List<MFile> files )
    {
        if ( place == null || files == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] addPlace() : place=" + place );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {
            factory.getCurrentSession().save( place );

            for ( MFile file : files )
            {
                factory.getCurrentSession().update( file );
            }

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( FilesControllerDAO.class );
    private File databaseFile;
    private File databaseFileTemplate;
    private SessionFactory factory;
    private List<MFile> tmpFiles;
    private MFile lastFile;

    private void init()
    {
        this.databaseFile = null;
        this.databaseFileTemplate = null;
        this.factory = null;

        this.tmpFiles = new ArrayList<MFile>();
        this.lastFile = null;
    }

    private MFile getFile( File file )
    {
        if ( file == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] getFile() : file=" + file );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {

            Query q = factory.getCurrentSession().createQuery(
                    "select f from MFile f where f.path=:path and f.name=:name and f.extension=:extension and f.size=:size" );
            q.setString( "path" ,
                         file.getParent() );
            q.setString( "name" ,
                         FilenameUtils.getBaseName( file.getName() ) );
            q.setString( "extension" ,
                         FilenameUtils.getExtension( file.getName() ) );
            q.setLong( "size" ,
                       file.length() );

            MFile mf = (MFile) q.uniqueResult();

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return mf;
        }
        catch( NonUniqueResultException ex )
        {
            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return null;
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    private MFile getFileByMD5( String md5hash ,
                                File file )
    {
        if ( md5hash == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] getAndUpdateFileByMD5() : md5hash=" + md5hash + " / file=" + file );
        }

        factory.getCurrentSession().beginTransaction();

        try
        {

            Query q = factory.getCurrentSession().createQuery(
                    "select f from MFile f where f.md5hash=:md5hash" );
            q.setString( "md5hash" ,
                         md5hash );

            MFile mf = (MFile) q.uniqueResult();
            if ( mf != null )
            {
                if ( file != null )
                {
                    mf.setFile( file );

                    normalize( mf );

                    factory.getCurrentSession().update( mf );
                }
            }

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return mf;
        }
        catch( NonUniqueResultException ex )
        {
            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();

            return null;
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    private void updatePicture( MFile mf )
            throws IOException
    {
        if ( mf == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] updatePicture() : mf=" + mf );
        }

        // EXIF and GPS
        try
        {
            Metadata md = ImageMetadataReader.readMetadata( mf.getFile() );

            ExifSubIFDDirectory dirExifSubIF = md.getDirectory( ExifSubIFDDirectory.class );
            if ( dirExifSubIF != null )
            {
                if ( dirExifSubIF.containsTag( ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL ) )
                {
                    if ( !"0000:00:00 00:00:00".equals( dirExifSubIF.getDescription( ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL ) ) )
                    {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime( dirExifSubIF.getDate( ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL ) );
                        mf.setCreated( cal );
                    }
                }
            }

            ExifIFD0Directory dirExifIFD0 = md.getDirectory( ExifIFD0Directory.class );
            if ( dirExifIFD0 != null )
            {
                if ( dirExifIFD0.containsTag( ExifIFD0Directory.TAG_MAKE ) )
                {
                    if ( dirExifIFD0.containsTag( ExifIFD0Directory.TAG_MODEL ) )
                    {
                        mf.setCamera( dirExifIFD0.getString( ExifIFD0Directory.TAG_MAKE ) + " / " + dirExifIFD0.
                                getString( ExifIFD0Directory.TAG_MODEL ) );
                    }
                    else
                    {
                        mf.setCamera( dirExifIFD0.getString( ExifIFD0Directory.TAG_MAKE ) );
                    }
                }
                else
                {
                    if ( dirExifIFD0.containsTag( ExifIFD0Directory.TAG_MODEL ) )
                    {
                        mf.setCamera( dirExifIFD0.getString( ExifIFD0Directory.TAG_MODEL ) );
                    }
                }
            }

            GpsDirectory dirGPS = md.getDirectory( GpsDirectory.class );
            if ( dirGPS != null )
            {
                GeoLocation location = dirGPS.getGeoLocation();
                if ( location != null )
                {
                    mf.setLatitude( location.getLatitude() );
                    mf.setLongitude( location.getLongitude() );
                }
            }
        }
        catch( ImageProcessingException ex )
        {
        }
    }

    private void updateMovie( MFile mf )
            throws IOException
    {
        if ( mf == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] updateMovie() : mf=" + mf );
        }

        Map<String , String> tags = MediaInfoExtract.getInstance().extract( mf.getFile() );

        String camera = tags.get( "Movie name/More" );
        if ( camera == null || camera.length() <= 0 )
        {
            camera = tags.get( "Writing application" );
            if ( camera == null || camera.length() <= 0 )
            {
                camera = tags.get( "Writing library" );
            }
        }

        if ( camera != null )
        {
            mf.setCamera( camera );
        }

        String dateStr = tags.get( "Tagged date" );
        if ( dateStr == null || dateStr.length() <= 0 )
        {
            dateStr = tags.get( "Encoded date" );
            if ( dateStr == null || dateStr.length() <= 0 )
            {
                dateStr = tags.get( "Mastered date" );
            }
        }

        Calendar cal = parse( dateStr );
        if ( cal != null )
        {
            mf.setCreated( cal );
        }
    }

    private void saveFile( MFile mf )
    {
        if ( mf == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug(
                    "[FilesControllerDAO] saveFile() : mf=" + mf );
        }

        normalize( mf );

        factory.getCurrentSession().beginTransaction();

        try
        {
            factory.getCurrentSession().save( mf );

            factory.getCurrentSession().flush();
            factory.getCurrentSession().getTransaction().commit();
        }
        catch( RuntimeException th )
        {
            factory.getCurrentSession().getTransaction().rollback();

            throw th;
        }
    }

    private void normalize( MFile mf )
    {
        if ( mf == null )
        {
            return;
        }

        if ( mf.getCamera() != null && mf.getCamera().length() <= 0 )
        {
            mf.setCamera( null );
        }

        if ( mf.getLatitude() != null && mf.getLatitude().isNaN() )
        {
            mf.setLatitude( null );
        }

        if ( mf.getLongitude() != null && mf.getLongitude().isNaN() )
        {
            mf.setLongitude( null );
        }
    }

    private static Calendar parse( String dateStr )
    {
        if ( dateStr == null )
        {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

        int ind = dateStr.indexOf( "UTC " );

        String rDateStr;
        if ( ind >= 0 )
        {
            TimeZone tz = TimeZone.getTimeZone( "UTC" );
            df.setTimeZone( tz );
            cal.setTimeZone( tz );

            rDateStr = dateStr.substring( 4 );
        }
        else
        {
            rDateStr = dateStr;
        }

        try
        {
            cal.setTime( df.parse( rDateStr ) );
            return cal;
        }
        catch( ParseException ex )
        {
            return null;
        }
    }
}
