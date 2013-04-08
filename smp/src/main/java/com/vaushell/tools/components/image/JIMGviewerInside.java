package com.vaushell.tools.components.image;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class JIMGviewerInside
        extends JPanel
        implements MouseWheelListener , MouseInputListener
{
    // PUBLIC
    public JIMGviewerInside()
    {
        super();
        init();
        addMouseListener( this );
        addMouseMotionListener( this );
        addMouseWheelListener( this );
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage(
            BufferedImage image )
    {
        this.image = image;
        resizePanel();
    }

    public double getScaleStep()
    {
        return scaleStep;
    }

    public void setScaleStep(
            double val )
    {
        scaleStep = val;
    }

    public boolean isCursorMoving()
    {
        return moving;
    }

    public void setCursorNotMoving()
    {
        moving = false;
    }

    public void resetCursor()
    {
        setCursor( defaultCursor );
        moving = false;
    }

    public void setCursor2Move()
    {
        setCursor( moveCursor );
        moving = true;
    }

    @Override
    public void mouseWheelMoved(
            MouseWheelEvent evt )
    {
        if ( evt.getWheelRotation() < 0 )
        {
            zoomIn( evt.getPoint() );
        }
        else
        {
            zoomOut( evt.getPoint() );
        }
    }

    @Override
    public void mouseClicked(
            MouseEvent e )
    {
    }

    @Override
    public void mousePressed(
            MouseEvent e )
    {
        startPoint = e.getPoint();
        setCursor2Move();
    }

    @Override
    public void mouseReleased(
            MouseEvent e )
    {
        mouseMoveSchema( e );
        setCursorNotMoving();
    }

    @Override
    public void mouseDragged(
            MouseEvent e )
    {
        mouseMoveSchema( e );
    }

    @Override
    public void mouseEntered(
            MouseEvent e )
    {
    }

    @Override
    public void mouseExited(
            MouseEvent e )
    {
    }

    @Override
    public void mouseMoved(
            MouseEvent e )
    {
    }

    public void zoomIn(
            Point mousePoint )
    {
        zoom( mousePoint ,
              scale + scaleStep );
    }

    public void zoomOut(
            Point mousePoint )
    {
        zoom( mousePoint ,
              scale - scaleStep );
    }

    public void zoom(
            Point mousePoint ,
            double newScale )
    {
        if ( newScale > 0 )
        {
            double factor = newScale / scale;
            Rectangle rect = getVisibleRect();
            Point center = new Point( rect.x + rect.width / 2 ,
                                      rect.y + rect.height / 2 );

            scale = newScale;
            resizePanel();

            Point newCenter;

            if ( mousePoint != null )
            {
                Point vect = new Point( mousePoint.x - center.x ,
                                        mousePoint.y - center.y );
                Point newMousePoint = new Point(
                        (int) Math.round( mousePoint.x * factor ) ,
                        (int) Math.round( mousePoint.y * factor ) );

                newCenter = new Point( newMousePoint.x - vect.x ,
                                       newMousePoint.y - vect.y );
            }
            else
            {
                newCenter = new Point( (int) Math.round( center.x * factor ) ,
                                       (int) Math.round( center.y * factor ) );
            }

            Dimension newDim = getVisibleRect().getSize();
            Rectangle newRect = new Rectangle(
                    newCenter.x - newDim.width / 2 ,
                    newCenter.y - newDim.height / 2 ,
                    newDim.width ,
                    newDim.height );

            scrollRectToVisible( newRect );
        }
    }

    @Override
    public void paint(
            Graphics g )
    {
        super.paint( g );
        g.setColor( Color.WHITE );
        g.fillRect( 0 ,
                    0 ,
                    getWidth() ,
                    getHeight() );

        Graphics2D g2d = (Graphics2D) g;

        if ( image != null )
        {
            Dimension real = getFinalSize();
            Dimension window = getSize();
            Point delta = new Point(
                    Math.max( 0 ,
                              window.width / 2 - real.width / 2 ) ,
                    Math.max( 0 ,
                              window.height / 2 - real.height / 2 ) );

            g2d.translate( delta.x ,
                           delta.y );
            g2d.scale( scale ,
                       scale );
            g2d.drawImage( image ,
                           0 ,
                           0 ,
                           null );
            g2d.scale( 1 / scale ,
                       1 / scale );
            g2d.translate( -delta.x ,
                           -delta.y );
        }
    }
    // PRIVATE
    private final static Cursor moveCursor = new Cursor( Cursor.MOVE_CURSOR );
    private final static Cursor defaultCursor = new Cursor( Cursor.DEFAULT_CURSOR );
    private BufferedImage image;
    private double scaleStep;
    private double scale;
    private boolean moving;
    private Point startPoint;

    private void init()
    {
        this.image = null;
        this.scale = 1.0;
        this.scaleStep = 0.1;
        this.moving = false;
    }

    private Dimension getFinalSize()
    {
        return new Dimension(
                (int) Math.round( (double) image.getWidth() * scale ) ,
                (int) Math.round( (double) image.getHeight() * scale ) );
    }

    private void resizePanel()
    {
        setPreferredSize( getFinalSize() );
        revalidate();
        repaint();
    }

    private void mouseMoveSchema(
            MouseEvent e )
    {
        Point endPoint = e.getPoint();
        Point vect = new Point( startPoint.x - endPoint.x ,
                                startPoint.y - endPoint.y );
        Rectangle oldRect = getVisibleRect();
        Rectangle newRect = new Rectangle( oldRect.x + vect.x ,
                                           oldRect.y + vect.y ,
                                           oldRect.width ,
                                           oldRect.height );

        scrollRectToVisible( newRect );
    }
}
