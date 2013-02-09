package com.vaushell.smp.model;
// Generated 30 janv. 2013 15:19:05 by Hibernate Tools 3.2.1.GA

import java.util.Calendar;

/**
 *
 * @author Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class MLatitudeCache
{
    // PUBLIC
    public MLatitudeCache( String ID ,
                           Calendar calendar ,
                           Double latitude ,
                           Double longitude ,
                           Double accuracy )
    {
        this.ID = ID;
        this.calendar = calendar;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;

        init();
    }

    public MLatitudeCache()
    {
        this( null ,
              null ,
              null ,
              null ,
              null );
    }

    public String getID()
    {
        return ID;
    }

    public void setID( String ID )
    {
        this.ID = ID;
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setCalendar( Calendar calendar )
    {
        this.calendar = calendar;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude( Double latitude )
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude( Double longitude )
    {
        this.longitude = longitude;
    }

    public Double getAccuracy()
    {
        return accuracy;
    }

    public void setAccuracy( Double accuracy )
    {
        this.accuracy = accuracy;
    }

    @Override
    public String toString()
    {
        return "MLatitudeCache{" + "ID=" + ID + ", calendar=" + calendar + ", latitude=" + latitude + ", longitude=" + longitude + ", accuracy=" + accuracy + '}';
    }
    // PRIVATE
    private String ID;
    private Calendar calendar;
    private Double latitude;
    private Double longitude;
    private Double accuracy;

    private void init()
    {
    }
}
