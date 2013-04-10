/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.viewer;

import com.vaushell.rc.popup.A_PopupPanel;
import com.vaushell.smp.MainJFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class PictureViewerPanel
        extends A_PopupPanel
{
    // PUBLIC
    public PictureViewerPanel( MainJFrame main )
    {
        super( main ,
               "Viewer" );

        init();
    }

    public void setImage( BufferedImage image )
    {
        this.image = image;

        repaint();
    }

    @Override
    public void finished()
    {
    }

    // PROTECTED
    @Override
    protected void paintComponent( Graphics g )
    {
        super.paintComponent( g );

        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints( RenderingHints.KEY_ANTIALIASING ,
                                                RenderingHints.VALUE_ANTIALIAS_ON );
        g2d.setRenderingHints( rh );

        if ( image != null )
        {
            double sx = (double) getWidth() / (double) image.getWidth();
            double sy = (double) getHeight() / (double) image.getHeight();

            double tx, ty;
            if ( sx < sy )
            {
                tx = 0;
                ty = ( (double) getHeight() - (double) image.getHeight() * sx ) / 2.0;
            }
            else
            {
                tx = ( (double) getWidth() - (double) image.getWidth() * sy) / 2.0;
                ty = 0;
            }

            g2d.translate( tx ,
                           ty );

            double min = Math.min( sx ,
                                   sy );

            g2d.scale( min ,
                       min );

            g2d.drawImage( image ,
                           0 ,
                           0 ,
                           this );

            g2d.scale( 1.0 / sx ,
                       1.0 / sy );

            g2d.translate( -tx ,
                           -ty );
        }

    }
    // PRIVATE
    private BufferedImage image;

    private void init()
    {
        this.image = null;

        setBackground( Color.WHITE );
        
        setPreferredSize( new Dimension( 1024 ,
                                         800 ) );
    }
}
