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
import javax.persistence.Id;
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
    public final static short TYPE_PICTURE = 1;
    public final static short TYPE_VIDEO = 2;
    public final static short TYPE_OTHER = 3;

    public Description()
    {
        this.ID = null;
        this.type = Short.MIN_VALUE;
        this.createdDate = null;
        this.gpsLat = null;
        this.gpsLng = null;
        this.gpsAlt = null;
        this.locCity = null;
        this.locCountry = null;
        this.title = null;
        this.caption = null;
        this.make = null;
        this.model = null;
        this.updated = false;

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
    public short getType()
    {
        return type;
    }

    public void setType( short type )
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

    @Column( name = "D_GPS_ALT" )
    public Double getGPSalt()
    {
        return gpsAlt;
    }

    public void setGPSalt( Double gpsAlt )
    {
        this.gpsAlt = gpsAlt;
    }

    @Column( name = "D_LOC_CITY" , length = 1024 )
    public String getLocCity()
    {
        return locCity;
    }

    public void setLocCity( String locCity )
    {
        this.locCity = locCity;
    }

    @Column( name = "D_LOC_COUNTRY" , length = 512 )
    public String getLocCountry()
    {
        return locCountry;
    }

    public void setLocCountry( String locCountry )
    {
        this.locCountry = locCountry;
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

    @Column( name = "D_CAPTION" , length = 1024 )
    public String getCaption()
    {
        return caption;
    }

    public void setCaption( String caption )
    {
        this.caption = caption;
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

    @Override
    public String toString()
    {
        return "Description{" + "ID=" + ID + ", type=" + type + ", createdDate=" + createdDate + ", gpsLat=" + gpsLat + ", gpsLng=" + gpsLng + ", gpsAlt=" + gpsAlt + ", locCity=" + locCity + ", locCountry=" + locCountry + ", title=" + title + ", caption=" + caption + ", make=" + make + ", model=" + model + ", updated=" + updated + '}';
    }
    // PROTECTED
    // PRIVATE
    private final static long serialVersionUID = 34592348345234L;
    private String ID;
    private short type;
    private Calendar createdDate;
    private Double gpsLat;
    private Double gpsLng;
    private Double gpsAlt;
    private String locCity;
    private String locCountry;
    private String title;
    private String caption;
    private String make;
    private String model;
    private boolean updated;

    private void init()
    {
    }
}
