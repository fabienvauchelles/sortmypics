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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
@Entity
@Table( name = "DESCRIPTION" )
public class Description
        implements Serializable
{
    // PUBLIC
    public enum FilePathType {
        PICTURE, VIDEO, OTHER
    }

    public Description()
    {
        init();
    }

    @Id
    @Column( name = "D_ID" , length = 32 )
    public String getID()
    {
        return ID;
    }

    public void setID( String ID )
    {
        this.ID = ID;
    }

    @Column( name = "D_TYPE" )
    @Basic( optional = false )
    @Enumerated(EnumType.STRING)
    public FilePathType getType()
    {
        return type;
    }

    public void setType( FilePathType type )
    {
        this.type = type;
    }

    @Column( name = "D_CREATED_DATE" )
    @Temporal( TemporalType.TIMESTAMP )
    public Calendar getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate( Calendar createdDate )
    {
        this.createdDate = createdDate;
    }

    @Column( name = "D_GPS_LAT" )
    public Double getGPSlat()
    {
        return gpsLat;
    }

    public void setGPSlat( Double gpsLat )
    {
        this.gpsLat = gpsLat;
    }

    @Column( name = "D_GPS_LNG" )
    public Double getGPSlng()
    {
        return gpsLng;
    }

    public void setGPSlng( Double gpsLng )
    {
        this.gpsLng = gpsLng;
    }

    @ManyToOne
    @JoinColumn( name = "D_PE_ID" )
    public Person getPerson()
    {
        return person;
    }

    public void setPerson( Person person )
    {
        this.person = person;
    }
    
    @Column( name = "D_TITLE" , length = 1024 )
    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    @Column( name = "D_MAKE" , length = 256 )
    public String getMake()
    {
        return make;
    }

    public void setMake( String make )
    {
        this.make = make;
    }

    @Column( name = "D_MODEL" , length = 256 )
    public String getModel()
    {
        return model;
    }

    public void setModel( String model )
    {
        this.model = model;
    }

    @ManyToOne
    @JoinColumn( name = "D_PL_ID" )
    public Place getPlace()
    {
        return place;
    }

    public void setPlace( Place place )
    {
        this.place = place;
    }
    
    @Column( name = "D_UPDATED" )
    @Basic( optional = false )
    public boolean isUpdated()
    {
        return updated;
    }

    public void setUpdated( boolean updated )
    {
        this.updated = updated;
    }

    @Column( name = "D_IGNORE_SORT" )
    @Basic( optional = false )
    public boolean isIgnoreSort()
    {
        return ignoreSort;
    }

    public void setIgnoreSort( boolean ignoreSort )
    {
        this.ignoreSort = ignoreSort;
    }
    
    @Column( name = "D_FILES_COUNT" )
    @Basic( optional = false )
    public int getFilesCount()
    {
        return filesCount;
    }

    public void setFilesCount( int filesCount )
    {
        this.filesCount = filesCount;
    }

    @Override
    public String toString()
    {
        return "Description{" + "ID=" + ID + ", type=" + type + ", createdDate=" + createdDate + ", gpsLat=" + gpsLat + ", gpsLng=" + gpsLng + ", place=" + place + ", title=" + title + ", make=" + make + ", model=" + model + ", person=" + person + ", updated=" + updated + ", ignoreSort=" + ignoreSort + ", filesCount=" + filesCount + '}';
    }
    
    // PROTECTED
    // PRIVATE
    private final static long serialVersionUID = 34592348345234L;
    private String ID;
    private FilePathType type;
    private Calendar createdDate;
    private Double gpsLat;
    private Double gpsLng;
    private Place place;
    private String title;
    private String make;
    private String model;
    private Person person;
    private boolean updated;
    private boolean ignoreSort;
    private int filesCount;

    private void init()
    {
        this.ID = null;
        this.type = null;
        this.createdDate = null;
        this.gpsLat = null;
        this.gpsLng = null;
        this.place=null;
        this.title = null;
        this.make = null;
        this.model = null;
        this.person=null;
        this.updated = false;
        this.ignoreSort=false;
        this.filesCount = 0;
    }
}
