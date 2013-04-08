/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.md5;

import com.twmacinta.util.MD5;
import com.twmacinta.util.MD5OutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class MD5tools
{
    // PUBLIC
    public static String md5sum( File f )
            throws IOException
    {
        String sum = null;

        MD5OutputStream out = null;
        InputStream in = null;
        try
        {
            out = new MD5OutputStream( new com.twmacinta.io.NullOutputStream() );
            in = new BufferedInputStream( new FileInputStream( f ) );

            IOUtils.copy( in ,
                          out );

            sum = MD5.asHex( out.hash() );
        }
        finally
        {
            if ( in != null )
            {
                in.close();
            }
            if ( out != null )
            {
                out.close();
            }
        }

        return sum;
    }
}
