/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.geo;

import java.io.IOException;
import java.util.HashMap;
import org.json.simple.parser.ParseException;
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
public class GeoReverseTest
{
    public GeoReverseTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
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
            throws IOException , ParseException
    {

        String address = "17 Rue du Bac, 75007 Paris, France";

        GeoReverse.GeoData gd = GeoReverse.getInstance().convertAddressToGeo( address );

        String revAddress = GeoReverse.getInstance().convertGeoToAddress( gd );

        assertEquals( address ,
                      revAddress );

    }
}
