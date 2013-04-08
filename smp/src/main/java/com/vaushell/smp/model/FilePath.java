/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.model;

import java.io.File;
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
import javax.persistence.Transient;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
@Entity
@Table( name = "FILEPATH" )
public class FilePath
        implements Serializable
{
    // PUBLIC
    public FilePath( String path ,
                     String name ,
                     Description description )
    {
        this.ID = Long.MIN_VALUE;
        this.path = path;
        this.name = name;
        this.description = description;

        init();
    }

    public FilePath()
    {
        this( null ,
              null ,
              null );
    }

    @Id
    @Column( name = "FP_ID" )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    public Long getID()
    {
        return ID;
    }

    public void setID( Long ID )
    {
        this.ID = ID;
    }

    @Column( name = "FP_PATH" , length = 2048 )
    @Basic( optional = false )
    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }

    @Column( name = "FP_NAME" , length = 1024 )
    @Basic( optional = false )
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @ManyToOne( optional = false )
    @JoinColumn( name = "FP_D_ID" )
    public Description getDescription()
    {
        return description;
    }

    public void setDescription( Description description )
    {
        this.description = description;
    }
    
    @Transient
    public File getFile()
    {
        return new File( getPath(), getName() );
    }

    @Override
    public String toString()
    {
        return "FilePath{" + "ID=" + ID + ", path=" + path + ", name=" + name + ", description=" + description + '}';
    }
    // PROTECTED
    // PRIVATE
    private final static long serialVersionUID = 321491238345234345L;
    private Long ID;
    private String path;
    private String name;
    private Description description;

    private void init()
    {
    }
}
