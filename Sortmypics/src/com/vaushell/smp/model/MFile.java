package com.vaushell.smp.model;

import java.io.File;
import java.util.Calendar;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class MFile
{
    // PUBLIC
    public final static short FILETYPE_OTHER = 0;
    public final static short FILETYPE_PICTURE = 1;
    public final static short FILETYPE_MOVIE = 2;

    public MFile( String md5hash ,
                  MGroup group ,
                  String name ,
                  String extension ,
                  String path ,
                  long size ,
                  short type ,
                  Calendar created ,
                  Calendar added ,
                  Double latitude ,
                  Double longitude ,
                  MPlace place ,
                  String camera )
    {
        this.md5hash = md5hash;
        this.group = group;
        this.name = name;
        this.extension = extension;
        this.path = path;
        this.size = size;
        this.type = type;
        this.created = created;
        this.added = added;
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
        this.camera = camera;

        init();
    }

    public MFile()
    {
        this( null ,
              null ,
              null ,
              null ,
              null ,
              Long.MIN_VALUE ,
              Short.MIN_VALUE ,
              null ,
              null ,
              null ,
              null ,
              null ,
              null );
    }

    public String getMd5hash()
    {
        return md5hash;
    }

    public void setMd5hash( String md5hash )
    {
        this.md5hash = md5hash;
    }

    public MGroup getGroup()
    {
        return group;
    }

    public void setGroup( MGroup group )
    {
        this.group = group;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension( String extension )
    {
        this.extension = extension;

        setType( getFiletype( this.extension ) );
    }

    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize( long size )
    {
        this.size = size;
    }

    public short getType()
    {
        return type;
    }

    public void setType( short type )
    {
        this.type = type;
    }

    public Calendar getCreated()
    {
        return created;
    }

    public void setCreated( Calendar created )
    {
        this.created = created;
    }

    public Calendar getAdded()
    {
        return added;
    }

    public void setAdded( Calendar added )
    {
        this.added = added;
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

    public MPlace getPlace()
    {
        return place;
    }

    public void setPlace( MPlace place )
    {
        this.place = place;
    }

    public String getCamera()
    {
        return camera;
    }

    public void setCamera( String camera )
    {
        this.camera = camera;
    }

    public File getFile()
    {
        if ( extension != null && extension.length() > 0 )
        {
            return new File( getPath() ,
                             getName() + "." + getExtension() );
        }
        else
        {
            return new File( getPath() ,
                             getName() );
        }
    }

    public void setFile( File f )
    {
        setPath( f.getParent() );
        setName( FilenameUtils.getBaseName( f.getName() ) );
        setSize( f.length() );

        String ext = FilenameUtils.getExtension( f.getName() );
        if ( ext != null && ext.length() > 0 )
        {
            setExtension( ext );
        }
        else
        {
            setExtension( null );
        }
    }

    @Override
    public String toString()
    {
        return "MFile{" + "md5hash=" + md5hash + ", group=" + group + ", name=" + name + ", extension=" + extension + ", path=" + path + ", size=" + size + ", type=" + type + ", created=" + created + ", added=" + added + ", latitude=" + latitude + ", longitude=" + longitude + ", place=" + place + ", camera=" + camera + '}';
    }

    public static short getFiletype( String extension )
    {
        if ( extension == null || extension.length() <= 0 )
        {
            return FILETYPE_OTHER;
        }
        else
        {
            if ( "jpg".equalsIgnoreCase( extension )
                 || "jpeg".equalsIgnoreCase( extension )
                 || "png".equalsIgnoreCase( extension )
                 || "tiff".equalsIgnoreCase( extension )
                 || "bmp".equalsIgnoreCase( extension ) )
            {
                return FILETYPE_PICTURE;
            }
            else if ( "mov".equalsIgnoreCase( extension )
                      || "avi".equalsIgnoreCase( extension )
                      || "mpg".equalsIgnoreCase( extension )
                      || "mp4".equalsIgnoreCase( extension )
                      || "3gp".equalsIgnoreCase( extension )
                      || "m4v".equalsIgnoreCase( extension ) )
            {
                return FILETYPE_MOVIE;
            }
            else
            {
                return FILETYPE_OTHER;
            }
        }
    }
    // PRIVATE
    private String md5hash;
    private MGroup group;
    private String name;
    private String extension;
    private String path;
    private long size;
    private short type;
    private Calendar created;
    private Calendar added;
    private Double latitude;
    private Double longitude;
    private MPlace place;
    private String camera;

    private void init()
    {
    }
}
