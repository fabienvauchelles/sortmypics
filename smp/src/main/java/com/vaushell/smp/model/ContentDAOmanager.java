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

    public List<Listing> getAllListings()
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] getAllListings" );
        }

        Query q = em.createQuery( "from Listing l order by l.date" );

        List<Listing> lst = q.getResultList();

        return lst;
    }

    public Listing addListing( Listing listing )
    {
        if ( listing == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] addListing : listing=" + listing );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            Listing newListing = em.merge( listing );

            transac.commit();

            return newListing;
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

    public void deleteListing( Listing listing )
    {
        if ( listing == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] deleteListing : listing=" + listing );
        }

        waitAndLock();

        EntityTransaction transac = em.getTransaction();
        try
        {
            transac.begin();

            Query qDescription = em.createQuery(
                    "delete from Description d where d in (select p.description from Place p where p.listing=:listing)" );
            qDescription.setParameter( "listing" ,
                                       listing );
            qDescription.executeUpdate();

            Query qPlace = em.createQuery( "delete from Place p where p.listing=:listing" );
            qPlace.setParameter( "listing" ,
                                 listing );
            qPlace.executeUpdate();

            Query qListing = em.createQuery( "delete from Listing l where l=:listing" );
            qListing.setParameter( "listing" ,
                                   listing );
            qListing.executeUpdate();

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

    public List<Place> getAllPlacesAndDescritorsForListing( Listing l )
    {
        if ( l == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ContentDAOmanager] getAllPlacesAndDescritorsForListing : l=" + l );
        }

        Query q = em.createQuery( "from Place p where p.listing=:listing order by p.path" );
        q.setParameter( "listing" ,
                        l );

        List<Place> places = q.getResultList();
        for ( Place p : places )
        {
            p.getDescription();
        }

        return places;
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

            em.merge( place );

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

    public Place getPlaceByPathAndDescription( String path ,
                                               Description d )
    {
        if ( path == null || d == null )
        {
            throw new NullPointerException();
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[getPlaceByPathAndDescription] path : path=" + path + " / d=" + d );
        }

        Query q = em.createQuery( "from Place p where p.path=:path and p.description=:description" );
        q.setParameter( "path" ,
                        path );
        q.setParameter( "description" ,
                        d );

        try
        {
            return (Place) q.getSingleResult();
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
