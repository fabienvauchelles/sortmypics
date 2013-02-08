package com.vaushell.smp.model;

/**
 *
 * @author Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class MPlace
{
    // PUBLIC
    public MPlace( String ID ,
                   String location ,
                   Double latitude ,
                   Double longitude )
    {
        this.ID = ID;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;

        init();
    }

    public MPlace()
    {
        this( null ,
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

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
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

    @Override
    public String toString()
    {
        return "MPlace{" + "ID=" + ID + ", location=" + location + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
    // PRIVATE
    private String ID;
    private String location;
    private Double latitude;
    private Double longitude;

    private void init()
    {
    }
}
