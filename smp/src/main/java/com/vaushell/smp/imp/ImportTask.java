/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.imp;

import com.vaushell.rc.thread.A_Task;
import com.vaushell.smp.MainJFrame;
import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Description;
import com.vaushell.smp.model.FilePath;
import com.vaushell.tools.exiftool.ExifTool;
import com.vaushell.tools.exiftool.ExifToolManager;
import com.vaushell.tools.md5.MD5tools;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class ImportTask
        extends A_Task
{
    // PUBLIC
    public ImportTask( File target ,
                       Description.FilePathType type ,
                       boolean ignoreSort ,
                       boolean writable ,
                       MainJFrame main )
    {
        super( "Import picture '" + target.getAbsolutePath() );

        this.target = target;
        this.type = type;
        this.ignoreSort = ignoreSort;
        this.writable = writable;
        this.main = main;

        init();
    }

    @Override
    public void run()
    {
        try
        {
            String md5hash = MD5tools.md5sum( target.getAbsoluteFile() );

            Description d = new Description();
            d.setID( md5hash );
            d.setType( type );
            d.setIgnoreSort( ignoreSort );
            d.setWritable( writable );
            d.setFilesCount( 1 );

            if ( type != Description.FilePathType.OTHER )
            {
                ExifTool tool = ExifToolManager.getInstance().borrowTool();

                Map<String , String> exifMap = tool.getAllMetas( target );

                ExifToolManager.getInstance().returnTool( tool );

                // CreatedDate
                SimpleDateFormat sf = new SimpleDateFormat( "yyyy:MM:dd HH:mm:ss" );
                for ( String dateTag : dateTags )
                {
                    String dateTagStr = exifMap.get( dateTag );
                    if ( dateTagStr != null )
                    {
                        try
                        {
                            Date dt = sf.parse( dateTagStr );
                            if ( dt != null )
                            {
                                Calendar c = Calendar.getInstance();
                                c.setTime( dt );

                                d.setCreatedDate( c );

                                if ( type == Description.FilePathType.VIDEO )
                                {
                                    // TODO time
                                    System.out.println( c.getTime() + " " + c.getTimeInMillis() );
                                }

                                break;
                            }
                        }
                        catch( ParseException ex )
                        {
                        }
                    }
                }

                // Title
                String title = exifMap.get( "Title" );
                if ( title != null )
                {
                    d.setTitle( title );
                }

                // GPS latitude
                String gpsLatitudeStr = exifMap.get( "GPSLatitude" );
                if ( gpsLatitudeStr != null )
                {
                    try
                    {
                        d.setGPSlat( Double.parseDouble( gpsLatitudeStr ) );
                    }
                    catch( NumberFormatException ex )
                    {
                    }
                }

                // GPS longitude
                String gpsLongitudeStr = exifMap.get( "GPSLongitude" );
                if ( gpsLongitudeStr != null )
                {
                    try
                    {
                        d.setGPSlng( Double.parseDouble( gpsLongitudeStr ) );
                    }
                    catch( NumberFormatException ex )
                    {
                    }
                }

                // Make
                String make = exifMap.get( "Make" );
                if ( make != null )
                {
                    d.setMake( make );
                }

                // Model
                String model = exifMap.get( "Model" );
                if ( model != null )
                {
                    d.setModel( model );
                }
            }

            FilePath fp = new FilePath( target.getParentFile().getAbsolutePath() ,
                                        target.getName() ,
                                        d );

            fp = ContentDAOmanager.getInstance().createFilePathAndDescriptionIfNecessary( fp ,
                                                                                          d );

            if ( fp != null )
            {
                main.addFilePath( fp );
            }
        }
        catch( IOException ex )
        {
            logger.error( ex.getMessage() ,
                          ex );
        }

    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( ImportTask.class );
    private final static String[] dateTags = new String[]
    {
        "DateTimeCreated" , "DateTimeOriginal" , "CreateDate" , "ModifyDate" , "PreviewDate" , "TrackCreateDate" ,
        "TrackModifyDate" , "MediaCreateDate" , "MediaModifyDate"
    };
    private File target;
    private Description.FilePathType type;
    private boolean ignoreSort;
    private boolean writable;
    private MainJFrame main;

    private void init()
    {
    }
}
