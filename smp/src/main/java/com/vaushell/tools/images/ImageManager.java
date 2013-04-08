/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.images;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class ImageManager
{
    // PUBLIC
    public static ImageManager getInstance()
    {
        return ImageManagerHolder.INSTANCE;
    }

    public Image getImage( String fileName )
    {
        Image image = myMapImage.get( fileName );
        if ( image == null )
        {
            try
            {
                image = ImageIO.read( this.getClass().getResource( fileName ) );
                if ( image == null )
                {
                    throw new NullPointerException();
                }

                myMapImage.put( fileName ,
                                image );
            }
            catch( IOException ex )
            {
                throw new RuntimeException( ex.getMessage() ,
                                            ex );
            }
        }
        return image;
    }

    public Dimension getSize( String fileName )
    {
        Image image = getImage( fileName );
        return new Dimension( image.getWidth( null ) ,
                              image.getHeight( null ) );
    }
    // PRIVATE
    private Map<String , Image> myMapImage;

    private ImageManager()
    {
        init();
    }

    private void init()
    {
        this.myMapImage = new HashMap<String , Image>();
    }

    private static class ImageManagerHolder
    {
        private static final ImageManager INSTANCE = new ImageManager();
    }
}
