/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.places;

import com.vaushell.rc.popup.A_PopupPanel;
import com.vaushell.smp.MainJFrame;
import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Description;
import com.vaushell.smp.model.Place;
import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.NominatimClient;
import fr.dudie.nominatim.model.Address;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class PlacesEditorPanel
        extends A_PopupPanel
{
    // PUBLIC
    public PlacesEditorPanel( MainJFrame main )
    {
        super( main ,
               "Edit places" );

        this.placesModel = new DefaultListModel();
        this.positions = new ArrayList<GeoPosition>();

        SchemeRegistry register = new SchemeRegistry();
        register.register( new Scheme( "http" ,
                                       new PlainSocketFactory() ,
                                       80 ) );
        ClientConnectionManager cm = new SingleClientConnManager( null ,
                                                                  register );
        HttpClient client = new DefaultHttpClient( cm ,
                                                   null );

        AbstractConfiguration config = ( (MainJFrame) getMain() ).getConfig();
        this.nclient = new JsonNominatimClient( config.getString( "nominatim.url" ) ,
                                                client ,
                                                config.getString( "nominatim.email" ) ,
                                                null ,
                                                false ,
                                                true );

        initComponents();

        initComponents2();
    }

    @Override
    public void finished()
    {
    }

    public void cachePicturesPositions()
    {
        positions.clear();

        for ( Description d : ContentDAOmanager.getInstance().getAllDescriptionsWithGPS() )
        {
            positions.add( new GeoPosition( d.getGPSlat() ,
                                            d.getGPSlng() ) );
        }
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jPNLcontent = new javax.swing.JPanel();
        jSPh = new javax.swing.JSplitPane();
        jSPv = new javax.swing.JSplitPane();
        jPNLplaces = new javax.swing.JPanel();
        jLBLplacesTitle = new javax.swing.JLabel();
        jSPplaces = new javax.swing.JScrollPane();
        jLSTplaces = new javax.swing.JList();
        jPNLplacesButtons = new javax.swing.JPanel();
        jBTremovePlaces = new javax.swing.JButton();
        jPNLplaceEditor = new javax.swing.JPanel();
        jLBLplaceTitle = new javax.swing.JLabel();
        jLBLtitle = new javax.swing.JLabel();
        jTXTtitleValue = new javax.swing.JTextField();
        jLBLgpsLat = new javax.swing.JLabel();
        jTXTgpsLatValue = new javax.swing.JTextField();
        jLBLgpsLng = new javax.swing.JLabel();
        jTXTgpsLngValue = new javax.swing.JTextField();
        jLBLradius = new javax.swing.JLabel();
        jTXTradiusValue = new javax.swing.JTextField();
        jPNLbuttons = new javax.swing.JPanel();
        jBTadd = new javax.swing.JButton();
        jBTmodify = new javax.swing.JButton();
        jLBLsearchTitle = new javax.swing.JLabel();
        jTXTsearchValue = new javax.swing.JTextField();
        jBTsearch = new javax.swing.JButton();
        jLBLoptions = new javax.swing.JLabel();
        jCBshowPictures = new javax.swing.JCheckBox();
        jPNLempty = new javax.swing.JPanel();
        jMP = new org.jdesktop.swingx.JXMapKit();

        jPNLcontent.setLayout(new java.awt.GridBagLayout());

        jSPh.setDividerLocation(300);

        jSPv.setDividerLocation(500);
        jSPv.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPNLplaces.setLayout(new java.awt.GridBagLayout());

        jLBLplacesTitle.setBackground(new java.awt.Color(153, 153, 153));
        jLBLplacesTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLBLplacesTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLBLplacesTitle.setText("Places");
        jLBLplacesTitle.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPNLplaces.add(jLBLplacesTitle, gridBagConstraints);

        jLSTplaces.setModel(placesModel);
        jSPplaces.setViewportView(jLSTplaces);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPNLplaces.add(jSPplaces, gridBagConstraints);

        jPNLplacesButtons.setLayout(new java.awt.GridBagLayout());

        jBTremovePlaces.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btDelete.png"))); // NOI18N
        jBTremovePlaces.setToolTipText("Delete selected");
        jBTremovePlaces.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jBTremovePlaces.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTremovePlacesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPNLplacesButtons.add(jBTremovePlaces, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPNLplaces.add(jPNLplacesButtons, gridBagConstraints);

        jSPv.setLeftComponent(jPNLplaces);

        jPNLplaceEditor.setLayout(new java.awt.GridBagLayout());

        jLBLplaceTitle.setBackground(new java.awt.Color(153, 153, 153));
        jLBLplaceTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLBLplaceTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLBLplaceTitle.setText("Place");
        jLBLplaceTitle.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPNLplaceEditor.add(jLBLplaceTitle, gridBagConstraints);

        jLBLtitle.setText("Titre :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jLBLtitle, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jTXTtitleValue, gridBagConstraints);

        jLBLgpsLat.setText("Lat. :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jLBLgpsLat, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jTXTgpsLatValue, gridBagConstraints);

        jLBLgpsLng.setText("Lng. :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jLBLgpsLng, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jTXTgpsLngValue, gridBagConstraints);

        jLBLradius.setText("Radius (meter) :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jLBLradius, gridBagConstraints);

        jTXTradiusValue.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTXTradiusValueActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jTXTradiusValue, gridBagConstraints);

        jPNLbuttons.setLayout(new java.awt.GridBagLayout());

        jBTadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btAdd.png"))); // NOI18N
        jBTadd.setToolTipText("Add");
        jBTadd.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jBTadd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTaddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLbuttons.add(jBTadd, gridBagConstraints);

        jBTmodify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btModify.png"))); // NOI18N
        jBTmodify.setToolTipText("Add");
        jBTmodify.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jBTmodify.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTmodifyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLbuttons.add(jBTmodify, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridheight = 4;
        jPNLplaceEditor.add(jPNLbuttons, gridBagConstraints);

        jLBLsearchTitle.setBackground(new java.awt.Color(153, 153, 153));
        jLBLsearchTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLBLsearchTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLBLsearchTitle.setText("Search");
        jLBLsearchTitle.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPNLplaceEditor.add(jLBLsearchTitle, gridBagConstraints);

        jTXTsearchValue.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTXTsearchValueActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jTXTsearchValue, gridBagConstraints);

        jBTsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btSearch.png"))); // NOI18N
        jBTsearch.setToolTipText("Search");
        jBTsearch.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jBTsearch.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTsearchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jBTsearch, gridBagConstraints);

        jLBLoptions.setBackground(new java.awt.Color(153, 153, 153));
        jLBLoptions.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLBLoptions.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLBLoptions.setText("Options");
        jLBLoptions.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPNLplaceEditor.add(jLBLoptions, gridBagConstraints);

        jCBshowPictures.setText("Show pictures/videos place");
        jCBshowPictures.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCBshowPicturesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLplaceEditor.add(jCBshowPictures, gridBagConstraints);

        org.jdesktop.layout.GroupLayout jPNLemptyLayout = new org.jdesktop.layout.GroupLayout(jPNLempty);
        jPNLempty.setLayout(jPNLemptyLayout);
        jPNLemptyLayout.setHorizontalGroup(
            jPNLemptyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPNLemptyLayout.setVerticalGroup(
            jPNLemptyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPNLplaceEditor.add(jPNLempty, gridBagConstraints);

        jSPv.setRightComponent(jPNLplaceEditor);

        jSPh.setLeftComponent(jSPv);
        jSPh.setRightComponent(jMP);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPNLcontent.add(jSPh, gridBagConstraints);

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

    private void jBTaddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTaddActionPerformed
    {//GEN-HEADEREND:event_jBTaddActionPerformed

        add();

    }//GEN-LAST:event_jBTaddActionPerformed

    private void jBTsearchActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTsearchActionPerformed
    {//GEN-HEADEREND:event_jBTsearchActionPerformed

        search();

    }//GEN-LAST:event_jBTsearchActionPerformed

    private void jBTremovePlacesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTremovePlacesActionPerformed
    {//GEN-HEADEREND:event_jBTremovePlacesActionPerformed

        delete();

    }//GEN-LAST:event_jBTremovePlacesActionPerformed

    private void jTXTsearchValueActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTXTsearchValueActionPerformed
    {//GEN-HEADEREND:event_jTXTsearchValueActionPerformed

        search();

    }//GEN-LAST:event_jTXTsearchValueActionPerformed

    private void jTXTradiusValueActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTXTradiusValueActionPerformed
    {//GEN-HEADEREND:event_jTXTradiusValueActionPerformed

        jMP.getMainMap().repaint();

    }//GEN-LAST:event_jTXTradiusValueActionPerformed

    private void jBTmodifyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTmodifyActionPerformed
    {//GEN-HEADEREND:event_jBTmodifyActionPerformed

        modify();

    }//GEN-LAST:event_jBTmodifyActionPerformed

    private void jCBshowPicturesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCBshowPicturesActionPerformed
    {//GEN-HEADEREND:event_jCBshowPicturesActionPerformed

        jMP.getMainMap().repaint();

    }//GEN-LAST:event_jCBshowPicturesActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBTadd;
    private javax.swing.JButton jBTmodify;
    private javax.swing.JButton jBTremovePlaces;
    private javax.swing.JButton jBTsearch;
    private javax.swing.JCheckBox jCBshowPictures;
    private javax.swing.JLabel jLBLgpsLat;
    private javax.swing.JLabel jLBLgpsLng;
    private javax.swing.JLabel jLBLoptions;
    private javax.swing.JLabel jLBLplaceTitle;
    private javax.swing.JLabel jLBLplacesTitle;
    private javax.swing.JLabel jLBLradius;
    private javax.swing.JLabel jLBLsearchTitle;
    private javax.swing.JLabel jLBLtitle;
    private javax.swing.JList jLSTplaces;
    private org.jdesktop.swingx.JXMapKit jMP;
    private javax.swing.JPanel jPNLbuttons;
    private javax.swing.JPanel jPNLcontent;
    private javax.swing.JPanel jPNLempty;
    private javax.swing.JPanel jPNLplaceEditor;
    private javax.swing.JPanel jPNLplaces;
    private javax.swing.JPanel jPNLplacesButtons;
    private javax.swing.JSplitPane jSPh;
    private javax.swing.JScrollPane jSPplaces;
    private javax.swing.JSplitPane jSPv;
    private javax.swing.JTextField jTXTgpsLatValue;
    private javax.swing.JTextField jTXTgpsLngValue;
    private javax.swing.JTextField jTXTradiusValue;
    private javax.swing.JTextField jTXTsearchValue;
    private javax.swing.JTextField jTXTtitleValue;
    // End of variables declaration//GEN-END:variables
    // PROTECTED
    // PRIVATE
    private final static int CROSS_RADIUS_SIZE = 10;
    private final static double DEFAULT_RADIUS = 0.01;
    private final static double RADIUS_INCR_BASE = 0.001;
    private final static double RADIUS_INCR_FACTOR = 2.5;
    private final static int PICTURE_RADIUS_SIZE = 4;
    private DefaultListModel placesModel;
    private List<GeoPosition> positions;
    private NominatimClient nclient;

    private void initComponents2()
    {
        // Map viewer
        jMP.setTileFactory( new DefaultTileFactory( new TF_Openstreetmap( ( (MainJFrame) getMain() ).getConfig() ) ) );

        jMP.getMainMap().addMouseMotionListener( new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved( MouseEvent e )
            {
                GeoPosition gp = jMP.getCenterPosition();

                jTXTgpsLatValue.setText( "" + gp.getLatitude() );
                jTXTgpsLngValue.setText( "" + gp.getLongitude() );
            }

            @Override
            public void mouseDragged( MouseEvent e )
            {
                mouseMoved( e );
            }
        } );

        jMP.getMainMap().setOverlayPainter( new Painter()
        {
            public void paint( Graphics2D g2d ,
                               Object t ,
                               int i ,
                               int i1 )
            {
                int centerX = jMP.getMainMap().getWidth() / 2;
                int centerY = jMP.getMainMap().getHeight() / 2;

                String radiusTxt = jTXTradiusValue.getText();
                if ( radiusTxt.length() > 0 )
                {
                    try
                    {
                        // Process radius in GPS (bearing point)
                        double radius = Double.parseDouble( radiusTxt );

                        GeoPosition center = jMP.getMainMap().getCenterPosition();

                        double distanceRad = radius / 6371.0;

                        double lat1 = Math.toRadians( center.getLatitude() );
                        double lng1 = Math.toRadians( center.getLongitude() );

                        double lat2 = Math.asin(
                                Math.sin( lat1 ) * Math.cos( distanceRad )
                                + Math.cos( lat1 ) * Math.sin( distanceRad ) * Math.cos( 0.0 ) );

                        double lng2 = lng1 + Math.atan2(
                                Math.sin( 0.0 ) * Math.sin( distanceRad ) * Math.cos( lat1 ) ,
                                Math.cos( distanceRad ) - Math.sin( lat1 ) * Math.sin( lat2 ) );

                        lng2 = ( lng2 + 3 * Math.PI ) % ( 2 * Math.PI ) - Math.PI;

                        GeoPosition newGP = new GeoPosition( Math.toDegrees( lat2 ) ,
                                                             Math.toDegrees( lng2 ) );

                        // Get pixel distance
                        Point2D p = jMP.getMainMap().convertGeoPositionToPoint( newGP );

                        int pxDistance = Math.abs( centerY - (int) p.getY() );

                        // Draw circle
                        g2d.setColor( new Color( Color.YELLOW.getRed() ,
                                                 Color.YELLOW.getGreen() ,
                                                 Color.YELLOW.getBlue() ,
                                                 64 ) );
                        g2d.fillOval( centerX - pxDistance ,
                                      centerY - pxDistance ,
                                      2 * pxDistance ,
                                      2 * pxDistance );

                        g2d.setColor( Color.YELLOW );
                        g2d.drawOval( centerX - pxDistance ,
                                      centerY - pxDistance ,
                                      2 * pxDistance ,
                                      2 * pxDistance );
                    }
                    catch( NumberFormatException ex )
                    {
                        // Ignore
                    }
                }

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

                // Draw picture points
                if ( jCBshowPictures.isSelected() )
                {
                    for ( GeoPosition position : positions )
                    {
                        Point2D p = jMP.getMainMap().convertGeoPositionToPoint( position );

                        int x = (int) p.getX();
                        int y = (int) p.getY();

                        if ( x >= 0 && x < jMP.getMainMap().getWidth() && y >= 0 && y < jMP.getMainMap().getHeight() )
                        {
                            g2d.setColor( new Color( Color.RED.getRed() ,
                                                     Color.RED.getGreen() ,
                                                     Color.RED.getBlue() ,
                                                     64 ) );

                            g2d.fillOval( x - PICTURE_RADIUS_SIZE ,
                                          y - PICTURE_RADIUS_SIZE ,
                                          2 * PICTURE_RADIUS_SIZE ,
                                          2 * PICTURE_RADIUS_SIZE );

                            g2d.setColor( Color.RED );

                            g2d.drawOval( x - PICTURE_RADIUS_SIZE ,
                                          y - PICTURE_RADIUS_SIZE ,
                                          2 * PICTURE_RADIUS_SIZE ,
                                          2 * PICTURE_RADIUS_SIZE );

                            g2d.drawLine( x ,
                                          y - PICTURE_RADIUS_SIZE ,
                                          x ,
                                          y + PICTURE_RADIUS_SIZE );

                            g2d.drawLine( x - PICTURE_RADIUS_SIZE ,
                                          y ,
                                          x + PICTURE_RADIUS_SIZE ,
                                          y );
                        }
                    }
                }
            }
        } );

        jMP.getMainMap().addKeyListener( new KeyAdapter()
        {
            @Override
            public void keyPressed( KeyEvent e )
            {
                if ( e.getKeyCode() == KeyEvent.VK_CONTROL )
                {
                    jMP.getMainMap().setZoomEnabled( false );
                }
            }

            @Override
            public void keyReleased( KeyEvent e )
            {
                if ( e.getKeyCode() == KeyEvent.VK_CONTROL )
                {
                    jMP.getMainMap().setZoomEnabled( true );
                }
            }
        } );

        jMP.getMainMap().addMouseWheelListener( new MouseWheelListener()
        {
            public void mouseWheelMoved( MouseWheelEvent e )
            {
                if ( !jMP.getMainMap().isZoomEnabled() )
                {
                    double radius;

                    String radiusTxt = jTXTradiusValue.getText();
                    if ( radiusTxt.length() > 0 )
                    {
                        try
                        {
                            radius = Double.parseDouble( radiusTxt );
                        }
                        catch( NumberFormatException ex )
                        {
                            radius = DEFAULT_RADIUS;
                        }
                    }
                    else
                    {
                        radius = DEFAULT_RADIUS;
                    }

                    if ( e.getWheelRotation() > 0 )
                    {
                        radius += RADIUS_INCR_BASE * Math.pow( RADIUS_INCR_FACTOR ,
                                                               jMP.getMainMap().getZoom() );
                    }
                    else
                    {
                        radius -= RADIUS_INCR_BASE * Math.pow( RADIUS_INCR_FACTOR ,
                                                               jMP.getMainMap().getZoom() );
                    }

                    if ( radius <= 0 )
                    {
                        radius = 0;
                    }

                    jTXTradiusValue.setText( "" + radius );

                    jMP.getMainMap().repaint();
                }
            }
        } );

        updateMap();

        // List
        jLSTplaces.setCellRenderer( new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent( JList list ,
                                                           Object value ,
                                                           int index ,
                                                           boolean isSelected ,
                                                           boolean cellHasFocus )
            {
                Component c = super.getListCellRendererComponent( list ,
                                                                  value ,
                                                                  index ,
                                                                  isSelected ,
                                                                  cellHasFocus );

                if ( value instanceof Place )
                {
                    Place p = (Place) value;
                    setText( p.getName() );
                }

                return c;
            }
        } );

        jLSTplaces.getSelectionModel().addListSelectionListener( new ListSelectionListener()
        {
            public void valueChanged( ListSelectionEvent e )
            {
                Place place = (Place) jLSTplaces.getSelectedValue();
                if ( place != null )
                {
                    jTXTtitleValue.setText( place.getName() );
                    jTXTgpsLatValue.setText( "" + place.getGPSlat() );
                    jTXTgpsLngValue.setText( "" + place.getGPSlng() );
                    jTXTradiusValue.setText( "" + place.getRadius() );

                    jBTmodify.setVisible( true );
                }
                else
                {
                    jTXTtitleValue.setText( "" );
//                    jTXTgpsLatValue.setText( "" );
//                    jTXTgpsLngValue.setText( "" );
                    jTXTradiusValue.setText( "" );

                    jBTmodify.setVisible( false );
                }

                updateMap();
            }
        } );

        loadPlaces();
    }

    private void loadPlaces()
    {
        placesModel.clear();
        for ( Place p : ContentDAOmanager.getInstance().getAllPlaces() )
        {
            placesModel.addElement( p );
        }

        if ( placesModel.size() > 0 )
        {
            jLSTplaces.setSelectedIndex( 0 );
        }
        else
        {
            jBTmodify.setVisible( false );
        }
    }

    private void search()
    {
        String address = jTXTsearchValue.getText();
        if ( address.length() <= 0 )
        {
            showWarning( "Address is empty" );
            return;
        }

        try
        {
            List<Address> addresses = nclient.search( address );

            if ( addresses.size() > 0 )
            {
                Address adr = addresses.get( 0 );

                jTXTtitleValue.setText( address );
                jTXTgpsLatValue.setText( "" + adr.getLatitude() );
                jTXTgpsLngValue.setText( "" + adr.getLongitude() );

                updateMap();
            }
        }
        catch( IOException ex )
        {
            throw new RuntimeException( ex );
        }
    }

    private void updateMap()
    {
        if ( jTXTgpsLatValue.getText().length() > 0 && jTXTgpsLngValue.getText().length() > 0 )
        {
            try
            {
                double gpsLat = Double.parseDouble( jTXTgpsLatValue.getText() );
                double gpsLnt = Double.parseDouble( jTXTgpsLngValue.getText() );

                jMP.setAddressLocation( new GeoPosition( gpsLat ,
                                                         gpsLnt ) );

                jMP.setZoom( 3 );
            }
            catch( NumberFormatException ex )
            {
//                jMP.setAddressLocation( null );
            }
        }
        else
        {
//            jMP.setAddressLocation( null );
        }

        jMP.getMainMap().repaint();
    }

    private void add()
    {
        // Check title
        String title = jTXTtitleValue.getText();
        if ( title.length() <= 0 )
        {
            showWarning( "Title is empty" );
            return;
        }

        // Check latitude
        String latStr = jTXTgpsLatValue.getText();
        if ( latStr.length() <= 0 )
        {
            showWarning( "Latitude is empty" );
            return;
        }

        double gpsLat;
        try
        {
            gpsLat = Double.parseDouble( latStr );
        }
        catch( NumberFormatException ex )
        {
            showWarning( "Latitude is not a double" );
            return;
        }

        // Longitude
        String lngStr = jTXTgpsLngValue.getText();
        if ( lngStr.length() <= 0 )
        {
            showWarning( "Longitude is empty" );
            return;
        }

        double gpsLng;
        try
        {
            gpsLng = Double.parseDouble( lngStr );
        }
        catch( NumberFormatException ex )
        {
            showWarning( "Longitude is not a double" );
            return;
        }

        // Radius
        String radiusStr = jTXTradiusValue.getText();
        if ( radiusStr.length() <= 0 )
        {
            showWarning( "Radius is empty" );
            return;
        }

        double radius;
        try
        {
            radius = Double.parseDouble( radiusStr );
        }
        catch( NumberFormatException ex )
        {
            showWarning( "Radius is not a double" );
            return;
        }

        Place p = new Place( title ,
                             gpsLat ,
                             gpsLng ,
                             radius );

        ContentDAOmanager.getInstance().addPlace( p );

        placesModel.addElement( p );

        jLSTplaces.setSelectedIndex( placesModel.getSize() - 1 );

        ( (MainJFrame) getMain() ).reloadPlaces( false );
    }

    private void modify()
    {
        // Check title
        String title = jTXTtitleValue.getText();
        if ( title.length() <= 0 )
        {
            showWarning( "Title is empty" );
            return;
        }

        // Check latitude
        String latStr = jTXTgpsLatValue.getText();
        if ( latStr.length() <= 0 )
        {
            showWarning( "Latitude is empty" );
            return;
        }

        double gpsLat;
        try
        {
            gpsLat = Double.parseDouble( latStr );
        }
        catch( NumberFormatException ex )
        {
            showWarning( "Latitude is not a double" );
            return;
        }

        // Longitude
        String lngStr = jTXTgpsLngValue.getText();
        if ( lngStr.length() <= 0 )
        {
            showWarning( "Longitude is empty" );
            return;
        }

        double gpsLng;
        try
        {
            gpsLng = Double.parseDouble( lngStr );
        }
        catch( NumberFormatException ex )
        {
            showWarning( "Longitude is not a double" );
            return;
        }

        // Radius
        String radiusStr = jTXTradiusValue.getText();
        if ( radiusStr.length() <= 0 )
        {
            showWarning( "Radius is empty" );
            return;
        }

        double radius;
        try
        {
            radius = Double.parseDouble( radiusStr );
        }
        catch( NumberFormatException ex )
        {
            showWarning( "Radius is not a double" );
            return;
        }

        Place p = (Place) placesModel.get( jLSTplaces.getSelectedIndex() );
        if ( p == null )
        {
            throw new NullPointerException();
        }

        p.setName( title );
        p.setGPSlat( gpsLat );
        p.setGPSlng( gpsLng );
        p.setRadius( radius );

        ContentDAOmanager.getInstance().updatePlace( p );

        placesModel.setElementAt( p ,
                                  jLSTplaces.getSelectedIndex() );

        ( (MainJFrame) getMain() ).reloadPlaces( true );
    }

    private void delete()
    {
        if ( getConfirm( "Do you confirm ?" ) )
        {
            TreeSet<Integer> indexes = new TreeSet<Integer>( new Comparator<Integer>()
            {
                public int compare( Integer o1 ,
                                    Integer o2 )
                {
                    return -o1.compareTo( o2 );
                }
            } );

            for ( int ind : jLSTplaces.getSelectedIndices() )
            {
                indexes.add( ind );
            }

            for ( Integer ind : indexes )
            {
                Place p = (Place) placesModel.getElementAt( ind );

                ContentDAOmanager.getInstance().deletePlace( p );
                placesModel.remove( ind );
            }

            if ( placesModel.size() > 0 )
            {
                jLSTplaces.setSelectedIndex( 0 );
            }

            ( (MainJFrame) getMain() ).reloadPlaces( true );
        }
    }
}
