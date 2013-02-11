/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.action;

import com.vaushell.smp.FilesControllerDAO;
import java.io.File;
import java.io.FileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class MoveDAO
{
    // PUBLIC
    public MoveDAO()
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

    public void setWay( boolean way )
    {
        this.way = way;
    }

    public void run()
            throws FileNotFoundException
    {
        if ( destination == null )
        {
            throw new NullPointerException();
        }

        logger.info( "[MoveDAO] begin" );

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[MoveDAO] retreive all files" );
        }

        for ( File[] files : dao.getAllFilesSRCtoDST( destination ) )
        {
            File src = files[ 0];
            File dst = files[ 1];

            if ( !way )
            {
                File tmp = dst;
                dst = src;
                src = tmp;
            }

            moveFile( src ,
                      dst );
        }

        logger.info( "[MoveDAO] end" );
    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( MoveDAO.class );
    private FilesControllerDAO dao;
    private boolean way;
    private File destination;

    private void init()
    {
        this.dao = null;
        this.way = true;
        this.destination = null;
    }

    private void moveFile( File src ,
                           File dst )
    {
        if ( src == null || dst == null )
        {
            throw new NullPointerException();
        }

        File dstParent = dst.getParentFile();
        if ( !dstParent.exists() )
        {
            dstParent.mkdirs();
        }

        File dst2 = dst;
        int i = 2;
        while ( dst2.exists() )
        {
            dst2 = new File( dst2.getParentFile() ,
                             FilenameUtils.getBaseName( dst2.getName() ) + "-" + i + '.' + FilenameUtils.getExtension( dst2.
                    getName() ) );
            ++i;
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[MoveDAO] move file '" + src.getAbsolutePath() + "' to '" + dst2.getAbsolutePath() + "'" );
        }

//        src.renameTo( dst2 );
    }
}
