/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.mediainfo;

import mediainfo.MediaInfo;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Fabien VAUCHELLES <fabien at vauchelles.com>
 */
public class MediaInfoExtract
{
    // PUBLIC
    public static MediaInfoExtract getInstance()
    {
        return MediaInfoExtractHolder.INSTANCE;
    }

    public Map<String , String> extract( File f )
    {
        if ( MI.Open( f.getAbsolutePath() ) <= 0 )
        {
            return null;
        }

        HashMap<String , String> map = new HashMap<String , String>();

//        MI.Option( "Complete" ,
//                   "1" );
        MI.Option( "Complete" ,
                   "" );

        Scanner sc = new Scanner( MI.Inform() );
        while ( sc.hasNextLine() )
        {
            String str = sc.nextLine();

            int ind = str.indexOf( ':' );

            if ( ind >= 0 )
            {
                String key = str.substring( 0 ,
                                            ind ).trim();
                String value = str.substring( ind + 1 ).trim();

                if ( key.length() > 0 && value.length() > 0 )
                {
                    map.put( key ,
                             value );
                }
            }
        }
        sc.close();

        MI.Close();

        return map;
    }
    // PRIVATE
    private MediaInfo MI;

    private static class MediaInfoExtractHolder
    {
        private static final MediaInfoExtract INSTANCE = new MediaInfoExtract();
    }

    private MediaInfoExtract()
    {
        this.MI = new MediaInfo();
    }
}
