/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.model;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
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
        this.sourcePath = path;
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
    public String getSourcePath()
    {
        return sourcePath;
    }

    public void setSourcePath( String getSourcePath )
    {
        this.sourcePath = getSourcePath;
    }

    @Transient
    public String getDestinationPath()
    {
        if ( description == null )
        {
            return null;
        }

        Calendar c = description.getCreatedDate();
        String title = description.getTitle();
        if ( c != null )
        {
            if ( title != null )
            {
                return String.format( "%04d%s%02d%02d - %s" ,
                                      c.get( Calendar.YEAR ) ,
                                      File.separator ,
                                      c.get( Calendar.MONTH ) + 1 ,
                                      c.get( Calendar.DAY_OF_MONTH ) ,
                                      title );
            }
            else
            {
                return String.format( "%04d%s%02d%02d" ,
                                      c.get( Calendar.YEAR ) ,
                                      File.separator ,
                                      c.get( Calendar.MONTH ) + 1 ,
                                      c.get( Calendar.DAY_OF_MONTH ) );
            }
        }
        else
        {
            if ( title != null )
            {
                return String.format( "%s" ,
                                      title );
            }
            else
            {
                return null;
            }
        }
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
        return new File( getSourcePath() ,
                         getName() );
    }

    @Override
    public String toString()
    {
        return "FilePath{" + "ID=" + ID + ", sourcePath=" + sourcePath + ", name=" + name + ", description=" + description + '}';
    }
    // PROTECTED
    // PRIVATE
    private final static long serialVersionUID = 321491238345234345L;
    private Long ID;
    private String sourcePath;
    private String name;
    private Description description;

    private void init()
    {
    }
}
