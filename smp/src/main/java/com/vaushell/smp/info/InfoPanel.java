/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.info;

import com.vaushell.rc.popup.A_PopupPanel;
import com.vaushell.smp.MainJFrame;
import com.vaushell.tools.exiftool.ExifTool;
import com.vaushell.tools.exiftool.ExifToolManager;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class InfoPanel
        extends A_PopupPanel
{
    // PUBLIC
    public InfoPanel( MainJFrame main ,
                      String filename ,
                      Map<String , String> tags )
    {
        super( main ,
               "Info for '" + filename + "'" );

        init();

        initComponents();

        initComponents2();

        DefaultTableModel model = (DefaultTableModel) jTBinfo.getModel();

        for ( Entry<String , String> entry : tags.entrySet() )
        {
            model.addRow( new Object[]
                    {
                        entry.getKey() , entry.getValue()
                    } );
        }
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
        jSPinfo = new javax.swing.JScrollPane();
        jTBinfo = new javax.swing.JTable();
        jPNLbuttons = new javax.swing.JPanel();
        jBTclose = new javax.swing.JButton();

        jPNLcontent.setLayout(new java.awt.GridBagLayout());

        jTBinfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Key", "Value"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jSPinfo.setViewportView(jTBinfo);
        jTBinfo.getColumnModel().getColumn(1).setResizable(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLcontent.add(jSPinfo, gridBagConstraints);

        jPNLbuttons.setLayout(new java.awt.GridBagLayout());

        jBTclose.setText("Close");
        jBTclose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBTcloseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 10);
        jPNLbuttons.add(jBTclose, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPNLcontent.add(jPNLbuttons, gridBagConstraints);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPNLcontent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPNLcontent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBTcloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBTcloseActionPerformed
    {//GEN-HEADEREND:event_jBTcloseActionPerformed

        close();

    }//GEN-LAST:event_jBTcloseActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBTclose;
    private javax.swing.JPanel jPNLbuttons;
    private javax.swing.JPanel jPNLcontent;
    private javax.swing.JScrollPane jSPinfo;
    private javax.swing.JTable jTBinfo;
    // End of variables declaration//GEN-END:variables

    // PROTECTED
    // PRIVATE
    private void init()
    {
    }

    private void initComponents2()
    {
    }
}
