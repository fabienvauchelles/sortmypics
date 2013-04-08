/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp;

import com.vaushell.rc.BaseJFrame;
import com.vaushell.rc.errors.ThrowableManager;
import com.vaushell.rc.popup.PopupDialog;
import com.vaushell.smp.editor.PlaceTableModel;
import com.vaushell.smp.imp.ImportPanel;
import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Listing;
import com.vaushell.smp.model.Place;
import com.vaushell.tools.exiftool.ExifToolManager;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.UIManager;
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
                    try
                    {
                        new MainJFrame( "Sort My Pics 1.0" ,
                                        ImageIO.read( getClass().getResource( "/com/vaushell/smp/icons/main.png" ) ) ,
                                        config.getInt( "thread.count" ) ,
                                        config ).setVisible( true );
                    }
                    catch( IOException ex )
                    {
                        throw new RuntimeException( ex );
                    }
                }
            } );
        }
        catch( Throwable ex )
        {
            ex.printStackTrace();
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

    public void addAndSelectListing( Listing listing )
    {
        DefaultComboBoxModel model = (DefaultComboBoxModel) jCMBlisting.getModel();
        model.addElement( listing );

        jCMBlisting.setSelectedItem( listing );
    }

    public void addPlace( Place place )
    {
        placeModel.addPlace( place );
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jTB = new javax.swing.JToolBar();
        jCMBlisting = new javax.swing.JComboBox();
        jBTdelete = new javax.swing.JButton();
        jBTimport = new javax.swing.JButton();
        jPNLtbEmpty = new javax.swing.JPanel();
        jSEPcontent = new javax.swing.JSeparator();
        jPNLcontent = new javax.swing.JPanel();
        jPNLcontentEmpty = new javax.swing.JPanel();
        jLBLempty = new javax.swing.JLabel();
        jSPplaces = new javax.swing.JScrollPane();
        jTBplaces = new com.vaushell.smp.editor.JTablePlaces();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTB.setFloatable(false);
        jTB.setRollover(true);

        jCMBlisting.setMinimumSize(new java.awt.Dimension(256, 20));
        jCMBlisting.setPreferredSize(new java.awt.Dimension(256, 22));
        jTB.add(jCMBlisting);

        jBTdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btDelete.png"))); // NOI18N
        jBTdelete.setToolTipText("Delete import");
        jBTdelete.setFocusable(false);
        jBTdelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBTdelete.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jBTdelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBTdelete.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTdeleteActionPerformed(evt);
            }
        });
        jTB.add(jBTdelete);

        jBTimport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vaushell/smp/icons/btImport.png"))); // NOI18N
        jBTimport.setToolTipText("Import");
        jBTimport.setFocusable(false);
        jBTimport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBTimport.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jBTimport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBTimport.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTimportActionPerformed(evt);
            }
        });
        jTB.add(jBTimport);

        jPNLtbEmpty.setLayout(new java.awt.GridBagLayout());
        jTB.add(jPNLtbEmpty);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTB, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jSEPcontent, gridBagConstraints);

        jPNLcontent.setLayout(new java.awt.CardLayout());

        jPNLcontentEmpty.setLayout(new java.awt.GridBagLayout());

        jLBLempty.setText("No import selected");
        jPNLcontentEmpty.add(jLBLempty, new java.awt.GridBagConstraints());

        jPNLcontent.add(jPNLcontentEmpty, "empty");

        jTBplaces.setModel(placeModel);
        jTBplaces.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTBplaces.setColumnControlVisible(true);
        jSPplaces.setViewportView(jTBplaces);

        jPNLcontent.add(jSPplaces, "places");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPNLcontent, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBTdeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTdeleteActionPerformed
    {//GEN-HEADEREND:event_jBTdeleteActionPerformed

        deleteImport();

    }//GEN-LAST:event_jBTdeleteActionPerformed

    private void jBTimportActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTimportActionPerformed
    {//GEN-HEADEREND:event_jBTimportActionPerformed

        new PopupDialog( this ,
                         new ImportPanel( this ) ).setVisible( true );

    }//GEN-LAST:event_jBTimportActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBTdelete;
    private javax.swing.JButton jBTimport;
    private javax.swing.JComboBox jCMBlisting;
    private javax.swing.JLabel jLBLempty;
    private javax.swing.JPanel jPNLcontent;
    private javax.swing.JPanel jPNLcontentEmpty;
    private javax.swing.JPanel jPNLtbEmpty;
    private javax.swing.JSeparator jSEPcontent;
    private javax.swing.JScrollPane jSPplaces;
    private javax.swing.JToolBar jTB;
    private com.vaushell.smp.editor.JTablePlaces jTBplaces;
    // End of variables declaration//GEN-END:variables
    // PROTECTED
    // PRIVATE
    private static Logger logger = LoggerFactory.getLogger( MainJFrame.class );
    private PlaceTableModel placeModel;
    private AbstractConfiguration config;

    private void init()
    {
        try
        {
            this.placeModel = new PlaceTableModel();

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
        // Listing
        jCMBlisting.setRenderer( new DefaultListCellRenderer()
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

                if ( value instanceof Listing )
                {
                    setText( ( (Listing) value ).getName() );
                }

                return c;
            }
        } );
        jCMBlisting.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                loadListing( (Listing) jCMBlisting.getSelectedItem() );
            }
        } );
        loadAllListings();
    }

    private void loadAllListings()
    {
        DefaultComboBoxModel model = (DefaultComboBoxModel) jCMBlisting.getModel();

        model.removeAllElements();
        for ( Listing l : ContentDAOmanager.getInstance().getAllListings() )
        {
            model.addElement( l );
        }
    }

    private void loadListing( Listing l )
    {
        CardLayout layout = (CardLayout) jPNLcontent.getLayout();
        if ( l == null )
        {
            layout.show( jPNLcontent ,
                         "empty" );
        }
        else
        {
            placeModel.clear();

            layout.show( jPNLcontent ,
                         "places" );

            // Do it in a thread
            for ( Place p : ContentDAOmanager.getInstance().getAllPlacesAndDescritorsForListing( l ) )
            {
                placeModel.addPlace( p );
            }
        }
    }

    private void deleteImport()
    {
        Listing listing = (Listing) jCMBlisting.getSelectedItem();
        if ( listing != null )
        {
            if ( getConfirm( "Do you want to delete import '" + listing.getName() + "' ?" ) )
            {
                ContentDAOmanager.getInstance().deleteListing( listing );

                jCMBlisting.removeItem( listing );
            }
        }
    }
}
