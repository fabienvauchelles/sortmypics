/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp;

import com.vaushell.rc.BaseJFrame;
import com.vaushell.rc.errors.ThrowableManager;
import com.vaushell.rc.popup.PopupDialog;
import com.vaushell.rc.popup.PopupFrame;
import com.vaushell.rc.thread.ModalTaskPopup;
import com.vaushell.smp.editor.FilePathTableModel;
import com.vaushell.smp.imp.ImportPanel;
import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Description;
import com.vaushell.smp.model.FilePath;
import com.vaushell.smp.model.Place;
import com.vaushell.smp.places.PlaceViewerPanel;
import com.vaushell.smp.places.PlacesEditorPanel;
import com.vaushell.smp.viewer.PictureViewerPanel;
import com.vaushell.tools.exiftool.ExifToolManager;
import com.vaushell.tools.images.ImageManager;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class MainJFrame
        extends BaseJFrame
{
    // PUBLIC
    public MainJFrame( String title ,
                       Image image ,
                       int initialThreadCount ,
                       AbstractConfiguration config )
    {
        super( title ,
               image ,
               initialThreadCount );

        this.config = config;

        init();

        initComponents();

        initComponents2();
    }

    public static void main( String[] args )
    {
        try
        {
            final XMLConfiguration config = new XMLConfiguration( "conf/configuration.xml" );

            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );

            java.awt.EventQueue.invokeLater( new Runnable()
            {
                public void run()
                {
                    new MainJFrame( "Sort My Pics 1.0" ,
                                    ImageManager.getInstance().getImage( "/com/vaushell/smp/icons/main.png" ) ,
                                    config.getInt( "thread.count" ) ,
                                    config ).setVisible( true );
                }
            } );
        }
        catch( Throwable th )
        {
            logger.error( th.getMessage() ,
                          th );
        }
    }

    @Override
    public void close()
    {
        try
        {
            ContentDAOmanager.getInstance().stop();

            ExifToolManager.getInstance().stop();
        }
        catch( Exception ex )
        {
            ThrowableManager.getInstance().throwException( ex );
        }

        super.close();
    }

    public void addFilePath( FilePath fp )
    {
        tmFilePaths.addFilePath( fp );

        int count = tmFilePaths.getRowCount();
        if ( count > 1 )
        {
            jLBLfilePathsCount.setText( String.format( "%d records found" ,
                                                       count ) );
        }
        else if ( count == 1 )
        {
            jLBLfilePathsCount.setText( "1 record found" );
        }
        else
        {
            jLBLfilePathsCount.setText( "no record found" );
        }
    }

    public AbstractConfiguration getConfig()
    {
        return config;
    }

    public void reloadPlaces( boolean full )
    {
        jTBfilePaths.reloadComboContent();

        if ( full )
        {
            tmFilePaths.fireTableDataChanged();
        }
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jTLBmain = new javax.swing.JToolBar();
        jBTshowPictureViewer = new javax.swing.JToggleButton();
        jBTshowPlaceViewer = new javax.swing.JToggleButton();
        jBTsyncGPSplaces = new javax.swing.JButton();
        jBTsyncPlacesGPS = new javax.swing.JButton();
        jPNLtbEmpty = new javax.swing.JPanel();
        jSPfilePaths = new javax.swing.JScrollPane();
        jTBfilePaths = new com.vaushell.smp.editor.FilePathTable();
        jPNLfooter = new javax.swing.JPanel();
        jLBLfilePathsCount = new javax.swing.JLabel();
        jMB = new javax.swing.JMenuBar();
        jMNfile = new javax.swing.JMenu();
        jMIimport = new javax.swing.JMenuItem();
        jMNwindow = new javax.swing.JMenu();
        jMIeditPlaces = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTLBmain.setFloatable(false);
        jTLBmain.setRollover(true);

        jBTshowPictureViewer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btViewer.png"))); // NOI18N
        jBTshowPictureViewer.setText("Viewer");
        jBTshowPictureViewer.setToolTipText("Show picture viewer");
        jBTshowPictureViewer.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTshowPictureViewerActionPerformed(evt);
            }
        });
        jTLBmain.add(jBTshowPictureViewer);

        jBTshowPlaceViewer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btMaps.png"))); // NOI18N
        jBTshowPlaceViewer.setText("Map");
        jBTshowPlaceViewer.setToolTipText("Show map");
        jBTshowPlaceViewer.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTshowPlaceViewerActionPerformed(evt);
            }
        });
        jTLBmain.add(jBTshowPlaceViewer);

        jBTsyncGPSplaces.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btSync.png"))); // NOI18N
        jBTsyncGPSplaces.setText("GPS > Places");
        jBTsyncGPSplaces.setToolTipText("Find places within GPS coordinate");
        jBTsyncGPSplaces.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTsyncGPSplacesActionPerformed(evt);
            }
        });
        jTLBmain.add(jBTsyncGPSplaces);

        jBTsyncPlacesGPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btSync.png"))); // NOI18N
        jBTsyncPlacesGPS.setText("GPS > Places");
        jBTsyncPlacesGPS.setToolTipText("Copy places to wrong GPS coordinate");
        jBTsyncPlacesGPS.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTsyncPlacesGPSActionPerformed(evt);
            }
        });
        jTLBmain.add(jBTsyncPlacesGPS);

        jPNLtbEmpty.setLayout(new java.awt.GridBagLayout());
        jTLBmain.add(jPNLtbEmpty);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTLBmain, gridBagConstraints);

        jTBfilePaths.setModel(tmFilePaths);
        jTBfilePaths.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTBfilePaths.setColumnControlVisible(true);
        jSPfilePaths.setViewportView(jTBfilePaths);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jSPfilePaths, gridBagConstraints);

        jPNLfooter.setLayout(new java.awt.GridBagLayout());

        jLBLfilePathsCount.setText("no record found");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPNLfooter.add(jLBLfilePathsCount, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPNLfooter, gridBagConstraints);

        jMNfile.setText("File");

        jMIimport.setText("Import");
        jMIimport.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMIimportActionPerformed(evt);
            }
        });
        jMNfile.add(jMIimport);

        jMB.add(jMNfile);

        jMNwindow.setText("Window");

        jMIeditPlaces.setText("Edit places");
        jMIeditPlaces.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMIeditPlacesActionPerformed(evt);
            }
        });
        jMNwindow.add(jMIeditPlaces);

        jMB.add(jMNwindow);

        setJMenuBar(jMB);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBTshowPictureViewerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTshowPictureViewerActionPerformed
    {//GEN-HEADEREND:event_jBTshowPictureViewerActionPerformed

        showOrHidePictureViewer();

    }//GEN-LAST:event_jBTshowPictureViewerActionPerformed

    private void jMIimportActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMIimportActionPerformed
    {//GEN-HEADEREND:event_jMIimportActionPerformed

        new PopupDialog( this ,
                         new ImportPanel( this ) ).setVisible( true );

    }//GEN-LAST:event_jMIimportActionPerformed

    private void jBTshowPlaceViewerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTshowPlaceViewerActionPerformed
    {//GEN-HEADEREND:event_jBTshowPlaceViewerActionPerformed

        showOrHidePlaceViewer();

    }//GEN-LAST:event_jBTshowPlaceViewerActionPerformed

    private void jMIeditPlacesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMIeditPlacesActionPerformed
    {//GEN-HEADEREND:event_jMIeditPlacesActionPerformed

        showOrHideEditPlaces();

    }//GEN-LAST:event_jMIeditPlacesActionPerformed

    private void jBTsyncGPSplacesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTsyncGPSplacesActionPerformed
    {//GEN-HEADEREND:event_jBTsyncGPSplacesActionPerformed

        syncGPSplaces();

    }//GEN-LAST:event_jBTsyncGPSplacesActionPerformed

    private void jBTsyncPlacesGPSActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTsyncPlacesGPSActionPerformed
    {//GEN-HEADEREND:event_jBTsyncPlacesGPSActionPerformed

        syncPlacesGPS();

    }//GEN-LAST:event_jBTsyncPlacesGPSActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jBTshowPictureViewer;
    private javax.swing.JToggleButton jBTshowPlaceViewer;
    private javax.swing.JButton jBTsyncGPSplaces;
    private javax.swing.JButton jBTsyncPlacesGPS;
    private javax.swing.JLabel jLBLfilePathsCount;
    private javax.swing.JMenuBar jMB;
    private javax.swing.JMenuItem jMIeditPlaces;
    private javax.swing.JMenuItem jMIimport;
    private javax.swing.JMenu jMNfile;
    private javax.swing.JMenu jMNwindow;
    private javax.swing.JPanel jPNLfooter;
    private javax.swing.JPanel jPNLtbEmpty;
    private javax.swing.JScrollPane jSPfilePaths;
    private com.vaushell.smp.editor.FilePathTable jTBfilePaths;
    private javax.swing.JToolBar jTLBmain;
    // End of variables declaration//GEN-END:variables
    // PROTECTED
    // PRIVATE
    private static Logger logger = LoggerFactory.getLogger( MainJFrame.class );
    private FilePathTableModel tmFilePaths;
    private AbstractConfiguration config;
    private PictureViewerPanel pictureViewerPanel;
    private PopupFrame pictureViewerFrame;
    private PlacesEditorPanel placesEditorPanel;
    private PopupFrame placesEditorFrame;
    private PlaceViewerPanel placeViewerPanel;
    private PopupFrame placeViewerFrame;

    private void init()
    {
        try
        {
            this.tmFilePaths = new FilePathTableModel();

            ContentDAOmanager.getInstance().start();

            ExifToolManager.getInstance().start( config.getString( "exiftool.path" ) );
        }
        catch( Throwable th )
        {
            logger.error( th.getMessage() ,
                          th );
        }
    }

    private void initComponents2()
    {
        loadAllFilePaths();

        // Viewer
        this.pictureViewerPanel = new PictureViewerPanel( this );

        this.pictureViewerFrame = new PopupFrame( this ,
                                                  this.pictureViewerPanel ,
                                                  true )
        {
            @Override
            public void close()
            {
                super.close();

                jBTshowPictureViewer.setSelected( false );
            }
        };

        // Viewer
        this.placeViewerPanel = new PlaceViewerPanel( this );

        this.placeViewerFrame = new PopupFrame( this ,
                                                this.placeViewerPanel ,
                                                true )
        {
            @Override
            public void close()
            {
                super.close();

                jBTshowPlaceViewer.setSelected( false );
            }
        };

        // Edit places
        this.placesEditorPanel = new PlacesEditorPanel( this );

        this.placesEditorFrame = new PopupFrame( this ,
                                                 this.placesEditorPanel ,
                                                 true );

        // Filepath
        jTBfilePaths.getSelectionModel().addListSelectionListener( new ListSelectionListener()
        {
            public void valueChanged( ListSelectionEvent e )
            {
                if ( pictureViewerFrame.isVisible() )
                {
                    SwingUtilities.invokeLater( new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                updatePictureInViewer();
                            }
                            catch( Throwable th )
                            {
                                logger.error( th.getMessage() ,
                                              th );
                            }
                        }
                    } );
                }

                if ( placeViewerFrame.isVisible() )
                {
                    SwingUtilities.invokeLater( new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                updatePlaceInViewer();
                            }
                            catch( Throwable th )
                            {
                                logger.error( th.getMessage() ,
                                              th );
                            }
                        }
                    } );
                }
            }
        } );
    }

    private void loadAllFilePaths()
    {
        tmFilePaths.clear();

        // Do it in a thread
        for ( FilePath fp : ContentDAOmanager.getInstance().getAllFilePathsAndDescription() )
        {
            addFilePath( fp );
        }
    }

    private void showOrHidePictureViewer()
    {
        try
        {
            if ( jBTshowPictureViewer.isSelected() )
            {
                updatePictureInViewer();

                pictureViewerFrame.setVisible( true );
            }
            else
            {
                pictureViewerFrame.setVisible( false );
            }
        }
        catch( Throwable th )
        {
            logger.error( th.getMessage() ,
                          th );
        }
    }

    private void showOrHidePlaceViewer()
    {
        try
        {
            if ( jBTshowPlaceViewer.isSelected() )
            {
                updatePlaceInViewer();

                placeViewerFrame.setVisible( true );
            }
            else
            {
                placeViewerFrame.setVisible( false );
            }
        }
        catch( Throwable th )
        {
            logger.error( th.getMessage() ,
                          th );
        }
    }

    private void showOrHideEditPlaces()
    {
        placesEditorPanel.cachePicturesPositions();

        placesEditorFrame.setVisible( true );
    }

    private void updatePictureInViewer()
            throws IOException
    {
        int rowView = jTBfilePaths.getSelectedRow();
        if ( rowView >= 0 )
        {
            int rowModel = jTBfilePaths.convertRowIndexToModel( rowView );

            FilePath fp = tmFilePaths.getAtRow( rowModel );

            File file = fp.getFile();

            if ( file.exists() )
            {
                pictureViewerPanel.setImage( ImageIO.read( file ) );
            }
            else
            {
                pictureViewerPanel.setImage( null );
            }
        }
        else
        {
            pictureViewerPanel.setImage( null );
        }
    }

    private void updatePlaceInViewer()
    {
        int rowView = jTBfilePaths.getSelectedRow();
        if ( rowView >= 0 )
        {
            int rowModel = jTBfilePaths.convertRowIndexToModel( rowView );

            FilePath fp = tmFilePaths.getAtRow( rowModel );

            Double gpsLat = fp.getDescription().getGPSlat();
            Double gpsLng = fp.getDescription().getGPSlng();

            if ( gpsLat != null && gpsLng != null )
            {
                placeViewerPanel.setAddressLocation( new GeoPosition( gpsLat ,
                                                                      gpsLng ) );
            }
            else
            {
                placeViewerPanel.setAddressLocation( null );
            }
        }
        else
        {
            placeViewerPanel.setAddressLocation( null );
        }
    }

    private void syncGPSplaces()
    {
        new PopupDialog( this ,
                         new ModalTaskPopup( this ,
                                             "Find" ,
                                             null )
        {
            @Override
            public void run()
            {
                try
                {
                    List<Place> places = ContentDAOmanager.getInstance().getAllPlaces();
                    List<Description> descriptions = ContentDAOmanager.getInstance().getAllDescriptionsWithGPS();

                    setMax( descriptions.size() );

                    int num = 0;
                    for ( Description d : descriptions )
                    {
                        setLabel( "Process " + num + "/" + descriptions.size() );

                        for ( Place place : places )
                        {
                            double R = 6371.0;

                            double dLat = Math.toRadians( place.getGPSlat() - d.getGPSlat() );
                            double dLng = Math.toRadians( place.getGPSlng() - d.getGPSlng() );

                            double lat1 = Math.toRadians( place.getGPSlat() );
                            double lat2 = Math.toRadians( d.getGPSlat() );

                            double a = Math.sin( dLat / 2.0 ) * Math.sin( dLat / 2 )
                                       + Math.sin( dLng / 2 ) * Math.sin( dLng / 2 ) * Math.cos( lat1 ) * Math.cos( lat2 );
                            double c = 2 * Math.atan2( Math.sqrt( a ) ,
                                                       Math.sqrt( 1 - a ) );
                            double e = R * c;

                            if ( e <= place.getRadius() )
                            {
                                d.setPlace( place );

                                ContentDAOmanager.getInstance().updateDescription( d );
                                break;
                            }
                        }

                        ++num;
                    }

                    tmFilePaths.fireTableDataChanged();
                }
                catch( Throwable th )
                {
                    logger.error( th.getMessage() ,
                                  th );
                }
            }
        } ).setVisible( true );
    }

    private void syncPlacesGPS()
    {
        new PopupDialog( this ,
                         new ModalTaskPopup( this ,
                                             "Copy" ,
                                             null )
        {
            @Override
            public void run()
            {
                try
                {
                    List<Description> descriptions = ContentDAOmanager.getInstance().getAllDescriptionsWithGPS();

                    setMax( descriptions.size() );

                    int num = 0;
                    for ( Description d : descriptions )
                    {
                        setLabel( "Process " + num + "/" + descriptions.size() );

                        if ( d.getPlace() != null )
                        {
                            double R = 6371.0;

                            double dLat = Math.toRadians( d.getPlace().getGPSlat() - d.getGPSlat() );
                            double dLng = Math.toRadians( d.getPlace().getGPSlng() - d.getGPSlng() );

                            double lat1 = Math.toRadians( d.getPlace().getGPSlat() );
                            double lat2 = Math.toRadians( d.getGPSlat() );

                            double a = Math.sin( dLat / 2.0 ) * Math.sin( dLat / 2 )
                                       + Math.sin( dLng / 2 ) * Math.sin( dLng / 2 ) * Math.cos( lat1 ) * Math.cos( lat2 );
                            double c = 2 * Math.atan2( Math.sqrt( a ) ,
                                                       Math.sqrt( 1 - a ) );
                            double e = R * c;

                            if ( e > d.getPlace().getRadius() )
                            {
                                d.setGPSlat( d.getPlace().getGPSlat() );
                                d.setGPSlng( d.getPlace().getGPSlng() );
                                d.setUpdated( true );

                                ContentDAOmanager.getInstance().updateDescription( d );
                                break;
                            }
                        }

                        ++num;
                    }

                    tmFilePaths.fireTableDataChanged();
                }
                catch( Throwable th )
                {
                    logger.error( th.getMessage() ,
                                  th );
                }
            }
        } ).setVisible( true );
    }
}
