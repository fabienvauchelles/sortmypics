package com.vaushell.smp.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class MGroup
{
    // PUBLIC
    public MGroup( String ID ,
                   MFile min ,
                   MFile max ,
                   Calendar calendar ,
                   MPlace place )
    {
        this.ID = ID;
        this.min = min;
        this.max = max;
        this.calendar = calendar;
        this.place = place;

        init();
    }

    public MGroup()
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

    public MFile getMin()
    {
        return min;
    }

    public void setMin( MFile min )
    {
        this.min = min;
    }

    public MFile getMax()
    {
        return max;
    }

    public void setMax( MFile max )
    {
        this.max = max;
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setCalendar( Calendar calendar )
    {
        this.calendar = calendar;
    }

    public MPlace getPlace()
    {
        return place;
    }

    public void setPlace( MPlace place )
    {
        this.place = place;
    }

    public Set<MFile> getFiles()
    {
        return files;
    }

    public void setFiles( Set<MFile> files )
    {
        this.files = files;
    }

    @Override
    public String toString()
    {
        return "MGroup{" + "ID=" + ID + ", min=" + min + ", max=" + max + ", calendar=" + calendar + ", place=" + place + ", files=" + files + '}';
    }
    // PRIVATE
    private String ID;
    private MFile min;
    private MFile max;
    private Calendar calendar;
    private MPlace place;
    private Set<MFile> files;

    private void init()
    {
        this.files = new HashSet<MFile>();
    }
}
