package com.vaushell.tools.components.image;

import java.awt.image.BufferedImage;
import javax.swing.JScrollPane;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class JIMGviewer
        extends JScrollPane
{
    // PUBLIC
    public JIMGviewer()
    {
        super();
        init();
    }

    public BufferedImage getImage()
    {
        if ( inside == null )
        {
            return null;
        }

        return inside.getImage();
    }

    public void setImage(
            BufferedImage image )
    {
        if ( image == null )
        {
            setViewportView( null );
            inside = null;
        }
        else
        {
            if ( inside == null )
            {
                inside = new JIMGviewerInside();
                setViewportView( inside );
            }

            inside.setImage( image );
        }
    }

    public double getScaleStep()
    {
        if ( inside == null )
        {
            return Double.NaN;
        }

        return inside.getScaleStep();
    }

    public void setScaleStep(
            double scaleStep )
    {
        if ( inside != null )
        {
            inside.setScaleStep( scaleStep );
        }
    }
    // PRIVATE
    private JIMGviewerInside inside;

    private void init()
    {
        this.inside = null;
    }
}
