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
import com.vaushell.tools.SafeCompare;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
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
        synchronized( fplsSorted )
        {
            fpls.clear();
            fplsSorted.clear();
        }

        fireTableDataChanged();
    }

    public FilePathLine getAtRow( int row )
    {
        return fpls.get( row );
    }

    public void setDescriptionCreatedDate( Calendar newCreatedDate ,
                                           FilePathLine fpl )
    {
        fplsSorted.remove( fpl );
        fpl.getFP().getDescription().setCreatedDate( newCreatedDate );
        fplsSorted.add( fpl );

        processColor();
    }

    public void setDescriptionTitle( String newTitle ,
                                     FilePathLine fpl )
    {
        fplsSorted.remove( fpl );
        fpl.getFP().getDescription().setTitle( newTitle );
        fplsSorted.add( fpl );

        processColor();
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
        synchronized( fplsSorted )
        {
            FilePathLine fpl = new FilePathLine( fp );
            fpls.add( fpl );
            fplsSorted.add( fpl );
            processColor();
        }

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
        return fpls.size();
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
        FilePathLine fpl = fpls.get( row );

        switch( column )
        {
            case 0:
            {
                return fpl.getFP().getDescription().getType();
            }

            case 1:
            {
                return fpl.getFP().getDescription().isUpdated();
            }

            case 2:
            {
                return fpl.getFP().getDescription().isIgnoreSort();
            }

            case 3:
            {
                return fpl.getFP().getDescription().isWritable();
            }

            case 4:
            {
                return fpl.getFP().getDescription().getFilesCount();
            }

            case 5:
            {
                return fpl.getFP().getSourcePath();
            }

            case 6:
            {
                return fpl.getFP().getDestinationPath();
            }

            case 7:
            {
                return fpl.getFP().getName();
            }

            case 8:
            {
                Calendar c = fpl.getFP().getDescription().getCreatedDate();
                if ( c == null )
                {
                    return null;
                }
                else
                {
                    return c.getTime();
                }
            }

            case 9:
            {
                return fpl.getFP().getDescription().getGPSlat();
            }

            case 10:
            {
                return fpl.getFP().getDescription().getGPSlng();
            }

            case 11:
            {
                Place pl = fpl.getFP().getDescription().getPlace();
                if ( pl == null )
                {
                    return null;
                }
                else
                {
                    return pl.getName();
                }
            }

            case 12:
            {
                return fpl.getFP().getDescription().getTitle();
            }

            case 13:
            {
                return fpl.getFP().getDescription().getMake();
            }

            case 14:
            {
                return fpl.getFP().getDescription().getModel();
            }

            case 15:
            {
                Person pe = fpl.getFP().getDescription().getPerson();
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
        FilePathLine fpl = fpls.get( row );

        boolean modified = false;

        switch( column )
        {
            case 2:
            {
                Boolean value = (Boolean) aValue;

                if ( !value.equals( fpl.getFP().getDescription().isIgnoreSort() ) )
                {
                    fpl.getFP().getDescription().setIgnoreSort( value );
                    modified = true;
                }

                break;
            }

            case 8:
            {
                Date value = (Date) aValue;

                if ( fpl.getFP().getDescription().getCreatedDate() == null )
                {
                    if ( value != null )
                    {
                        Calendar c = Calendar.getInstance();
                        c.setTime( value );

                        setDescriptionCreatedDate( c ,
                                                   fpl );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    Calendar c = Calendar.getInstance();
                    c.setTime( value );

                    if ( !fpl.getFP().getDescription().getCreatedDate().equals( c ) )
                    {
                        setDescriptionCreatedDate( c ,
                                                   fpl );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }

            case 9:
            {
                Double value = (Double) aValue;

                if ( fpl.getFP().getDescription().getGPSlat() == null )
                {
                    if ( value != null )
                    {
                        fpl.getFP().getDescription().setGPSlat( value );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fpl.getFP().getDescription().getGPSlat().equals( value ) )
                    {
                        fpl.getFP().getDescription().setGPSlat( value );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }

            case 10:
            {
                Double value = (Double) aValue;

                if ( fpl.getFP().getDescription().getGPSlng() == null )
                {
                    if ( value != null )
                    {
                        fpl.getFP().getDescription().setGPSlng( value );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fpl.getFP().getDescription().getGPSlng().equals( value ) )
                    {
                        fpl.getFP().getDescription().setGPSlng( value );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }

            case 11:
            {
                Place value = (Place) aValue;

                if ( fpl.getFP().getDescription().getPlace() == null )
                {
                    if ( value != null )
                    {
                        fpl.getFP().getDescription().setPlace( value );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fpl.getFP().getDescription().getPlace().equals( value ) )
                    {
                        fpl.getFP().getDescription().setPlace( value );
                        modified = true;
                    }
                }

                break;
            }

            case 12:
            {
                String value = (String) aValue;

                if ( fpl.getFP().getDescription().getTitle() == null )
                {
                    if ( value != null )
                    {
                        setDescriptionTitle( value ,
                                             fpl );
                        fpl.getFP().getDescription().setUpdated( true );

                        modified = true;
                    }
                }
                else
                {
                    if ( !fpl.getFP().getDescription().getTitle().equals( value ) )
                    {
                        setDescriptionTitle( value ,
                                             fpl );
                        fpl.getFP().getDescription().setUpdated( true );

                        modified = true;
                    }
                }

                break;
            }

            case 13:
            {
                String value = (String) aValue;

                if ( fpl.getFP().getDescription().getMake() == null )
                {
                    if ( value != null )
                    {
                        fpl.getFP().getDescription().setMake( value );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fpl.getFP().getDescription().getMake().equals( value ) )
                    {
                        fpl.getFP().getDescription().setMake( value );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }

            case 14:
            {
                String value = (String) aValue;

                if ( fpl.getFP().getDescription().getModel() == null )
                {
                    if ( value != null )
                    {
                        fpl.getFP().getDescription().setModel( value );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }
                else
                {
                    if ( !fpl.getFP().getDescription().getModel().equals( value ) )
                    {
                        fpl.getFP().getDescription().setModel( value );
                        fpl.getFP().getDescription().setUpdated( true );
                        modified = true;
                    }
                }

                break;
            }
        }

        if ( modified )
        {
            ContentDAOmanager.getInstance().updateDescription( fpl.getFP().getDescription() );

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

    public class FilePathLine
    {
        // PUBLIC
        public FilePathLine( FilePath fp )
        {
            this.fp = fp;
            this.num = Integer.MIN_VALUE;
        }

        public boolean sameGroup( FilePathLine o )
        {
            if ( !isNear( getFP().getDescription().getCreatedDate() ,
                          o.getFP().getDescription().getCreatedDate() ) )
            {
                return false;
            }

            if ( SafeCompare.compareTo( getFP().getDescription().getTitle() ,
                                        o.getFP().getDescription().getTitle() ) != 0 )
            {
                return false;
            }

            return true;
        }

        public FilePath getFP()
        {
            return fp;
        }

        public void setFP( FilePath fp )
        {
            this.fp = fp;
        }

        public int getNum()
        {
            return num;
        }

        public void setNum( int num )
        {
            this.num = num;
        }
        // PRIVATE
        private FilePath fp;
        private int num;
    }

    public Object[][] getDescriptor()
    {
        return descriptor;
    }
    // PROTECTED
    // PRIVATE
    private final static Object[][] descriptor = new Object[][]
    {
        {
            "T" , Description.FilePathType.class , false , 30
        } ,
        {
            "M" , Boolean.class , false , 30
        } ,
        {
            "S" , Boolean.class , true , 30
        } ,
        {
            "W" , Boolean.class , true , 30
        } ,
        {
            "Clone" , Integer.class , true , 40
        } ,
        {
            "SRC path" , String.class , false , null
        } ,
        {
            "DST path" , String.class , false , null
        } ,
        {
            "Name" , String.class , false , null
        } ,
        {
            "Date" , Date.class , true , 120
        } ,
        {
            "Latitude" , Double.class , true , 100
        } ,
        {
            "Longitude" , Double.class , true , 100
        } ,
        {
            "Place" , String.class , true , null
        } ,
        {
            "Title" , String.class , true , null
        } ,
        {
            "Make" , String.class , true , null
        } ,
        {
            "Model" , String.class , true , null
        } ,
        {
            "Person" , String.class , false , null
        }
    };
    private List<FilePathLine> fpls;
    private final TreeSet<FilePathLine> fplsSorted = new TreeSet<FilePathLine>( new Comparator<FilePathLine>()
    {
        public int compare( FilePathLine o1 ,
                            FilePathLine o2 )
        {
            if ( isNear( o1.getFP().getDescription().getCreatedDate() ,
                         o2.getFP().getDescription().getCreatedDate() ) )
            {
                int cmp = SafeCompare.compareTo( o1.getFP().getDescription().getTitle() ,
                                                 o2.getFP().getDescription().getTitle() );
                if ( cmp != 0 )
                {
                    return cmp;
                }

                cmp = SafeCompare.compareTo( o1.getFP().getDescription().getID() ,
                                             o2.getFP().getDescription().getID() );
                if ( cmp != 0 )
                {
                    return cmp;
                }

                return SafeCompare.compareTo( o1.getFP().getID() ,
                                              o2.getFP().getID() );
            }
            else
            {
                return SafeCompare.compareTo( o1.getFP().getDescription().getCreatedDate() ,
                                              o2.getFP().getDescription().getCreatedDate() );
            }
        }
    } );

    private void init()
    {
        this.fpls = new ArrayList<FilePathLine>();


    }

    private void processColor()
    {
        synchronized( fplsSorted )
        {
            int num = 0;
            FilePathLine previous = null;
            for ( FilePathLine fpl : fplsSorted )
            {
                if ( previous != null && !previous.sameGroup( fpl ) )
                {
                    ++num;
                }

                fpl.num = num;
                previous = fpl;
            }
        }
    }

    private static boolean isNear( Calendar c1 ,
                                   Calendar c2 )
    {
        if ( c1 == null )
        {
            if ( c2 == null )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if ( c2 == null )
            {
                return false;
            }
            else
            {
                if ( c1.get( Calendar.YEAR ) == c2.get( Calendar.YEAR )
                     && c1.get( Calendar.DAY_OF_YEAR ) == c2.get( Calendar.DAY_OF_YEAR ) )
                {
                    return true;
                }

                if ( Math.abs( c1.getTimeInMillis() - c2.getTimeInMillis() ) <= 2 * 60 * 60 * 1000 )
                {
                    return true;
                }

                return false;
            }
        }
    }
}
