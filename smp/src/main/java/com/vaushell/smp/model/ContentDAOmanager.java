/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.model;

import com.vaushell.tools.Reference;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class ContentDAOmanager
{
    // PUBLIC
    public static ContentDAOmanager getInstance()
    {
        return ContentDAOmanagerHolder.INSTANCE;
    }

    public void start()
            throws IOException
    {
        File sourceFile = new File( "database-template/db.h2.db" );
        if ( !sourceFile.exists() )
        {
            throw new FileNotFoundException( "Cannot find '" + sourceFile.getAbsolutePath() + "'" );
        }

        File destPath = new File( "database" );
        if ( !destPath.exists() )
        {
            destPath.mkdir();
        }

        File destFile = new File( destPath ,
                                  "db.h2.db" );
        if ( !destFile.exists() )
        {
            FileUtils.copyFile( sourceFile ,
                                destFile );
        }

        // Create entitymanager factory
        Map properties = new HashMap();
        properties.put( "hibernate.connection.url" ,
                        "jdbc:h2:" + destPath.getAbsolutePath() + File.separator + "db" );

        this.emf = Persistence.createEntityManagerFactory( "pu" ,
                                                           properties );
        this.em = emf.createEntityManager();
    }

    public void stop()
    {
        if ( em != null )
        {
            em.close();
        }

        if ( emf != null )
        {
            emf.close();
        }
    }

    public Description getDescriptionByID( String ID )
    {
        if ( ID == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] getDescriptionByID : ID=" + ID );
        }

        return em.find( Description.class ,
                        ID );
    }

    public List<FilePath> getAllFilePathsAndDescription()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] getAllFilePathsAndDescription" );
        }

        Query q = em.createQuery( "from FilePath fp order by fp.path, fp.name" );
        List<FilePath> fps = q.getResultList();

        // Links are automatically build (same description object between filepath)
        for ( FilePath fp : fps )
        {
            fp.getDescription();
        }

        return fps;
    }

    public void addFilePath( FilePath fp )
    {
        if ( fp == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] addFilePath : fp=" + fp );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            em.persist( fp );
            
            Description d = fp.getDescription();
            d.setFilesCount( d.getFilesCount() + 1 );
            em.merge( d );

            transac.commit();
        }
        catch( RuntimeException ex )
        {
            if ( transac != null )
            {
                transac.rollback();
            }

            throw ex;
        }
        finally
        {
            releaseLock();
        }
    }

    public void updateFilePath( FilePath fp )
    {
        if ( fp == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] updateFilePath : fp=" + fp );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            em.merge( fp );

            transac.commit();
        }
        catch( RuntimeException ex )
        {
            if ( transac != null )
            {
                transac.rollback();
            }

            throw ex;
        }
        finally
        {
            releaseLock();
        }
    }

    public FilePath getFilePathByNameAndDescription( String path ,
                                                     String name ,
                                                     Description d )
    {
        if ( path == null || d == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.
                    debug(
                    "[ContentDAOmanager] getFilePathByPathAndDescription() : path=" + path + " / name=" + name + " / d=" + d );
        }

        Query q = em.createQuery( "from FilePath fp where fp.path=:path and fp.name=:name and fp.description=:description" );
        q.setParameter( "path" ,
                        path );
        q.setParameter( "name" ,
                        name );
        q.setParameter( "description" ,
                        d );

        try
        {
            return (FilePath) q.getSingleResult();
        }
        catch( NoResultException ex )
        {
            return null;
        }
        catch( NonUniqueResultException ex )
        {
            return null;
        }
    }

    public void addDescription( Description description )
    {
        if ( description == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] addDescription : description=" + description );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            em.persist( description );

            transac.commit();
        }
        catch( RuntimeException ex )
        {
            if ( transac != null )
            {
                transac.rollback();
            }

            throw ex;
        }
        finally
        {
            releaseLock();
        }
    }

    public void updateDescription( Description description )
    {
        if ( description == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] updateDescription : description=" + description );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            em.merge( description );

            transac.commit();
        }
        catch( RuntimeException ex )
        {
            if ( transac != null )
            {
                transac.rollback();
            }

            throw ex;
        }
        finally
        {
            releaseLock();
        }
    }
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( ContentDAOmanager.class );
    private EntityManagerFactory emf;
    private EntityManager em;
    private final Reference<Boolean> lock = new Reference<Boolean>( Boolean.FALSE );

    private void init()
    {
    }

    private ContentDAOmanager()
    {
        init();
    }

    private static class ContentDAOmanagerHolder
    {
        private static final ContentDAOmanager INSTANCE = new ContentDAOmanager();
    }

    private void waitAndLock()
    {
        synchronized( lock )
        {
            while ( lock.o )
            {
                try
                {
                    lock.wait();
                }
                catch( InterruptedException ex )
                {
                    // Ignore
                }
            }

            lock.o = Boolean.TRUE;
        }
    }

    private void releaseLock()
    {
        synchronized( lock )
        {
            lock.o = Boolean.FALSE;

            lock.notifyAll();
        }
    }
}
