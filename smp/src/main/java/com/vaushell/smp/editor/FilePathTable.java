/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.editor;

import com.vaushell.smp.model.Description;
import com.vaushell.smp.model.Place;
import com.vaushell.tools.images.ImageManager;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class FilePathTable
        extends JXTable
{
    // PUBLIC
    public FilePathTable()
    {
        super();

        init();
    }

    public void reloadComboContent()
    {
        cmbModel.reload();
        
        cmb.setModel( cmbModel );
    }

    @Override
    public void setModel( TableModel dataModel )
    {
        super.setModel( dataModel );

        if ( dataModel != null && getColumnCount() > 9 )
        {
            getColumnModel().getColumn( 9 ).setCellEditor( new DefaultCellEditor( cmb ) );
        }
    }
    // PROTECTED
    // PRIVATE
    private SimpleDateFormat sf;
    private ComboBoxPlacesModel cmbModel;
    private JComboBox cmb;

    private void init()
    {
        this.sf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

        setDefaultRenderer( Calendar.class ,
                            new DefaultTableCellRenderer()
        {
            @Override
            protected void setValue( Object value )
            {
                if ( value == null )
                {
                    setText( null );
                }
                else
                {
                    setText( sf.format( ( (Calendar) value ).getTime() ) );
                }
            }
        } );

        setDefaultRenderer( Description.FilePathType.class ,
                            new DefaultTableCellRenderer()
        {
            @Override
            protected void setValue( Object value )
            {
                setHorizontalAlignment( SwingConstants.CENTER );

                if ( value == null )
                {
                    setText( null );
                }
                else
                {
                    switch( (Description.FilePathType) value )
                    {
                        case VIDEO:
                        {
                            setIcon(
                                    new ImageIcon( ImageManager.getInstance().getImage( "/com/vaushell/smp/icons/movieIcon.png" ) ) );
                            break;
                        }

                        case PICTURE:
                        {
                            setIcon( new ImageIcon( ImageManager.getInstance().
                                    getImage( "/com/vaushell/smp/icons/pictureIcon.png" ) ) );
                            break;
                        }

                        case OTHER:
                        {
                            setIcon(
                                    new ImageIcon( ImageManager.getInstance().getImage( "/com/vaushell/smp/icons/otherIcon.png" ) ) );
                            break;
                        }

                        default:
                        {
                            throw new UnsupportedOperationException();
                        }
                    }
                }
            }
        } );

        this.cmbModel = new ComboBoxPlacesModel();
        this.cmb= new JComboBox( this.cmbModel );
        this.cmb.setRenderer( new DefaultListCellRenderer()
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

        reloadComboContent();
    }
}
