/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.action;

import com.vaushell.smp.FilesControllerDAO;
import com.vaushell.smp.model.MFile;
import com.vaushell.tools.geo.GeoReverse;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class ExtractKMLdao
{
    // PUBLIC
    public ExtractKMLdao()
    {
        init();
    }

    public void setDAO( FilesControllerDAO dao )
    {
        this.dao = dao;
    }

    public void setDestination( File destination )
    {
        this.destination = destination;
    }

    public void setRoundDistance( Double roundDistance )
    {
        this.roundDistance = roundDistance;
    }

    public void run()
            throws FileNotFoundException
    {
        if ( destination == null || roundDistance == null )
        {
            throw new NullPointerException();
        }

        logger.info( "[ExtractKMLdao] begin" );

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ExtractKMLdao] retreive all locations" );
        }
        List<MFile> locations = dao.getAllLocation();

        // Create groups
        groups.clear();

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ExtractKMLdao] create group" );
        }
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
            }
            else
            {
                Iterator<GroupDistance> it = groupToMerge.iterator();
                GroupDistance firstGroup = it.next();

                firstGroup.files.add( location );

                while ( it.hasNext() )
                {
                    GroupDistance otherGroup = it.next();
                    firstGroup.files.addAll( otherGroup.files );

                    otherGroup.files.clear();
                    groups.remove( otherGroup );
                }
            }
        }

        // Write KML
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ExtractKMLdao] write KML" );
        }

        final Kml kml = new Kml();

        Document doc = kml.createAndSetDocument().withName( "JAK Export" ).withOpen( true );

        Folder folder = doc.createAndAddFolder();
        folder.withName( "Export" ).withOpen( true );

        int number = 0;
        for ( GroupDistance group : groups )
        {
            Folder subfolder = folder.createAndAddFolder();
            subfolder.withName( "Group " + number ).withOpen( true );
            ++number;

            for ( MFile e : group.files )
            {
                subfolder
                        .createAndAddPlacemark()
                        .withName( e.getName() + "." + e.getExtension() )
                        .withDescription( "<![CDATA[<img src=\"file:///" + e.getFile().getAbsolutePath() + "\" />" )
                        .withOpen( Boolean.TRUE )
                        .createAndSetPoint()
                        .addToCoordinates( e.getLongitude() ,
                                           e.getLatitude() );

            }
        }

        kml.marshal( destination );

        logger.info( "[ExtractKMLdao] end" );
    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( ExtractKMLdao.class );
    private FilesControllerDAO dao;
    private File destination;
    private Double roundDistance;
    private List<GroupDistance> groups;

    private void init()
    {
        this.dao = null;
        this.destination = null;
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

        private void init()
        {
            this.files = new ArrayList<MFile>();
        }
    }
}
