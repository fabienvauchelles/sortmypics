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
@Table( name = "PLACE" )
public class Place
        implements Serializable
{
    // PUBLIC
    public Place( String name ,
                  double gpsLat ,
                  double gpsLng ,
                  double radius )
    {
        this.ID = Long.MIN_VALUE;
        this.name = name;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.radius = radius;

        init();
    }

    public Place()
    {
        this( null ,
              Double.NaN ,
              Double.NaN ,
              Double.NaN );
    }

    @Id
    @Column( name = "PL_ID" )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    public Long getID()
    {
        return ID;
    }

    public void setID( Long ID )
    {
        this.ID = ID;
    }

    @Column( name = "PL_NAME" , length = 1024 )
    @Basic( optional = false )
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Column( name = "PL_GPS_LAT" )
    @Basic( optional = false )
    public double getGPSlat()
    {
        return gpsLat;
    }

    public void setGPSlat( double gpsLat )
    {
        this.gpsLat = gpsLat;
    }

    @Column( name = "PL_GPS_LNG" )
    @Basic( optional = false )
    public double getGPSlng()
    {
        return gpsLng;
    }

    public void setGPSlng( double gpsLng )
    {
        this.gpsLng = gpsLng;
    }

    @Column( name = "PL_RADIUS" )
    @Basic( optional = false )
    public double getRadius()
    {
        return radius;
    }

    public void setRadius( double radius )
    {
        this.radius = radius;
    }

    @Override
    public String toString()
    {
        return "Place{" + "ID=" + ID + ", name=" + name + ", gpsLat=" + gpsLat + ", gpsLng=" + gpsLng + ", radius=" + radius + '}';
    }
    // PROTECTED
    // PRIVATE
    private final static long serialVersionUID = 3459183452134312L;
    private Long ID;
    private String name;
    private double gpsLat;
    private double gpsLng;
    private double radius;

    private void init()
    {
    }
}
