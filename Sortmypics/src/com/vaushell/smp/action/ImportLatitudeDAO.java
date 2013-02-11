/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.action;

import com.vaushell.smp.FilesControllerDAO;
import com.vaushell.smp.model.MFile;
import com.vaushell.tools.geo.GeoReverse;
import com.vaushell.tools.latitude.GLatitude;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class ImportLatitudeDAO
{
    // PUBLIC
    public static final double MAXDISTANCE_IN_METERS = 2000.0;
    public static final double ACCURACY_MAX_IN_METERS = 50.0;

    public ImportLatitudeDAO()
    {
        init();
    }

    public void setDAO( FilesControllerDAO dao )
    {
        this.dao = dao;
    }

    public void run()
    {
        logger.info( "[ImportLatitudeDAO] begin" );

        try
        {
            // Prepare dates
            List<MFile> updated = new ArrayList<MFile>();
            for ( MFile actualFile : dao.getAllFilesOrderbyCalendar() )
            {
                Calendar minCal = (Calendar) actualFile.getCreated().clone();
                minCal.add( Calendar.MINUTE ,
                            -1 );

                GLatitude.GLocation best = null;
                long bestDiff = Long.MAX_VALUE;
                for ( GLatitude.GLocation gl : dao.getAllLocations( minCal ) )
                {
                    if ( gl.getAccuracy() == null || gl.getAccuracy().compareTo( ACCURACY_MAX_IN_METERS ) <= 0 )
                    {
                        long diff = Math.abs( gl.getCalendar().getTimeInMillis() - actualFile.getCreated().getTimeInMillis() );
                        if ( diff < bestDiff )
                        {
                            bestDiff = diff;
                            best = gl;
                        }
                    }
                }

                if ( best != null )
                {
                    if ( actualFile.getLatitude() == null || actualFile.getLongitude() == null )
                    {
                        actualFile.setLatitude( best.getLatitude() );
                        actualFile.setLongitude( best.getLongitude() );

                        updated.add( actualFile );
                    }
                    else
                    {
                        if ( GeoReverse.distance( actualFile.getLatitude() ,
                                                  actualFile.getLongitude() ,
                                                  best.getLatitude() ,
                                                  best.getLongitude() ) > MAXDISTANCE_IN_METERS )
                        {
                            logger.warn( "GPS doesnt match between latitude and existing for '" + actualFile.getFile().
                                    getAbsolutePath() + "'" );
                        }
                    }
                }
            }

            if ( updated.size() > 0 )
            {
                dao.update( updated );
            }
        }
        catch( IOException ex )
        {
            throw new RuntimeException( ex );
        }

        logger.info( "[ImportLatitudeDAO] end" );
    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( ImportLatitudeDAO.class );
    private FilesControllerDAO dao;

    private void init()
    {
        this.dao = null;
    }
}
