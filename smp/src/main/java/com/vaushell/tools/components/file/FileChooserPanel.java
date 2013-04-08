/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.components.file;

import com.vaushell.tools.files.FileChooser;
import java.io.File;
import javax.swing.JPanel;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class FileChooserPanel
        extends JPanel
{
    // PUBLIC
    public FileChooserPanel()
    {
        super();

        init();

        initComponents();

        initComponents2();
    }

    public File getFile()
    {
        return file;
    }

    public void setFile( File file )
    {
        this.file = file;
        
        if ( this.file == null )
        {
            jTXTpath.setText( "" );
        }
        else
        {
            jTXTpath.setText( this.file.getAbsolutePath() );
        }
    }

    public boolean isOnlyDirectory()
    {
        return onlyDirectory;
    }

    public void setOnlyDirectory( boolean onlyDirectory )
    {
        this.onlyDirectory = onlyDirectory;
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jTXTpath = new javax.swing.JTextField();
        jTXTchoose = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jTXTpath.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(jTXTpath, gridBagConstraints);

        jTXTchoose.setText("...");
        jTXTchoose.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jTXTchoose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTXTchooseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(jTXTchoose, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jTXTchooseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTXTchooseActionPerformed
    {//GEN-HEADEREND:event_jTXTchooseActionPerformed

        choose();

    }//GEN-LAST:event_jTXTchooseActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jTXTchoose;
    private javax.swing.JTextField jTXTpath;
    // End of variables declaration//GEN-END:variables
    // PROTECTED
    // PRIVATE
    private File file;
    private boolean onlyDirectory;

    private void init()
    {
        this.file = null;
        this.onlyDirectory = false;
    }

    private void initComponents2()
    {
    }

    private void choose()
    {
        File oFile;
        if ( onlyDirectory )
        {
            oFile = FileChooser.chooseDirectory( this ,
                                                file );
        }
        else
        {
            oFile = FileChooser.chooseFile( this ,
                                           file );
        }
        
        setFile( oFile );
    }
}
