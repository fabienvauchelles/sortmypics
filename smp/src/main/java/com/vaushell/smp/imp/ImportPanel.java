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
import java.io.File;
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
        jLBLsource = new javax.swing.JLabel();
        jFCsourceValue = new com.vaushell.tools.components.file.FileChooserPanel();
        jLBLignoreSort = new javax.swing.JLabel();
        jCBignoreSortValue = new javax.swing.JCheckBox();
        jPNLbuttons = new javax.swing.JPanel();
        jBTok = new javax.swing.JButton();
        jBTcancel = new javax.swing.JButton();

        jPNLcontent.setLayout(new java.awt.GridBagLayout());

        jLBLsource.setText("Path :");
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

        jLBLignoreSort.setText("Ignore sorting :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLcontent.add(jLBLignoreSort, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLcontent.add(jCBignoreSortValue, gridBagConstraints);

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
    private javax.swing.JCheckBox jCBignoreSortValue;
    private com.vaushell.tools.components.file.FileChooserPanel jFCsourceValue;
    private javax.swing.JLabel jLBLignoreSort;
    private javax.swing.JLabel jLBLsource;
    private javax.swing.JPanel jPNLbuttons;
    private javax.swing.JPanel jPNLcontent;
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
        final File path = jFCsourceValue.getFile();

        if ( path == null )
        {
            showWarning( "Path is empty" );
            return;
        }

        if ( !path.isDirectory() )
        {
            showWarning( "Path is not a directory" );
            return;
        }

        final boolean ignoreSort = jCBignoreSortValue.isSelected();

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
                            // Parse content
                            Stack<File> stack = new Stack<File>();
                            stack.push( path );

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
                                    if ( extension.equals( "jpg" )
                                         || extension.equals( "png" ) )
                                    {
                                        ( (MainJFrame) getMain() ).getWorkQueue().addTask(
                                                new ImportPictureTask( actual ,
                                                                       ignoreSort ,
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
