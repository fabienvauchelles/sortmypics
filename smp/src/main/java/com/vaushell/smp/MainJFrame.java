/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp;

import com.vaushell.rc.BaseJFrame;
import com.vaushell.rc.errors.ThrowableManager;
import com.vaushell.rc.popup.PopupDialog;
import com.vaushell.rc.popup.PopupFrame;
import com.vaushell.smp.editor.FilePathTableModel;
import com.vaushell.smp.imp.ImportPanel;
import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.FilePath;
import com.vaushell.smp.viewer.PictureViewerPanel;
import com.vaushell.tools.exiftool.ExifToolManager;
import com.vaushell.tools.images.ImageManager;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
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

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jTLBmain = new javax.swing.JToolBar();
        jBTimport = new javax.swing.JButton();
        jBTshowViewer = new javax.swing.JToggleButton();
        jPNLtbEmpty = new javax.swing.JPanel();
        jSEPcontent = new javax.swing.JSeparator();
        jSPfilePaths = new javax.swing.JScrollPane();
        jTBfilePaths = new com.vaushell.smp.editor.FilePathTable();
        jPNLfooter = new javax.swing.JPanel();
        jLBLfilePathsCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTLBmain.setFloatable(false);
        jTLBmain.setRollover(true);

        jBTimport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btImport.png"))); // NOI18N
        jBTimport.setText("Import");
        jBTimport.setToolTipText("Import");
        jBTimport.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jBTimport.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTimportActionPerformed(evt);
            }
        });
        jTLBmain.add(jBTimport);

        jBTshowViewer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btViewer.png"))); // NOI18N
        jBTshowViewer.setText("Viewer");
        jBTshowViewer.setToolTipText("Viewer");
        jBTshowViewer.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTshowViewerActionPerformed(evt);
            }
        });
        jTLBmain.add(jBTshowViewer);

        jPNLtbEmpty.setLayout(new java.awt.GridBagLayout());
        jTLBmain.add(jPNLtbEmpty);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTLBmain, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jSEPcontent, gridBagConstraints);

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBTimportActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTimportActionPerformed
    {//GEN-HEADEREND:event_jBTimportActionPerformed

        new PopupDialog( this ,
                         new ImportPanel( this ) ).setVisible( true );

    }//GEN-LAST:event_jBTimportActionPerformed

    private void jBTshowViewerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTshowViewerActionPerformed
    {//GEN-HEADEREND:event_jBTshowViewerActionPerformed

        showOrHideViewer();

    }//GEN-LAST:event_jBTshowViewerActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBTimport;
    private javax.swing.JToggleButton jBTshowViewer;
    private javax.swing.JLabel jLBLfilePathsCount;
    private javax.swing.JPanel jPNLfooter;
    private javax.swing.JPanel jPNLtbEmpty;
    private javax.swing.JSeparator jSEPcontent;
    private javax.swing.JScrollPane jSPfilePaths;
    private com.vaushell.smp.editor.FilePathTable jTBfilePaths;
    private javax.swing.JToolBar jTLBmain;
    // End of variables declaration//GEN-END:variables
    // PROTECTED
    // PRIVATE
    private static Logger logger = LoggerFactory.getLogger( MainJFrame.class );
    private FilePathTableModel tmFilePaths;
    private AbstractConfiguration config;
    private PictureViewerPanel viewerPanel;
    private PopupFrame viewerFrame;

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

        this.viewerPanel = new PictureViewerPanel( this );

        this.viewerFrame = new PopupFrame( this ,
                                           this.viewerPanel ,
                                           true )
        {
            @Override
            public void close()
            {
                super.close();
                
                jBTshowViewer.setSelected( false );
            }
        };

        jTBfilePaths.getSelectionModel().addListSelectionListener( new ListSelectionListener()
        {
            public void valueChanged( ListSelectionEvent e )
            {
                if ( viewerFrame.isVisible() )
                {
                    SwingUtilities.invokeLater( new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                updateImageInViewer();
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

    private void showOrHideViewer()
    {
        try
        {
            if ( jBTshowViewer.isSelected() )
            {
                updateImageInViewer();

                viewerFrame.setVisible( true );
            }
            else
            {
                viewerFrame.setVisible( false );
            }
        }
        catch( Throwable th )
        {
            logger.error( th.getMessage() ,
                          th );
        }
    }

    private void updateImageInViewer()
            throws IOException
    {
        int rowView = jTBfilePaths.getSelectedRow();
        final File file;
        if ( rowView >= 0 )
        {
            int rowModel = jTBfilePaths.convertRowIndexToModel( rowView );

            FilePath fp = tmFilePaths.getAtRow( rowModel );

            file = fp.getFile();

            if ( file.exists() )
            {
                viewerPanel.setImage( ImageIO.read( file ) );
            }
            else
            {
                viewerPanel.setImage( null );
            }
        }
        else
        {
            viewerPanel.setImage( null );
        }
    }
}
