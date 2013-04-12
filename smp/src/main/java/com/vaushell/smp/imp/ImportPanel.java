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
import com.vaushell.smp.model.Description;
import java.io.File;
import java.util.HashMap;
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
    private final static Object[][] types = new Object[][]
    {
        {
            "3FR" , Description.FilePathType.PICTURE , false
        } ,
        {
            "ARW" , Description.FilePathType.PICTURE , true
        } ,
        {
            "ASF" , Description.FilePathType.VIDEO , false
        } ,
        {
            "AVI" , Description.FilePathType.VIDEO , false
        } ,
        {
            "BMP" , Description.FilePathType.PICTURE , false
        } ,
        {
            "BTF" , Description.FilePathType.PICTURE , false
        } ,
        {
            "CIFF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "CR2" , Description.FilePathType.PICTURE , true
        } ,
        {
            "CRW" , Description.FilePathType.PICTURE , true
        } ,
        {
            "CS1" , Description.FilePathType.PICTURE , true
        } ,
        {
            "DCR" , Description.FilePathType.PICTURE , false
        } ,
        {
            "DIB" , Description.FilePathType.PICTURE , false
        } ,
        {
            "DIVX" , Description.FilePathType.VIDEO , false
        } ,
        {
            "DNG" , Description.FilePathType.PICTURE , true
        } ,
        {
            "DV" , Description.FilePathType.VIDEO , false
        } ,
        {
            "ERF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "EXIF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "F4V" , Description.FilePathType.VIDEO , false
        } ,
        {
            "FFF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "FLV" , Description.FilePathType.VIDEO , false
        } ,
        {
            "FPX" , Description.FilePathType.PICTURE , false
        } ,
        {
            "GIF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "HDP" , Description.FilePathType.PICTURE , true
        } ,
        {
            "HDR" , Description.FilePathType.PICTURE , false
        } ,
        {
            "IIQ" , Description.FilePathType.PICTURE , true
        } ,
        {
            "J2C" , Description.FilePathType.PICTURE , false
        } ,
        {
            "J2K" , Description.FilePathType.PICTURE , true
        } ,
        {
            "JNG" , Description.FilePathType.PICTURE , true
        } ,
        {
            "JP2" , Description.FilePathType.PICTURE , true
        } ,
        {
            "JPC" , Description.FilePathType.PICTURE , false
        } ,
        {
            "JPEG" , Description.FilePathType.PICTURE , true
        } ,
        {
            "JPF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "JPG" , Description.FilePathType.PICTURE , true
        } ,
        {
            "JPM" , Description.FilePathType.PICTURE , true
        } ,
        {
            "JPX" , Description.FilePathType.PICTURE , true
        } ,
        {
            "K25" , Description.FilePathType.PICTURE , false
        } ,
        {
            "KDC" , Description.FilePathType.PICTURE , false
        } ,
        {
            "M2T" , Description.FilePathType.VIDEO , false
        } ,
        {
            "M2TS" , Description.FilePathType.VIDEO , false
        } ,
        {
            "M2V" , Description.FilePathType.VIDEO , false
        } ,
        {
            "M4V" , Description.FilePathType.VIDEO , false
        } ,
        {
            "MEF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "MNG" , Description.FilePathType.PICTURE , true
        } ,
        {
            "MOS" , Description.FilePathType.PICTURE , true
        } ,
        {
            "MOV" , Description.FilePathType.VIDEO , false
        } ,
        {
            "MP4" , Description.FilePathType.VIDEO , false
        } ,
        {
            "MPEG" , Description.FilePathType.VIDEO , false
        } ,
        {
            "MPG" , Description.FilePathType.VIDEO , false
        } ,
        {
            "MPO" , Description.FilePathType.PICTURE , true
        } ,
        {
            "MQV" , Description.FilePathType.VIDEO , false
        } ,
        {
            "MRW" , Description.FilePathType.PICTURE , true
        } ,
        {
            "MTS" , Description.FilePathType.VIDEO , false
        } ,
        {
            "NEF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "NRW" , Description.FilePathType.PICTURE , true
        } ,
        {
            "ORF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "PCT" , Description.FilePathType.PICTURE , false
        } ,
        {
            "PDF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "PEF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "PGF" , Description.FilePathType.PICTURE , false
        } ,
        {
            "PICT" , Description.FilePathType.PICTURE , false
        } ,
        {
            "PMP" , Description.FilePathType.PICTURE , false
        } ,
        {
            "PNG" , Description.FilePathType.PICTURE , true
        } ,
        {
            "PSB" , Description.FilePathType.PICTURE , true
        } ,
        {
            "PSD" , Description.FilePathType.PICTURE , true
        } ,
        {
            "PSP" , Description.FilePathType.PICTURE , false
        } ,
        {
            "PSPIMAGE" , Description.FilePathType.PICTURE , false
        } ,
        {
            "QIF" , Description.FilePathType.PICTURE , false
        } ,
        {
            "QT" , Description.FilePathType.VIDEO , false
        } ,
        {
            "QTI" , Description.FilePathType.PICTURE , false
        } ,
        {
            "QTIF" , Description.FilePathType.PICTURE , false
        } ,
        {
            "RAF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "RAW" , Description.FilePathType.PICTURE , false
        } ,
        {
            "RM" , Description.FilePathType.VIDEO , false
        } ,
        {
            "RMVB" , Description.FilePathType.VIDEO , false
        } ,
        {
            "RV" , Description.FilePathType.VIDEO , false
        } ,
        {
            "RW2" , Description.FilePathType.PICTURE , true
        } ,
        {
            "RWL" , Description.FilePathType.PICTURE , true
        } ,
        {
            "SR2" , Description.FilePathType.PICTURE , true
        } ,
        {
            "SRF" , Description.FilePathType.PICTURE , false
        } ,
        {
            "SRW" , Description.FilePathType.PICTURE , true
        } ,
        {
            "TIF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "TIFF" , Description.FilePathType.PICTURE , true
        } ,
        {
            "TS" , Description.FilePathType.VIDEO , false
        } ,
        {
            "VOB" , Description.FilePathType.VIDEO , false
        } ,
        {
            "WDP" , Description.FilePathType.PICTURE , true
        } ,
        {
            "WEBM" , Description.FilePathType.VIDEO , false
        } ,
        {
            "WEBP" , Description.FilePathType.PICTURE , false
        } ,
        {
            "WMV" , Description.FilePathType.VIDEO , false
        } ,
        {
            "X3F" , Description.FilePathType.PICTURE , true
        } ,
        {
            "XCF" , Description.FilePathType.PICTURE , false
        },
    };
    private final static HashMap<String , Object[]> extToTypes;

    static
    {
        extToTypes = new HashMap<String , Object[]>();
        for ( int i = 0 ; i < types.length ; i++ )
        {
            extToTypes.put( ( (String) types[ i][ 0] ).toLowerCase() ,
                            types[ i] );
        }
    }

    private void init()
    {
    }

    private void initComponents2()
    {
        jFCsourceValue.setFile( new File( "d:/tmp/src" ) );
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

                                    Object[] typeObj = extToTypes.get( extension );
                                    if ( typeObj != null )
                                    {
                                        Description.FilePathType type = (Description.FilePathType) typeObj[ 1];
                                        ( (MainJFrame) getMain() ).getWorkQueue().addTask(
                                                new ImportTask( actual ,
                                                                type ,
                                                                ignoreSort ,
                                                                (Boolean) typeObj[2] ,
                                                                (MainJFrame) getMain() ) );
                                    }
                                    else
                                    {
                                        ( (MainJFrame) getMain() ).getWorkQueue().addTask(
                                                new ImportTask( actual ,
                                                                Description.FilePathType.OTHER ,
                                                                ignoreSort ,
                                                                false ,
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
