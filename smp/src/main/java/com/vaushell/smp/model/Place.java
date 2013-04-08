/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
@Entity
@Table( name = "PLACE" )
public class Place
        implements Serializable
{
    // PUBLIC
    public Place( String path ,
                  Description description ,
                  Listing listing )
    {
        this.ID = Long.MIN_VALUE;
        this.path = path;
        this.description = description;
        this.listing = listing;

        init();
    }

    public Place()
    {
        this( null ,
              null ,
              null );
    }

    @Id
    @Column( name = "P_ID" )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    public Long getID()
    {
        return ID;
    }

    public void setID( Long ID )
    {
        this.ID = ID;
    }

    @Column( name = "P_PATH" , length = 2048 )
    @Basic( optional = false )
    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }

    @ManyToOne( optional = false )
    @JoinColumn( name = "P_D_ID" )
    public Description getDescription()
    {
        return description;
    }

    public void setDescription( Description description )
    {
        this.description = description;
    }

    @ManyToOne( optional = false )
    @JoinColumn( name = "P_L_ID" )
    public Listing getListing()
    {
        return listing;
    }

    public void setListing( Listing listing )
    {
        this.listing = listing;
    }

    @Override
    public String toString()
    {
        return "Place{" + "ID=" + ID + ", path=" + path + ", description=" + description + ", listing=" + listing + '}';
    }
    // PROTECTED
    // PRIVATE
    private final static long serialVersionUID = 6992349238436L;
    private Long ID;
    private String path;
    private Description description;
    private Listing listing;

    private void init()
    {
    }
}
