/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
@Entity
@Table( name = "LISTING" )
public class Listing
        implements Serializable
{
    // PUBLIC
    public Listing( String name ,
                    Calendar date )
    {
        this.ID = Long.MIN_VALUE;
        this.name = name;
        this.date = date;

        init();
    }

    public Listing()
    {
        this( null ,
              null );
    }

    @Id
    @Column( name = "L_ID" )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    public Long getID()
    {
        return ID;
    }

    public void setID( Long ID )
    {
        this.ID = ID;
    }

    @Column( name = "L_NAME" , length = 1024 )
    @Basic( optional = false )
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Column( name = "L_DATE" )
    @Temporal( TemporalType.TIMESTAMP )
    @Basic( optional = false )
    public Calendar getDate()
    {
        return date;
    }

    public void setDate( Calendar date )
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "Listing{" + "ID=" + ID + ", name=" + name + ", date=" + date + '}';
    }
    // PROTECTED
    // PRIVATE
    private final static long serialVersionUID = 3245912383345L;
    private Long ID;
    private String name;
    private Calendar date;

    private void init()
    {
    }
}
