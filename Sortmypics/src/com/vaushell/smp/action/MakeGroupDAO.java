/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.action;

import com.vaushell.smp.FilesControllerDAO;
import com.vaushell.smp.model.MFile;
import com.vaushell.smp.model.MGroup;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class MakeGroupDAO
{
    // PUBLIC
    public MakeGroupDAO()
    {
        init();
    }

    public void setDAO( FilesControllerDAO dao )
    {
        this.dao = dao;
    }

    public void run()
    {
        logger.info( "[MakeGroupDAO] begin" );

        // Clean group
        dao.cleanGroups();

        // Parse all file and create group and affect to files
        List<MFile> toUpdate = new ArrayList<MFile>();
        MGroup actualGroup = null;
        for ( MFile file : dao.getAllFilesOrderbyCalendar() )
        {
            boolean samegroup;
            if ( actualGroup == null )
            {
                samegroup = false;
            }
            else
            {
                if ( near( file.getCreated() ,
                           actualGroup.getMax().getCreated() ) )
                {
                    if ( file.getPlace() != null )
                    {
                        if ( actualGroup.getPlace() != null )
                        {
                            if ( file.getPlace().getID().equals( actualGroup.getPlace().getID() ) )
                            {
                                samegroup = true;
                            }
                            else
                            {
                                samegroup = false;
                            }
                        }
                        else
                        {
                            samegroup = false;
                        }
                    }
                    else
                    {
                        if ( actualGroup.getPlace() != null )
                        {
                            samegroup = false;
                        }
                        else
                        {
                            samegroup = true;
                        }
                    }

                }
                else
                {
                    samegroup = false;
                }
            }

            if ( samegroup )
            {
                actualGroup.setMax( file );
            }
            else
            {
                if ( toUpdate.size() > 0 )
                {
                    dao.addGroupForFiles( actualGroup ,
                                          toUpdate );

                    toUpdate.clear();
                }

                actualGroup = new MGroup( UUID.randomUUID().toString() ,
                                          file ,
                                          file ,
                                          file.getCreated() ,
                                          file.getPlace() );
            }

            toUpdate.add( file );
        }

        if ( toUpdate.size() > 0 )
        {
            if ( toUpdate.size() > 0 )
            {
                dao.addGroupForFiles( actualGroup ,
                                      toUpdate );

                toUpdate.clear();
            }
        }

        logger.info( "[MakeGroupDAO] end" );
    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( MakeGroupDAO.class );
    private FilesControllerDAO dao;

    private void init()
    {
        this.dao = null;
    }

    private boolean near( Calendar c1 ,
                          Calendar c2 )
    {
        long l1 = c1.getTimeInMillis();
        long l2 = c2.getTimeInMillis();

        return Math.abs( l2 - l1 ) <= ( 60 * 60 * 1000 );
    }
}
