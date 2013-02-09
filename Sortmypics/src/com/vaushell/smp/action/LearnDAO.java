/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.action;

import com.vaushell.smp.FilesControllerDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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

        // Find all files
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[LearnDAO] Find all files" );
        }

        files.clear();
        findRecursively( sourceDirectory );

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[LearnDAO] found " + files.size() + " files" );
        }

        // Process all files
        int count = 1;
        for ( File f : files )
        {
            try
            {
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "[LearnDAO] Process file " + count + "/" + files.size() + " '" + f.getAbsolutePath() + "'" );
                }

                dao.createFileIfNecessary( f ,
                                           update );

                count++;
            }
            catch( IOException ex )
            {
                logger.error( ex.getMessage() ,
                              ex );
            }
        }

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
    private List<File> files;

    private void init()
    {
        this.dao = null;
        this.sourceDirectory = null;
        this.update = false;
        this.files = new ArrayList<File>();
    }

    private void findRecursively( File f )
            throws NoSuchAlgorithmException
    {
        if ( f.isDirectory() )
        {
            if ( logger.isDebugEnabled() )
            {
                logger.debug( "[LearnDAO] Found directory '" + f.getAbsolutePath() + "'" );
            }

            for ( File child : f.listFiles() )
            {
                findRecursively( child );
            }
        }
        else
        {
            files.add( f );
        }
    }
}
