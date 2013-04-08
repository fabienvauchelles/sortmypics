/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.editor;

import com.vaushell.smp.model.Place;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class PlaceTableModel
        extends DefaultTableModel
{
    // PUBLIC
    public PlaceTableModel()
    {
        super();

        init();
    }

    public void clear()
    {
        places.clear();

        fireTableDataChanged();
    }

    @Override
    public void setDataVector( Vector dataVector ,
                               Vector columnIdentifiers )
    {
//        super.setDataVector( dataVector ,
//                             columnIdentifiers );
    }

    @Override
    public void setDataVector( Object[][] dataVector ,
                               Object[] columnIdentifiers )
    {
//        super.setDataVector( dataVector ,
//                             columnIdentifiers );
    }

    @Override
    public void setNumRows( int rowCount )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRowCount( int rowCount )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addRow( Vector rowData )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addRow( Object[] rowData )
    {
        throw new UnsupportedOperationException();
    }

    public void addPlace( Place place )
    {
        places.add( place );

        fireTableDataChanged();
    }

    @Override
    public void insertRow( int row ,
                           Vector rowData )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertRow( int row ,
                           Object[] rowData )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void moveRow( int start ,
                         int end ,
                         int to )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeRow( int row )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setColumnIdentifiers( Vector columnIdentifiers )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setColumnIdentifiers( Object[] newIdentifiers )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setColumnCount( int columnCount )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addColumn( Object columnName )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addColumn( Object columnName ,
                           Vector columnData )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addColumn( Object columnName ,
                           Object[] columnData )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRowCount()
    {
        return places.size();
    }

    @Override
    public int getColumnCount()
    {
        return descriptor.length;
    }

    @Override
    public String getColumnName( int column )
    {
        return (String) descriptor[ column][0];
    }

    @Override
    public boolean isCellEditable( int row ,
                                   int column )
    {
        return (Boolean) descriptor[ column][ 2];
    }

    @Override
    public Object getValueAt( int row ,
                              int column )
    {
        Place place = places.get( row );

        switch( column )
        {
            case 0:
            {
                return place.getPath();
            }
                
                case 1:
            {
                return place.getDescription().getCreatedDate();
            }

            case 2:
            {
                return place.getDescription().getGPSlat();
            }

            case 3:
            {
                return place.getDescription().getGPSlng();
            }

            case 4:
            {
                return place.getDescription().getGPSalt();
            }

            case 5:
            {
                return place.getDescription().getLocCity();
            }

            case 6:
            {
                return place.getDescription().getLocCountry();
            }

            case 7:
            {
                return place.getDescription().getTitle();
            }

            case 8:
            {
                return place.getDescription().getCaption();
            }

            case 9:
            {
                return place.getDescription().getMake();
            }

            case 10:
            {
                return place.getDescription().getModel();
            }

            case 11:
            {
                return place.getDescription().isUpdated();
            }

            default:
            {
                throw new IllegalStateException();
            }
        }
    }

    @Override
    public void setValueAt( Object aValue ,
                            int row ,
                            int column )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int findColumn( String columnName )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getColumnClass( int columnIndex )
    {
        return (Class<?>) descriptor[ columnIndex][ 1];
    }
    // PROTECTED
    // PRIVATE
    private final static Object[][] descriptor = new Object[][]
    {
        {
            "Path" , String.class , false
        } ,
        {
            "Date" , Calendar.class , false,
        } ,
        {
            "Latitude" , Double.class , false,
        } ,
        {
            "Longitude" , Double.class , false,
        } ,
        {
            "Altitude (m)" , Double.class , false,
        } ,
        {
            "City" , String.class , false,
        } ,
        {
            "Country" , String.class , false,
        } ,
        {
            "Title" , String.class , false,
        } ,
        {
            "Caption" , String.class , false,
        } ,
        {
            "Make" , String.class , false,
        } ,
        {
            "Model" , String.class , false,
        } ,
        {
            "Updated" , Boolean.class , false,
        }
    };
    private List<Place> places;

    private void init()
    {
        this.places = new ArrayList<Place>();
    }
}
