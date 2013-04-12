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
import com.vaushell.smp.info.InfoPanel;
import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Description;
import com.vaushell.smp.model.FilePath;
import com.vaushell.smp.model.Place;
import com.vaushell.smp.places.PlaceViewerPanel;
import com.vaushell.smp.places.PlacesEditorPanel;
import com.vaushell.smp.viewer.PictureViewerPanel;
import com.vaushell.tools.ColorMaker;
import com.vaushell.tools.exiftool.ExifTool;
import com.vaushell.tools.exiftool.ExifToolManager;
import com.vaushell.tools.images.ImageManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.Highlighter;
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
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jBTshowEmpty = new javax.swing.JToggleButton();
        jBTshowGroup = new javax.swing.JToggleButton();
        jPNLtbEmpty = new javax.swing.JPanel();
        jSPfilePaths = new javax.swing.JScrollPane();
        jTBfilePaths = new com.vaushell.smp.editor.FilePathTable();
        jPNLfooter = new javax.swing.JPanel();
        jLBLreference = new javax.swing.JLabel();
        jLBLfilePathsCount = new javax.swing.JLabel();
        jMB = new javax.swing.JMenuBar();
        jMNfile = new javax.swing.JMenu();
        jMIimport = new javax.swing.JMenuItem();
        jMIsave = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMIreset = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMIclose = new javax.swing.JMenuItem();
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
        jTLBmain.add(jSeparator3);

        jBTshowEmpty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btViewEmpty.png"))); // NOI18N
        jBTshowEmpty.setText("View empty");
        jBTshowEmpty.setToolTipText("View empty");
        jBTshowEmpty.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTshowEmptyActionPerformed(evt);
            }
        });
        jTLBmain.add(jBTshowEmpty);

        jBTshowGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btViewGroup.png"))); // NOI18N
        jBTshowGroup.setText("View group");
        jBTshowGroup.setToolTipText("View group");
        jBTshowGroup.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTshowGroupActionPerformed(evt);
            }
        });
        jTLBmain.add(jBTshowGroup);

        jPNLtbEmpty.setLayout(new java.awt.GridBagLayout());
        jTLBmain.add(jPNLtbEmpty);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTLBmain, gridBagConstraints);

        jTBfilePaths.setModel(tmFilePaths);
        jSPfilePaths.setViewportView(jTBfilePaths);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jSPfilePaths, gridBagConstraints);

        jPNLfooter.setLayout(new java.awt.GridBagLayout());

        jLBLreference.setText("no copy reference");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPNLfooter.add(jLBLreference, gridBagConstraints);

        jLBLfilePathsCount.setText("no record found");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
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

        jMIsave.setText("Apply changes");
        jMIsave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMIsaveActionPerformed(evt);
            }
        });
        jMNfile.add(jMIsave);
        jMNfile.add(jSeparator1);

        jMIreset.setText("Reset files");
        jMIreset.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMIresetActionPerformed(evt);
            }
        });
        jMNfile.add(jMIreset);
        jMNfile.add(jSeparator2);

        jMIclose.setText("Close");
        jMIclose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMIcloseActionPerformed(evt);
            }
        });
        jMNfile.add(jMIclose);

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

    private void jMIcloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMIcloseActionPerformed
    {//GEN-HEADEREND:event_jMIcloseActionPerformed

        close();

    }//GEN-LAST:event_jMIcloseActionPerformed

    private void jMIresetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMIresetActionPerformed
    {//GEN-HEADEREND:event_jMIresetActionPerformed

        deleteAllDescriptions();

    }//GEN-LAST:event_jMIresetActionPerformed

    private void jBTshowGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTshowGroupActionPerformed
    {//GEN-HEADEREND:event_jBTshowGroupActionPerformed

        jTBfilePaths.repaint();

    }//GEN-LAST:event_jBTshowGroupActionPerformed

    private void jBTshowEmptyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTshowEmptyActionPerformed
    {//GEN-HEADEREND:event_jBTshowEmptyActionPerformed

        jTBfilePaths.repaint();

    }//GEN-LAST:event_jBTshowEmptyActionPerformed

    private void jMIsaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMIsaveActionPerformed
    {//GEN-HEADEREND:event_jMIsaveActionPerformed
        
        save();
        
    }//GEN-LAST:event_jMIsaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jBTshowEmpty;
    private javax.swing.JToggleButton jBTshowGroup;
    private javax.swing.JToggleButton jBTshowPictureViewer;
    private javax.swing.JToggleButton jBTshowPlaceViewer;
    private javax.swing.JLabel jLBLfilePathsCount;
    private javax.swing.JLabel jLBLreference;
    private javax.swing.JMenuBar jMB;
    private javax.swing.JMenuItem jMIclose;
    private javax.swing.JMenuItem jMIeditPlaces;
    private javax.swing.JMenuItem jMIimport;
    private javax.swing.JMenuItem jMIreset;
    private javax.swing.JMenuItem jMIsave;
    private javax.swing.JMenu jMNfile;
    private javax.swing.JMenu jMNwindow;
    private javax.swing.JPanel jPNLfooter;
    private javax.swing.JPanel jPNLtbEmpty;
    private javax.swing.JScrollPane jSPfilePaths;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
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
    private PopupDialog placesEditorDialog;
    private PlaceViewerPanel placeViewerPanel;
    private PopupFrame placeViewerFrame;
    private FilePathTableModel.FilePathLine reference;

    private void init()
    {
        try
        {
            this.reference = null;
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

        this.placesEditorDialog = new PopupDialog( this ,
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

        jTBfilePaths.addMouseListener( new MouseAdapter()
        {
            @Override
            public void mouseClicked( MouseEvent e )
            {
                if ( e.getButton() == MouseEvent.BUTTON3 )
                {
                    JPopupMenu menu = buildPPmenu();
                    if ( menu != null )
                    {
                        menu.show( jTBfilePaths ,
                                   e.getX() ,
                                   e.getY() );
                    }
                }
            }
        } );

        jTBfilePaths.getActionMap().put( "copy" ,
                                         new AbstractAction()
        {
            public void actionPerformed( ActionEvent e )
            {
                int[] rowsView = jTBfilePaths.getSelectedRows();
                if ( rowsView.length > 0 )
                {
                    copy( rowsView );
                }
            }
        } );
        jTBfilePaths.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_C ,
                                                                Event.CTRL_MASK ) ,
                                        "copy" );

        jTBfilePaths.getActionMap().put( "paste" ,
                                         new AbstractAction()
        {
            public void actionPerformed( ActionEvent e )
            {
                int[] rowsView = jTBfilePaths.getSelectedRows();
                if ( rowsView.length > 0 && reference != null )
                {
                    pasteFilled( rowsView );
                }
            }
        } );
        jTBfilePaths.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_P ,
                                                                Event.CTRL_MASK ) ,
                                        "paste" );

        // Highlights
        jTBfilePaths.setHighlighters( new Highlighter()
        {
            public Component highlight( Component cmpnt ,
                                        ComponentAdapter ca )
            {
                if ( ca.isSelected() )
                {
                    return cmpnt;
                }

                if ( jBTshowEmpty.isSelected()
                     && ( ca.getValue() == null
                          || ( ca.getValue() instanceof String && ( (String) ca.getValue() ).length() <= 0 ) ) )
                {

                    cmpnt.setBackground( Color.ORANGE );

                    return cmpnt;
                }

                if ( jBTshowGroup.isSelected() )
                {
                    int rowModel = jTBfilePaths.convertRowIndexToModel( ca.row );

                    FilePathTableModel.FilePathLine fpl = tmFilePaths.getAtRow( rowModel );

                    Color color = ColorMaker.getBrightColor( fpl.getNum() ,
                                                             400 );

                    cmpnt.setBackground( color );

                    return cmpnt;
                }

                return cmpnt;
            }

            public void addChangeListener( ChangeListener cl )
            {
            }

            public void removeChangeListener( ChangeListener cl )
            {
            }

            public ChangeListener[] getChangeListeners()
            {
                return new ChangeListener[ 0 ];
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

        placesEditorDialog.setVisible( true );
    }

    private void updatePictureInViewer()
            throws IOException
    {
        int rowView = jTBfilePaths.getSelectedRow();
        if ( rowView >= 0 )
        {
            int rowModel = jTBfilePaths.convertRowIndexToModel( rowView );

            FilePathTableModel.FilePathLine fpl = tmFilePaths.getAtRow( rowModel );

            File file = fpl.getFP().getFile();

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

            FilePathTableModel.FilePathLine fpl = tmFilePaths.getAtRow( rowModel );

            Double gpsLat = fpl.getFP().getDescription().getGPSlat();
            Double gpsLng = fpl.getFP().getDescription().getGPSlng();

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

    private void syncGPSplaces( final int[] rowsView )
    {
        new PopupDialog( this ,
                         new ModalTaskPopup( this ,
                                             "Find place for coordinate" ,
                                             null )
        {
            @Override
            public void run()
            {
                try
                {
                    List<Place> places = ContentDAOmanager.getInstance().getAllPlaces();

                    setMax( rowsView.length );

                    int num = 0;
                    for ( int rowView : rowsView )
                    {
                        setLabel( "Process " + num + "/" + rowsView.length );

                        int rowModel = jTBfilePaths.convertRowIndexToModel( rowView );
                        FilePathTableModel.FilePathLine fpl = tmFilePaths.getAtRow( rowModel );

                        for ( Place place : places )
                        {
                            double R = 6371.0;

                            double dLat = Math.toRadians( place.getGPSlat() - fpl.getFP().getDescription().getGPSlat() );
                            double dLng = Math.toRadians( place.getGPSlng() - fpl.getFP().getDescription().getGPSlng() );

                            double lat1 = Math.toRadians( place.getGPSlat() );
                            double lat2 = Math.toRadians( fpl.getFP().getDescription().getGPSlat() );

                            double a = Math.sin( dLat / 2.0 ) * Math.sin( dLat / 2 )
                                       + Math.sin( dLng / 2 ) * Math.sin( dLng / 2 ) * Math.cos( lat1 ) * Math.cos( lat2 );
                            double c = 2 * Math.atan2( Math.sqrt( a ) ,
                                                       Math.sqrt( 1 - a ) );
                            double e = R * c;

                            if ( e <= place.getRadius() )
                            {
                                fpl.getFP().getDescription().setPlace( place );

                                ContentDAOmanager.getInstance().updateDescription( fpl.getFP().getDescription() );
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

    private void syncPlacesGPS( final int[] rowsView )
    {
        new PopupDialog( this ,
                         new ModalTaskPopup( this ,
                                             "Update coordinate with place" ,
                                             null )
        {
            @Override
            public void run()
            {
                try
                {
                    setMax( rowsView.length );

                    int num = 0;
                    for ( int rowView : rowsView )
                    {
                        setLabel( "Process " + num + "/" + rowsView.length );

                        int rowModel = jTBfilePaths.convertRowIndexToModel( rowView );
                        FilePathTableModel.FilePathLine fpl = tmFilePaths.getAtRow( rowModel );

                        if ( fpl.getFP().getDescription().getPlace() != null )
                        {
                            double R = 6371.0;

                            double dLat = Math.toRadians( fpl.getFP().getDescription().getPlace().getGPSlat() - fpl.getFP().
                                    getDescription().
                                    getGPSlat() );
                            double dLng = Math.toRadians( fpl.getFP().getDescription().getPlace().getGPSlng() - fpl.getFP().
                                    getDescription().
                                    getGPSlng() );

                            double lat1 = Math.toRadians( fpl.getFP().getDescription().getPlace().getGPSlat() );
                            double lat2 = Math.toRadians( fpl.getFP().getDescription().getGPSlat() );

                            double a = Math.sin( dLat / 2.0 ) * Math.sin( dLat / 2 )
                                       + Math.sin( dLng / 2 ) * Math.sin( dLng / 2 ) * Math.cos( lat1 ) * Math.cos( lat2 );
                            double c = 2 * Math.atan2( Math.sqrt( a ) ,
                                                       Math.sqrt( 1 - a ) );
                            double e = R * c;

                            if ( e > fpl.getFP().getDescription().getPlace().getRadius() )
                            {
                                fpl.getFP().getDescription().setGPSlat( fpl.getFP().getDescription().getPlace().getGPSlat() );
                                fpl.getFP().getDescription().setGPSlng( fpl.getFP().getDescription().getPlace().getGPSlng() );
                                fpl.getFP().getDescription().setUpdated( true );

                                ContentDAOmanager.getInstance().updateDescription( fpl.getFP().getDescription() );
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

    private JPopupMenu buildPPmenu()
    {
        final int[] rowsView = jTBfilePaths.getSelectedRows();
        if ( rowsView.length <= 0 )
        {
            return null;
        }

        JPopupMenu menu = new JPopupMenu();
        menu.add( new JMenuItem( new AbstractAction( "Copy" )
        {
            public void actionPerformed( ActionEvent e )
            {
                copy( rowsView );
            }
        } ) );


        if ( reference != null )
        {
            menu.add( new JMenuItem( new AbstractAction( "Filled paste" )
            {
                public void actionPerformed( ActionEvent e )
                {
                    pasteFilled( rowsView );
                }
            } ) );

            menu.add( new JMenuItem( new AbstractAction( "Forced paste" )
            {
                public void actionPerformed( ActionEvent e )
                {
                    pasteForced( rowsView );
                }
            } ) );
        }

        menu.addSeparator();

        menu.add( new JMenuItem( new AbstractAction( "Find place for coordinate" )
        {
            public void actionPerformed( ActionEvent e )
            {
                syncGPSplaces( rowsView );
            }
        } ) );

        menu.add( new JMenuItem( new AbstractAction( "Update coordinate with place" )
        {
            public void actionPerformed( ActionEvent e )
            {
                syncPlacesGPS( rowsView );
            }
        } ) );

        menu.addSeparator();

        menu.add( new JMenuItem( new AbstractAction( "Get full info" )
        {
            public void actionPerformed( ActionEvent e )
            {
                SwingUtilities.invokeLater( new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            int rowModel = jTBfilePaths.convertRowIndexToModel( rowsView[ 0] );
                            FilePathTableModel.FilePathLine fpl = tmFilePaths.getAtRow( rowModel );

                            ExifTool tool = ExifToolManager.getInstance().borrowTool();
                            Map<String , String> tags = tool.getAllMetas( fpl.getFP().getFile() );
                            ExifToolManager.getInstance().returnTool( tool );

                            new PopupFrame( MainJFrame.this ,
                                            new InfoPanel( MainJFrame.this ,
                                                           fpl.getFP().getName() ,
                                                           tags ) ).setVisible( true );
                        }
                        catch( Throwable th )
                        {
                            logger.error( th.getMessage() ,
                                          th );
                        }
                    }
                } );
            }
        } ) );


        return menu;
    }

    private void copy( final int[] rowsView )
    {
        int rowModel = jTBfilePaths.convertRowIndexToModel( rowsView[ 0] );

        reference = tmFilePaths.getAtRow( rowModel );

        jLBLreference.setText( String.format( "copy reference is '%s'" ,
                                              reference.getFP().getName() ) );
    }

    private void pasteForced( final int[] rowsView )
    {
        new PopupDialog( this ,
                         new ModalTaskPopup( this ,
                                             "Forced paste" ,
                                             null )
        {
            @Override
            public void run()
            {
                try
                {
                    for ( int rowView : rowsView )
                    {
                        int rowModel = jTBfilePaths.convertRowIndexToModel( rowView );
                        FilePathTableModel.FilePathLine fpl = tmFilePaths.getAtRow( rowModel );

                         tmFilePaths.setDescriptionCreatedDate( reference.getFP().getDescription().getCreatedDate() ,
                                                         fpl );
                        
                        fpl.getFP().getDescription().setGPSlat( reference.getFP().getDescription().getGPSlat() );
                        fpl.getFP().getDescription().setGPSlng( reference.getFP().getDescription().getGPSlng() );
                        fpl.getFP().getDescription().setMake( reference.getFP().getDescription().getMake() );
                        fpl.getFP().getDescription().setModel( reference.getFP().getDescription().getModel() );
                        fpl.getFP().getDescription().setPerson( reference.getFP().getDescription().getPerson() );
                        fpl.getFP().getDescription().setPlace( reference.getFP().getDescription().getPlace() );

                        tmFilePaths.setDescriptionTitle( reference.getFP().getDescription().getTitle() ,
                                                         fpl );

                        fpl.getFP().getDescription().setUpdated( true );

                        ContentDAOmanager.getInstance().updateDescription( fpl.getFP().getDescription() );
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

    private void pasteFilled( final int[] rowsView )
    {
        new PopupDialog( this ,
                         new ModalTaskPopup( this ,
                                             "Filled paste" ,
                                             null )
        {
            @Override
            public void run()
            {
                try
                {
                    for ( int rowView : rowsView )
                    {
                        int rowModel = jTBfilePaths.convertRowIndexToModel( rowView );
                        FilePathTableModel.FilePathLine fpl = tmFilePaths.getAtRow( rowModel );

                        boolean updated = false;
                        if ( fpl.getFP().getDescription().getCreatedDate() == null )
                        {
                            tmFilePaths.setDescriptionCreatedDate( reference.getFP().getDescription().getCreatedDate() ,
                                                             fpl );

                            updated = true;
                        }


                        if ( fpl.getFP().getDescription().getGPSlat() == null && fpl.getFP().getDescription().getGPSlng() == null && fpl.
                                getFP().getDescription().getPlace() == null )
                        {
                            fpl.getFP().getDescription().setGPSlat( reference.getFP().getDescription().getGPSlat() );
                            fpl.getFP().getDescription().setGPSlng( reference.getFP().getDescription().getGPSlng() );
                            fpl.getFP().getDescription().setPlace( reference.getFP().getDescription().getPlace() );

                            updated = true;
                        }

                        if ( fpl.getFP().getDescription().getPerson() == null )
                        {
                            fpl.getFP().getDescription().setPerson( reference.getFP().getDescription().getPerson() );

                            updated = true;
                        }

                        if ( fpl.getFP().getDescription().getTitle() == null
                             || fpl.getFP().getDescription().getTitle().length() <= 0 )
                        {
                            tmFilePaths.setDescriptionTitle( reference.getFP().getDescription().getTitle() ,
                                                             fpl );

                            updated = true;
                        }

                        if ( updated )
                        {
                            fpl.getFP().getDescription().setUpdated( true );
                        }

                        ContentDAOmanager.getInstance().updateDescription( fpl.getFP().getDescription() );
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

    private void deleteAllDescriptions()
    {
        if ( getConfirm( "Do you confirm ?" ) )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                public void run()
                {
                    try
                    {
                        ContentDAOmanager.getInstance().deleteAllDescriptions();
                        tmFilePaths.clear();
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
    
    private void save()
    {
        new PopupDialog( this,
                         new ModalTaskPopup( this,
                                             "Apply changes",
                                             null) {
            @Override
            public void run()
            {
                List<Description> descriptions = ContentDAOmanager.getInstance().getAllDescriptionsUpdated();
                for ( Description description : descriptions )
                {
                }
            }
        }).setVisible( true );
    }
}
