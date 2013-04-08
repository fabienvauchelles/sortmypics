/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.editor;

import com.vaushell.smp.model.Description;
import com.vaushell.tools.images.ImageManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
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
    // PROTECTED
    // PRIVATE
    private SimpleDateFormat sf;
    
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
    }
}
