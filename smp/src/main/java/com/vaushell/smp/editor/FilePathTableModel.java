/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.editor;

import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Description;
import com.vaushell.smp.model.FilePath;
import com.vaushell.smp.model.Person;
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
public class FilePathTableModel
        extends DefaultTableModel
{
    // PUBLIC
    public FilePathTableModel()
    {
        super();

        init();
    }

    public void clear()
    {
        fps.clear();

        fireTableDataChanged();
    }

    public FilePath getAtRow( int row )
    {
        return fps.get( row );
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

    public void addFilePath( FilePath fp )
    {
        fps.add( fp );

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
        return fps.size();
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
        FilePath fp = fps.get( row );

        switch( column )
        {
            case 0:
            {
                return fp.getDescription().getType();
            }

            case 1:
            {
                return fp.getDescription().isUpdated();
            }

            case 2:
            {
                return fp.getDescription().isIgnoreSort();
            }

            case 3:
            {
                return fp.getDescription().getFilesCount();
            }

            case 4:
            {
                return fp.getPath();
            }

            case 5:
            {
                return fp.getName();
            }

            case 6:
            {
                return fp.getDescription().getCreatedDate();
            }

            case 7:
            {
                return fp.getDescription().getGPSlat();
            }

            case 8:
            {
                return fp.getDescription().getGPSlng();
            }

            case 9:
            {
                Place pl = fp.getDescription().getPlace();
                if ( pl == null )
                {
                    return null;
                }
                else
                {
                    return pl.getName();
                }
            }

            case 10:
            {
                return fp.getDescription().getTitle();
            }

            case 11:
            {
                return fp.getDescription().getMake();
            }

            case 12:
            {
                return fp.getDescription().getModel();
            }

            case 13:
            {
                Person pe = fp.getDescription().getPerson();
                if ( pe == null )
                {
                    return null;
                }
                else
                {
                    return pe.getName();
                }
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
        FilePath fp = fps.get( row );

        boolean modified = false;

        switch( column )
        {
            case 2:
            {
                Boolean value = (Boolean) aValue;

                if ( !value.equals( fp.getDescription().isIgnoreSort() ) )
                {
                    fp.getDescription().setIgnoreSort( value );
                    modified = true;
                }

                break;
            }

            case 7:
            {
                Double value = (Double) aValue;

                if ( fp.getDescription().getGPSlat() == null )
                {
                    if ( value != null )
                    {
                        fp.getDescription().setGPSlat( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fp.getDescription().getGPSlat().equals( value ) )
                    {
                        fp.getDescription().setGPSlat( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }

            case 8:
            {
                Double value = (Double) aValue;

                if ( fp.getDescription().getGPSlng() == null )
                {
                    if ( value != null )
                    {
                        fp.getDescription().setGPSlng( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fp.getDescription().getGPSlng().equals( value ) )
                    {
                        fp.getDescription().setGPSlng( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }

            case 9:
            {
                Place value = (Place) aValue;

                if ( fp.getDescription().getPlace() == null )
                {
                    if ( value != null )
                    {
                        fp.getDescription().setPlace( value );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fp.getDescription().getPlace().equals( value ) )
                    {
                        fp.getDescription().setPlace( value );
                        modified = true;
                    }
                }

                break;
            }

            case 10:
            {
                String value = (String) aValue;

                if ( fp.getDescription().getTitle() == null )
                {
                    if ( value != null )
                    {
                        fp.getDescription().setTitle( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fp.getDescription().getTitle().equals( value ) )
                    {
                        fp.getDescription().setTitle( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }

            case 11:
            {
                String value = (String) aValue;

                if ( fp.getDescription().getMake() == null )
                {
                    if ( value != null )
                    {
                        fp.getDescription().setMake( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fp.getDescription().getMake().equals( value ) )
                    {
                        fp.getDescription().setMake( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }

            case 12:
            {
                String value = (String) aValue;

                if ( fp.getDescription().getModel() == null )
                {
                    if ( value != null )
                    {
                        fp.getDescription().setModel( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fp.getDescription().getModel().equals( value ) )
                    {
                        fp.getDescription().setModel( value );
                        fp.getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }
        }

        if ( modified )
        {
            ContentDAOmanager.getInstance().updateDescription( fp.getDescription() );

            fireTableDataChanged();
        }
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
            "Type" , Description.FilePathType.class , false,
        } ,
        {
            "Updated" , Boolean.class , false,
        } ,
        {
            "Ignore sort" , Boolean.class , true,
        } ,
        {
            "Duplicate" , Integer.class , true,
        } ,
        {
            "Path" , String.class , false
        } ,
        {
            "Name" , String.class , false
        } ,
        {
            "Date" , Calendar.class , false,
        } ,
        {
            "Latitude" , Double.class , true,
        } ,
        {
            "Longitude" , Double.class , true,
        } ,
        {
            "Place" , String.class , true,
        } ,
        {
            "Title" , String.class , true,
        } ,
        {
            "Make" , String.class , true,
        } ,
        {
            "Model" , String.class , true,
        } ,
        {
            "Person" , String.class , false,
        }
    };
    private List<FilePath> fps;

    private void init()
    {
        this.fps = new ArrayList<FilePath>();
    }
}
