/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.imp;

import com.vaushell.rc.thread.A_Task;
import com.vaushell.smp.MainJFrame;
import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Description;
import com.vaushell.smp.model.Listing;
import com.vaushell.smp.model.Place;
import com.vaushell.tools.exiftool.ExifTool;
import com.vaushell.tools.exiftool.ExifToolManager;
import com.vaushell.tools.md5.MD5tools;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class ImportPictureTask
        extends A_Task
{
    // PUBLIC
    public ImportPictureTask( File target ,
                              Listing listing ,
                              MainJFrame main )
    {
        super( "Import picture '" + target.getAbsolutePath() + "' for listing '" + listing.getName() + "'" );

        this.target = target;
        this.listing = listing;
        this.main = main;

        init();
    }

    @Override
    public void run()
    {
        try
        {
            String md5hash = MD5tools.md5sum( target.getAbsoluteFile() );

            Description d = ContentDAOmanager.getInstance().getDescriptionByID( md5hash );
            if ( d == null )
            {
                ExifTool tool = ExifToolManager.getInstance().borrowTool();

                Map<String , String> exifMap = tool.getAllMetas( target );

                ExifToolManager.getInstance().returnTool( tool );

                d = new Description();
                d.setID( md5hash );
                d.setType( Description.TYPE_PICTURE );
                updateFromExif( d ,
                                exifMap );

                ContentDAOmanager.getInstance().addDescription( d );

                Place place = new Place( target.getAbsolutePath() ,
                                         d ,
                                         listing );
                ContentDAOmanager.getInstance().addPlace( place );

                main.addPlace( place );
            }
            else
            {
                Place place = ContentDAOmanager.getInstance().getPlaceByPathAndDescription( target.getAbsolutePath() ,
                                                                                            d );
                if ( place == null )
                {
                    place = new Place( target.getAbsolutePath() ,
                                       d ,
                                       listing );

                    ContentDAOmanager.getInstance().addPlace( place );

                    main.addPlace( place );
                }

            }


        }
        catch( IOException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }

    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( ImportPictureTask.class );
    private File target;
    private Listing listing;
    private MainJFrame main;

    private void init()
    {
    }

    public void updateFromExif( Description d ,
                                Map<String , String> exifMap )
    {
        if ( d == null || exifMap == null )
        {
            throw new NullPointerException();
        }

        // CreatedDate
        String dateTimeCreatedStr = exifMap.get( "DateTimeCreated" );
        if ( dateTimeCreatedStr != null )
        {
            try
            {
                SimpleDateFormat sf = new SimpleDateFormat( "yyyy:MM:dd HH:mm:ss" );

                Calendar c = Calendar.getInstance();
                c.setTime( sf.parse( dateTimeCreatedStr ) );

                d.setCreatedDate( c );
            }
            catch( ParseException ex )
            {
            }
        }

        // Title
        String title = exifMap.get( "Title" );
        if ( title != null )
        {
            d.setTitle( title );
        }

        // Description
        String caption = exifMap.get( "Description" );
        if ( caption != null )
        {
            d.setCaption( caption );
        }

        // GPS altitude
        String gpsAltitudeStr = exifMap.get( "GPSAltitude" );
        if ( gpsAltitudeStr != null )
        {
            try
            {
                d.setGPSalt( Double.parseDouble( gpsAltitudeStr ) );
            }
            catch( NumberFormatException ex )
            {
            }
        }

        // GPS latitude
        String gpsLatitudeStr = exifMap.get( "GPSLatitude" );
        if ( gpsLatitudeStr != null )
        {
            try
            {
                d.setGPSlat( Double.parseDouble( gpsLatitudeStr ) );
            }
            catch( NumberFormatException ex )
            {
            }
        }

        // GPS longitude
        String gpsLongitudeStr = exifMap.get( "GPSLongitude" );
        if ( gpsLongitudeStr != null )
        {
            try
            {
                d.setGPSlng( Double.parseDouble( gpsLongitudeStr ) );
            }
            catch( NumberFormatException ex )
            {
            }
        }

        // Country
        String country = exifMap.get( "Country" );
        if ( country != null )
        {
            d.setLocCountry( country );
        }

        // City
        String city = exifMap.get( "City" );
        if ( city != null )
        {
            d.setLocCity( city );
        }

        // Make
        String make = exifMap.get( "Make" );
        if ( make != null )
        {
            d.setMake( make );
        }

        // Model
        String model = exifMap.get( "Model" );
        if ( model != null )
        {
            d.setModel( model );
        }
    }
}
