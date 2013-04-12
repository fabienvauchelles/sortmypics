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

    // <editor-fold defaultstate="collapsed" desc="Init">
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Description">
    public List<Description> getAllDescriptionsWithGPS()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] getAllDescriptionsWithGPS" );
        }

        waitAndLock();

        try
        {
            Query q = em.createQuery( "from Description d where d.GPSlat<>null and d.GPSlng<>null" );

            return q.getResultList();
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

            em.flush();

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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FilePath">
    public List<FilePath> getAllFilePathsAndDescription()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] getAllFilePathsAndDescription" );
        }

        waitAndLock();

        try
        {
            Query q = em.createQuery( "from FilePath fp order by fp.sourcePath, fp.name" );
            List<FilePath> fps = q.getResultList();

            // Links are automatically build (same description object between filepath)
            for ( FilePath fp : fps )
            {
                fp.getDescription();
            }

            return fps;
        }
        finally
        {
            releaseLock();
        }
    }

    public FilePath createFilePathAndDescriptionIfNecessary( FilePath newFilePath ,
                                                             Description newDescription )
    {
        if ( newFilePath == null || newDescription == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.
                    debug(
                    "[ContentDAOmanager] createFilePathAndDescriptionIfNecessary : newFilePath=" + newFilePath + " / newDescription=" + newDescription );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            Description oldDescription = (Description) em.find( Description.class ,
                                                                newDescription.getID() );
            if ( oldDescription != null )
            {
                Query qFilePath = em.
                        createQuery(
                        "select count(fp) from FilePath fp where fp.sourcePath=:sourcePath and fp.name=:name and fp.description=:description" );
                qFilePath.setParameter( "sourcePath" ,
                                        newFilePath.getSourcePath() );
                qFilePath.setParameter( "name" ,
                                        newFilePath.getName() );
                qFilePath.setParameter( "description" ,
                                        oldDescription );

                Long count = (Long) qFilePath.getSingleResult();

                if ( count > 0 )
                {
                    em.flush();

                    transac.commit();

                    return null;
                }
                else
                {
                    oldDescription.setFilesCount( oldDescription.getFilesCount() + 1 );
                    em.merge( oldDescription );
                    em.flush();

                    newFilePath.setDescription( oldDescription );

                    em.persist( newFilePath );

                    em.flush();

                    transac.commit();

                    return newFilePath;
                }
            }
            else
            {
                newDescription.setFilesCount( 1 );
                em.persist( newDescription );
                em.flush();

                em.persist( newFilePath );
                em.flush();

                transac.commit();

                return newFilePath;
            }
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

            em.flush();

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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Place">
    public List<Place> getAllPlaces()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] getAllPlaces" );
        }

        waitAndLock();

        try
        {
            Query q = em.createQuery( "from Place p order by p.name" );

            return q.getResultList();
        }
        finally
        {
            releaseLock();
        }
    }

    public void addPlace( Place place )
    {
        if ( place == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] addPlace : place=" + place );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            em.persist( place );

            em.flush();

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

    public void updatePlace( Place place )
    {
        if ( place == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] updatePlace : place=" + place );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            em.merge( place );

            em.flush();

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

    public void deletePlace( Place place )
    {
        if ( place == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] removePlace : place=" + place );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            Query q = em.createQuery( "from Description d where d.place=:place" );
            q.setParameter( "place" ,
                            place );
            List<Description> ds = q.getResultList();
            for ( Description d : ds )
            {
                d.setPlace( null );
                em.merge( d );
            }

            em.remove( place );

            em.flush();

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

    public void deleteAllDescriptions()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] deleteAllDescriptions" );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            Query qFP = em.createQuery( "from FilePath fp" );
            List<FilePath> fps = qFP.getResultList();
            for ( FilePath fp : fps )
            {
                em.remove( fp );
            }

            Query qDesc = em.createQuery( "from Description d" );
            List<Description> ds = qDesc.getResultList();
            for ( Description d : ds )
            {
                em.remove( d );
            }

            em.flush();

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
    // </editor-fold>
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( ContentDAOmanager.class );
    private EntityManagerFactory emf;
    private EntityManager em;
    private boolean lock;

    private void init()
    {
        this.lock = false;
    }

    private ContentDAOmanager()
    {
        init();
    }

    private static class ContentDAOmanagerHolder
    {
        private static final ContentDAOmanager INSTANCE = new ContentDAOmanager();
    }

    private synchronized void waitAndLock()
    {
        while ( lock )
        {
            try
            {
                wait();
            }
            catch( InterruptedException ex )
            {
                // Ignore
            }
        }

        lock = true;
    }

    private synchronized void releaseLock()
    {
        lock = false;

        notify();
    }
}
