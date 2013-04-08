package com.vaushell.tools.exiftool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class ExifTool
{
    // PUBLIC
    public ExifTool( String exifToolPath )
    {
        this.exifToolPath = exifToolPath;

        this.reader = null;
        this.writer = null;
        this.cleanupTimer = new Timer( "ExifTool Cleanup Thread" ,
                                       true );
        this.currentCleanupTask = null;
    }

    public Map<String , String> getAllMetas( File file )
            throws IOException
    {
        if ( file == null )
        {
            throw new NullPointerException();
        }

        Map<String , String> resultMap = new HashMap<String , String>();

        startExifToolProcessIfNecessary();

        writer.write( "-n\n-S\n" + file.getAbsolutePath() + "\n-execute\n" ); // numeric output
        writer.flush();

        String line = null;
        while ( ( line = reader.readLine() ) != null )
        {
            String[] pair = TAG_VALUE_PATTERN.split( line );

            if ( pair != null && pair.length == 2 )
            {
                resultMap.put( pair[0] ,
                               pair[1] );
            }

            if ( line.equals( "{ready}" ) )
            {
                break;
            }
        }

        return resultMap;
    }

    public void setMetas( File file ,
                          Map<String , String> tags )
            throws IOException
    {
        if ( file == null || tags == null || tags.size() == 0 )
        {
            throw new NullPointerException();
        }

        startExifToolProcessIfNecessary();

        writer.write( "-n\nS\n" ); // numeric output

        for ( Entry<String , String> entry : tags.entrySet() )
        {
            writer.write( '-' + entry.getKey() + '=' + entry.getValue() + "\n" );
        }

        writer.write( file.getAbsolutePath() + "\n-execute\n" );
        writer.flush();

        String line = null;
        while ( ( line = reader.readLine() ) != null )
        {
            if ( line.equals( "{ready}" ) )
            {
                break;
            }
        }
    }

    public void close()
            throws IOException
    {
        if ( writer != null )
        {
            try
            {
                writer.write( "-stay_open\nFalse\n" );
                writer.flush();
            }
            finally
            {
                writer.close();

                writer = null;
            }
        }

        if ( reader != null )
        {
            reader.close();

            reader = null;
        }
    }
    // PRIVATE
    private static final long PROCESS_CLEANUP_DELAY = 600000L;
    private static final Pattern TAG_VALUE_PATTERN = Pattern.compile( ": " );
    private Timer cleanupTimer;
    private TimerTask currentCleanupTask;
    private BufferedReader reader;
    private OutputStreamWriter writer;
    private String exifToolPath;

    private void startExifToolProcessIfNecessary()
            throws IOException
    {
        if ( currentCleanupTask != null )
        {
            currentCleanupTask.cancel();
        }

        cleanupTimer.schedule(
                ( currentCleanupTask = new CleanupTimerTask( this ) ) ,
                PROCESS_CLEANUP_DELAY ,
                PROCESS_CLEANUP_DELAY );

        if ( reader == null || writer == null )
        {
            List<String> args = new ArrayList<String>();
            args.add( exifToolPath );
            args.add( "-stay_open" );
            args.add( "True" );
            args.add( "-@" );
            args.add( "-" );

            Process proc = new ProcessBuilder( args ).start();

            reader = new BufferedReader( new InputStreamReader( proc.getInputStream() ) );
            writer = new OutputStreamWriter( proc.getOutputStream() );
        }
    }

    private class CleanupTimerTask
            extends TimerTask
    {
        // PUBLIC
        public CleanupTimerTask( ExifTool owner )
        {
            if ( owner == null )
            {
                throw new NullPointerException();
            }

            this.owner = owner;
        }

        // PRIVATE
        @Override
        public void run()
        {
            try
            {
                owner.close();
            }
            catch( IOException ex )
            {
                throw new RuntimeException( ex );
            }
        }
        private ExifTool owner;
    }
}