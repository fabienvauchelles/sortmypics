/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.md5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class MD5toolsTest
{
    public MD5toolsTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
            throws IOException
    {
        tmpFile = File.createTempFile( "test" ,
                                       "txt" );

        BufferedWriter bfw = new BufferedWriter( new FileWriter( tmpFile ) );
        bfw.write( "essai" );
        bfw.newLine();
        bfw.write( "encore" );
        bfw.newLine();
        bfw.close();
    }

    @AfterClass
    public static void tearDownClass()
    {
        if ( tmpFile != null && tmpFile.exists() )
        {
            tmpFile.delete();
        }
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void hello()
            throws NoSuchAlgorithmException , IOException
    {
        String sum = MD5tools.md5sum( tmpFile );

        assertEquals( "048a83bd7d605378dc9ce4b202594675" ,
                      sum );
    }
    // PRIVATE
    private static File tmpFile;
}
