/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.places;

import org.apache.commons.configuration.AbstractConfiguration;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class TF_Openstreetmap
        extends TileFactoryInfo
{
    // PUBLIC
    public TF_Openstreetmap( AbstractConfiguration config )
    {
        super( 1 ,
               MAX_DEPTH - 2 ,
               MAX_DEPTH ,
               256 ,
               true ,
               true , // tile size is 256 and x/y orientation is normal
               "" ,//5/15/10.png",
               "x" ,
               "y" ,
               "z" );

        this.pattern = config.getString( "openstreetmap.tiles-pattern" );

        init();
    }

    @Override
    public String getTileUrl( int x ,
                              int y ,
                              int zoom )
    {
        return String.format( pattern ,
                              new Integer( MAX_DEPTH - zoom ) ,
                              new Integer( x ) ,
                              new Integer( y ) );
    }
    // PROTECTED
    // PRIVATE
    private final static int MAX_DEPTH = 19;
    private String pattern;

    private void init()
    {
    }
}
