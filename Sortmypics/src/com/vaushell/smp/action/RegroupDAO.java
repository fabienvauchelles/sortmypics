/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.action;

import com.vaushell.smp.FilesControllerDAO;
import com.vaushell.smp.model.MFile;
import com.vaushell.smp.model.MPlace;
import com.vaushell.tools.geo.GeoReverse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class RegroupDAO
{
    // PUBLIC
    public RegroupDAO()
    {
        init();
    }

    public void setDAO( FilesControllerDAO dao )
    {
        this.dao = dao;
    }

    /**
     *
     * @param roundDistance in meters
     */
    public void setRoundDistance( Double roundDistance )
    {
        this.roundDistance = roundDistance;
    }

    public void run()
            throws FileNotFoundException
    {
        if ( roundDistance == null )
        {
            throw new NullPointerException();
        }

        try
        {
            logger.info( "[RegroupDAO] begin" );

            if ( logger.isDebugEnabled() )
            {
                logger.debug( "[RegroupDAO] retreive all locations" );
            }
            List<MFile> locations = dao.getAllFilesWithLocationOrderbyCalendar();

            // Create groups
            if ( logger.isDebugEnabled() )
            {
                logger.debug( "[RegroupDAO] create group" );
            }

            groups.clear();
            for ( MFile location : locations )
            {
                List<GroupDistance> groupToMerge = new ArrayList<GroupDistance>();
                for ( GroupDistance group : groups )
                {
                    for ( MFile groupLocation : group.files )
                    {
                        double distance = GeoReverse.distance( groupLocation.getLatitude() ,
                                                               groupLocation.getLongitude() ,
                                                               location.getLatitude() ,
                                                               location.getLongitude() );
                        if ( distance <= roundDistance )
                        {
                            groupToMerge.add( group );
                            break;
                        }
                    }
                }

                if ( groupToMerge.isEmpty() )
                {
                    GroupDistance newGroup = new GroupDistance();
                    groups.add( newGroup );

                    newGroup.files.add( location );

                    if ( location.getPlace() != null )
                    {
                        newGroup.place = location.getPlace();
                    }
                }
                else
                {
                    Iterator<GroupDistance> it = groupToMerge.iterator();
                    GroupDistance firstGroup = it.next();

                    firstGroup.files.add( location );

                    while ( it.hasNext() )
                    {
                        GroupDistance otherGroup = it.next();

                        for ( MFile ofile : otherGroup.files )
                        {
                            firstGroup.files.add( ofile );

                            if ( ofile.getPlace() != null && firstGroup.place == null )
                            {
                                firstGroup.place = ofile.getPlace();
                            }
                        }

                        otherGroup.files.clear();
                        groups.remove( otherGroup );
                    }

                    if ( location.getPlace() != null && firstGroup.place == null )
                    {
                        firstGroup.place = location.getPlace();
                    }
                }
            }

            // Find place for empty place group and affect to files
            if ( logger.isDebugEnabled() )
            {
                logger.debug( "[RegroupDAO] find place for empty place group and affect to files" );
            }

            for ( GroupDistance group : groups )
            {
                if ( group.place == null )
                {
                    // Process center
                    double latSum = 0.0;
                    double lngSum = 0.0;
                    for ( MFile f : group.files )
                    {
                        latSum = latSum + f.getLatitude();
                        lngSum = lngSum + f.getLongitude();
                    }

                    GeoReverse.GeoData gd = new GeoReverse.GeoData( latSum / group.files.size() ,
                                                                    lngSum / group.files.size() );
                    String address = dao.convertGeoToAddress( gd );
                    if ( address == null || address.length() <= 0)
                    {
                        address = gd.getLatitude() + ", " + gd.getLongitude();
                    }
                    
                    if ( logger.isDebugEnabled() )
                    {
                        logger.debug( "[RegroupDAO] create place '" + address + "' for " + group.files.size() + " files" );
                    }

                    MPlace newPlace = new MPlace( UUID.randomUUID().toString() ,
                                                  address ,
                                                  gd.getLatitude() ,
                                                  gd.getLongitude() );

                    dao.addPlaceForFiles( newPlace ,
                                          group.files );
                }
            }
        }
        catch( IOException ex )
        {
            throw new RuntimeException( ex );
        }
        catch( ParseException ex )
        {
            throw new RuntimeException( ex );
        }

        logger.info( "[RegroupDAO] end" );
    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( RegroupDAO.class );
    private FilesControllerDAO dao;
    private Double roundDistance;
    private List<GroupDistance> groups;

    private void init()
    {
        this.dao = null;
        this.roundDistance = null;
        this.groups = new ArrayList<GroupDistance>();
    }

    private class GroupDistance
    {
        // PUBLIC
        public GroupDistance()
        {
            init();
        }
        // PRIVATE
        private List<MFile> files;
        private MPlace place;

        private void init()
        {
            this.files = new ArrayList<MFile>();
            this.place = null;
        }
    }
}
