/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.action;

import com.vaushell.smp.FilesControllerDAO;
import com.vaushell.smp.model.MFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class LearnDAO
{
    // PUBLIC
    public LearnDAO()
    {
        init();
    }

    public void setDAO( FilesControllerDAO dao )
    {
        this.dao = dao;
    }

    public void setSourceDirectory( File sourceDirectory )
    {
        this.sourceDirectory = sourceDirectory;
    }

    public void setUpdate( boolean update )
    {
        this.update = update;
    }

    public void run()
            throws NoSuchAlgorithmException , IOException
    {
        if ( !sourceDirectory.exists() )
        {
            throw new FileNotFoundException();
        }

        logger.info( "[LearnDAO] begin" );

        dao.clear();

        processRecursively( sourceDirectory );

        if ( dao.hasStack() )
        {
            throw new IOException( "Stack not empty !!!" );
        }

        logger.info( "[LearnDAO] end" );
    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( LearnDAO.class );
    private FilesControllerDAO dao;
    private File sourceDirectory;
    private boolean update;

    private void init()
    {
        this.dao = null;
        this.sourceDirectory = null;
        this.update = false;
    }

    private void processRecursively( File f )
            throws NoSuchAlgorithmException
    {
        if ( f.isDirectory() )
        {
            logger.info( "Process directory '" + f.getAbsolutePath() + "'" );
            for ( File child : f.listFiles() )
            {
                processRecursively( child );
            }
        }
        else
        {
            if ( logger.isDebugEnabled() )
            {
                logger.debug( "  File '" + f.getName() + "'" );
            }

            try
            {
                dao.createFileIfNecessary( f ,
                                           update );
            }
            catch( IOException ex )
            {
                logger.error( ex.getMessage() ,
                              ex );
            }
        }
    }
}
