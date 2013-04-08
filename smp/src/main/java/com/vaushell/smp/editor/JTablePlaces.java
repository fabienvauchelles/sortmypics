/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.editor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class JTablePlaces
        extends JXTable
{
    // PUBLIC
    public JTablePlaces()
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
    }
}
