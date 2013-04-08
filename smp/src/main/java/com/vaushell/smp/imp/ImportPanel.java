/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.imp;

import com.vaushell.rc.BaseJFrame;
import com.vaushell.rc.popup.A_PopupPanel;
import com.vaushell.rc.popup.PopupDialog;
import com.vaushell.rc.thread.ModalTaskPopup;
import com.vaushell.smp.MainJFrame;
import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Listing;
import java.io.File;
import java.util.Calendar;
import java.util.Stack;
import javax.swing.SwingUtilities;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class ImportPanel
        extends A_PopupPanel
{
    // PUBLIC
    public ImportPanel( BaseJFrame main )
    {
        super( main ,
               "Import" );

        init();

        initComponents();

        initComponents2();
    }

    @Override
    public void finished()
    {
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jPNLcontent = new javax.swing.JPanel();
        jLBLname = new javax.swing.JLabel();
        jTXTnameValue = new javax.swing.JTextField();
        jLBLsource = new javax.swing.JLabel();
        jFCsourceValue = new com.vaushell.tools.components.file.FileChooserPanel();
        jPNLbuttons = new javax.swing.JPanel();
        jBTok = new javax.swing.JButton();
        jBTcancel = new javax.swing.JButton();

        jPNLcontent.setLayout(new java.awt.GridBagLayout());

        jLBLname.setText("Titre :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLcontent.add(jLBLname, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLcontent.add(jTXTnameValue, gridBagConstraints);

        jLBLsource.setText("Chemin source :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLcontent.add(jLBLsource, gridBagConstraints);

        jFCsourceValue.setOnlyDirectory(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLcontent.add(jFCsourceValue, gridBagConstraints);

        jPNLbuttons.setLayout(new java.awt.GridBagLayout());

        jBTok.setText("OK");
        jBTok.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTokActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPNLbuttons.add(jBTok, gridBagConstraints);

        jBTcancel.setText("Cancel");
        jBTcancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTcancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPNLbuttons.add(jBTcancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 10);
        jPNLcontent.add(jPNLbuttons, gridBagConstraints);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPNLcontent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPNLcontent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBTokActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTokActionPerformed
    {//GEN-HEADEREND:event_jBTokActionPerformed

        doIt();

    }//GEN-LAST:event_jBTokActionPerformed

    private void jBTcancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTcancelActionPerformed
    {//GEN-HEADEREND:event_jBTcancelActionPerformed

        close();

    }//GEN-LAST:event_jBTcancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBTcancel;
    private javax.swing.JButton jBTok;
    private com.vaushell.tools.components.file.FileChooserPanel jFCsourceValue;
    private javax.swing.JLabel jLBLname;
    private javax.swing.JLabel jLBLsource;
    private javax.swing.JPanel jPNLbuttons;
    private javax.swing.JPanel jPNLcontent;
    private javax.swing.JTextField jTXTnameValue;
    // End of variables declaration//GEN-END:variables
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( ImportPanel.class );

    private void init()
    {
    }

    private void initComponents2()
    {
        jFCsourceValue.setFile( new File( "d:/tmp/src/test" ) );
    }

    private void doIt()
    {
        if ( jTXTnameValue.getText().isEmpty() )
        {
            showWarning( "Name is empty" );
            return;
        }

        if ( jFCsourceValue.getFile() == null )
        {
            showWarning( "Source path is empty" );
            return;
        }

        if ( !jFCsourceValue.getFile().isDirectory() )
        {
            showWarning( "Source path is not a directory" );
            return;
        }

        close();

        SwingUtilities.invokeLater( new Runnable()
        {
            public void run()
            {
                new PopupDialog( getMain() ,
                                 new ModalTaskPopup( getMain() ,
                                                     "Import" ,
                                                     null )
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            // Create listing
                            Listing listing = new Listing( jTXTnameValue.getText() ,
                                                           Calendar.getInstance() );

                            listing = ContentDAOmanager.getInstance().addListing( listing );
                            ( (MainJFrame) getMain() ).addAndSelectListing( listing );

                            // Parse content
                            Stack<File> stack = new Stack<File>();
                            stack.push( jFCsourceValue.getFile() );

                            while ( !stack.isEmpty() )
                            {
                                final File actual = stack.pop();

                                if ( actual.isDirectory() )
                                {
                                    for ( File child : actual.listFiles() )
                                    {
                                        stack.push( child );
                                    }
                                }
                                else
                                {
                                    String extension = FilenameUtils.getExtension( actual.getName() ).toLowerCase();
                                    if ( extension.equals( "jpg" ) || extension.equals( "png" ) )
                                    {
                                        ( (MainJFrame) getMain() ).getWorkQueue().addTask(
                                                new ImportPictureTask( actual ,
                                                                       listing ,
                                                                       (MainJFrame) getMain() ) );
                                    }
                                }
                            }
                        }
                        catch( Throwable th )
                        {
                            logger.error( th.getMessage() ,
                                          th );
                        }
                    }
                } ).setVisible( true );
            }
        } );
    }
}
