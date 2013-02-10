/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp;

import com.google.gdata.util.ServiceException;
import com.vaushell.smp.action.ExtractKMLdao;
import com.vaushell.smp.action.ImportContactDAO;
import com.vaushell.smp.action.ImportLatitudeDAO;
import com.vaushell.smp.action.LearnDAO;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class Main
{
    // PUBLIC
    public static void main( String[] args )
    {
        if ( args.length <= 0 )
        {
            showUsage();
        }
        else
        {
            String order = args[ 0];
            if ( order.equalsIgnoreCase( "learn" ) )
            {
                if ( args.length < 2 || args.length > 3 )
                {
                    showUsage();
                }
                else
                {
                    File source = new File( args[ 1] );
                    if ( !source.exists() )
                    {
                        logger.error( "Directory '" + source.getAbsolutePath() + "' doesn't exist" );
                    }
                    else
                    {
                        if ( args.length == 3 && args[ 2].equalsIgnoreCase( "update" ) )
                        {
                            learn( source ,
                                   true );
                        }
                        else
                        {
                            learn( source ,
                                   false );
                        }
                    }
                }
            }
            else if ( order.equalsIgnoreCase( "clean" ) )
            {
                if ( args.length != 1 )
                {
                    showUsage();
                }
                else
                {
                    clean();
                }
            }
            else if ( order.equalsIgnoreCase( "extract" ) )
            {
                if ( args.length != 3 )
                {
                    showUsage();
                }
                else
                {
                    File destination = new File( args[ 1] );

                    Double roundDistance = new Double( args[ 2] );

                    extractKML( destination ,
                                roundDistance );
                }
            }
            else if ( order.equalsIgnoreCase( "importcontact" ) )
            {
                if ( args.length != 3 )
                {
                    showUsage();
                }
                else
                {
                    String username = args[ 1];
                    String password = args[ 2];

                    importContact( username ,
                                   password );
                }
            }
            else if ( order.equalsIgnoreCase( "importlatitude" ) )
            {
                if ( args.length != 1 )
                {
                    showUsage();
                }
                else
                {

                    importLatitude();
                }
            }
            else
            {
                showUsage();
            }
        }
    }
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( Main.class );

    private static void showUsage()
    {
        System.err.println( "usage: Sortmypics" );
        System.err.println(
                "  learn <source directory> [update] : parse directory for information (update if new files are added)" );
        System.err.println( "  1. clean : clean all database" );
        System.err.println( "  2. importlatitude : add google latitude position to existing picture" );
        System.err.println( "  3. importcontact <username> <password> : import google address books to place" );
        System.err.println( "  4. regroup <round distance in meters> : regroup position for unassociated place" );
        System.err.println( "  (optional) extract <kml destination> <round distance in meters> : extract all kml data to a file" );
    }

    private static void learn( File source ,
                               boolean update )
    {
        try
        {
            FilesControllerDAO dao = new FilesControllerDAO();
            dao.setDatabaseFile( new File( "database" ) );
            dao.setDatabaseFileTemplate( new File( "database-template" ) );

            dao.start();

            LearnDAO learnDAO = new LearnDAO();
            learnDAO.setDAO( dao );
            learnDAO.setSourceDirectory( source );
            learnDAO.setUpdate( update );
            learnDAO.run();

            dao.stop();
        }
        catch( IOException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }
        catch( NoSuchAlgorithmException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }
    }

    private static void clean()
    {
        try
        {
            FilesControllerDAO dao = new FilesControllerDAO();
            dao.setDatabaseFile( new File( "database" ) );
            dao.setDatabaseFileTemplate( new File( "database-template" ) );

            dao.start();

            dao.clean();

            dao.stop();
        }
        catch( IOException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }
    }

    /**
     * 
     * @param destination
     * @param roundDistance in meters
     */
    private static void extractKML( File destination ,
                                    Double roundDistance )
    {
        try
        {
            FilesControllerDAO dao = new FilesControllerDAO();
            dao.setDatabaseFile( new File( "database" ) );
            dao.setDatabaseFileTemplate( new File( "database-template" ) );

            dao.start();

            ExtractKMLdao extractDAO = new ExtractKMLdao();
            extractDAO.setDAO( dao );
            extractDAO.setDestination( destination );
            extractDAO.setRoundDistance( roundDistance );
            extractDAO.run();

            dao.stop();
        }
        catch( IOException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }
    }

    private static void importContact( String username ,
                                       String password )
    {
        try
        {
            FilesControllerDAO dao = new FilesControllerDAO();
            dao.setDatabaseFile( new File( "database" ) );
            dao.setDatabaseFileTemplate( new File( "database-template" ) );

            dao.start();

            ImportContactDAO importDAO = new ImportContactDAO();
            importDAO.setDAO( dao );
            importDAO.setUsername( username );
            importDAO.setPassword( password );
            importDAO.run();

            dao.stop();
        }
        catch( ParseException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }
        catch( ServiceException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }
        catch( IOException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }
    }

    private static void importLatitude()
    {
        try
        {
            FilesControllerDAO dao = new FilesControllerDAO();
            dao.setDatabaseFile( new File( "database" ) );
            dao.setDatabaseFileTemplate( new File( "database-template" ) );

            dao.start();

            ImportLatitudeDAO importDAO = new ImportLatitudeDAO();
            importDAO.setDAO( dao );
            importDAO.run();

            dao.stop();
        }
        catch( IOException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }
    }
}
