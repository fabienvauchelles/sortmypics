/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.places;

import com.vaushell.rc.popup.A_PopupPanel;
import com.vaushell.smp.MainJFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class PlaceViewerPanel
        extends A_PopupPanel
{
    // PUBLIC
    public PlaceViewerPanel( MainJFrame main )
    {
        super( main ,
               "View place" );

        init();

        initComponents();

        initComponents2();
    }

    @Override
    public void finished()
    {
    }

    public void setAddressLocation( GeoPosition gp )
    {
        if ( gp != null )
        {
            jMP.setAddressLocation( gp );
            jMP.setZoom( 3 );
        }
        else
        {
            jMP.setAddressLocation( DEFAULT_POSITION );
            jMP.setZoom( 17 );
        }
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jPNLcontent = new javax.swing.JPanel();
        jMP = new org.jdesktop.swingx.JXMapKit();

        jPNLcontent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPNLcontent.add(jMP, gridBagConstraints);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPNLcontent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPNLcontent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXMapKit jMP;
    private javax.swing.JPanel jPNLcontent;
    // End of variables declaration//GEN-END:variables
    // PROTECTED
    // PRIVATE
    private final static int CROSS_RADIUS_SIZE = 10;
    private final static GeoPosition DEFAULT_POSITION = new GeoPosition( 11.35 ,
                                                                         142.2 );

    private void init()
    {
    }

    private void initComponents2()
    {
        // Map viewer
        jMP.setTileFactory( new DefaultTileFactory( new TF_Openstreetmap( ( (MainJFrame) getMain() ).getConfig() ) ) );

        jMP.setMiniMapVisible( false );
        jMP.getMainMap().setPanEnabled( false );

        jMP.getMainMap().setOverlayPainter( new Painter()
        {
            public void paint( Graphics2D g2d ,
                               Object t ,
                               int i ,
                               int i1 )
            {
                int centerX = jMP.getMainMap().getWidth() / 2;
                int centerY = jMP.getMainMap().getHeight() / 2;

                // Draw cross
                g2d.setColor( Color.RED );
                g2d.drawLine( centerX - CROSS_RADIUS_SIZE ,
                              centerY ,
                              centerX + CROSS_RADIUS_SIZE ,
                              centerY );
                g2d.drawLine( centerX ,
                              centerY - CROSS_RADIUS_SIZE ,
                              centerX ,
                              centerY + CROSS_RADIUS_SIZE );
            }
        } );
    }
}
