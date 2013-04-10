/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.editor;

import com.vaushell.smp.model.ContentDAOmanager;
import com.vaushell.smp.model.Place;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class ComboBoxPlacesModel
        implements ComboBoxModel
{
    // PUBLIC
    public ComboBoxPlacesModel()
    {
        init();
    }
    
    public void reload()
    {
        places.clear();
        
        boolean found = false;
        for ( Place place : ContentDAOmanager.getInstance().getAllPlaces() )
        {
            places.add( place );
            
            if ( !found && selectedPlace != null && selectedPlace.equals( place ) )
            {
                found = true;
            }
        }
        
        if ( !found )
        {
            setSelectedItem( null );
        }
    }
    
    public void setSelectedItem( Object anItem )
    {
        if ( anItem == null )
        {
            selectedPlace = null;
        }
        else
        {
            if ( places.contains( (Place) anItem ) )
            {
                selectedPlace = (Place) anItem;
            }
        }
    }
    
    public Object getSelectedItem()
    {
        return selectedPlace;
    }
    
    public int getSize()
    {
        return places.size();
    }
    
    public Object getElementAt( int index )
    {
        return places.get( index );
    }
    
    public void addListDataListener( ListDataListener l )
    {
    }
    
    public void removeListDataListener( ListDataListener l )
    {
    }
    // PROTECTED
    // PRIVATE
    private List<Place> places;
    private Place selectedPlace;
    
    private void init()
    {
        this.places = new ArrayList<Place>();
        this.selectedPlace = null;
    }
}
