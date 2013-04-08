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
import javax.persistence.Table;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
@Entity
@Table( name = "PERSON" )
public class Person
        implements Serializable
{
    // PUBLIC
    public Person( String name )
    {
        this.ID = Long.MIN_VALUE;
        this.name = name;

        init();
    }

    public Person()
    {
        this( null );
    }

    @Id
    @Column( name = "PE_ID" )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    public Long getID()
    {
        return ID;
    }

    public void setID( Long ID )
    {
        this.ID = ID;
    }

    @Column( name = "PE_NAME" , length = 1024 )
    @Basic( optional = false )
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Person{" + "ID=" + ID + ", name=" + name + '}';
    }
    // PROTECTED
    // PRIVATE
    private final static long serialVersionUID = 123934589123L;
    private Long ID;
    private String name;

    private void init()
    {
    }
}
