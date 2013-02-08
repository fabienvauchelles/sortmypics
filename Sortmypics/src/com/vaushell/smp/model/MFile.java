package com.vaushell.smp.model;

import java.io.File;
import java.util.Date;
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
                  Date created ,
                  Date added ,
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

    public Date getCreated()
    {
        return created;
    }

    public void setCreated( Date created )
    {
        this.created = created;
    }

    public Date getAdded()
    {
        return added;
    }

    public void setAdded( Date added )
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

    public Double distance( MFile b )
    {
        return distance( this ,
                         b );
    }

    public static Double distance( MFile a ,
                                   MFile b )
    {
        if ( a == null || a.getLatitude() == null || a.getLongitude() == null || b == null || b.getLatitude() == null || b.
                getLongitude() == null )
        {
            return null;

        }
        double half = Math.PI / 180.0;
        double lat1 = a.getLatitude() * half;
        double lat2 = b.getLatitude() * half;
        double lon1 = a.getLongitude() * half;
        double lon2 = b.getLongitude() * half;
        double t1 = Math.sin( lat1 ) * Math.sin( lat2 );
        double t2 = Math.cos( lat1 ) * Math.cos( lat2 );
        double t3 = Math.cos( lon1 - lon2 );
        double t4 = t2 * t3;
        double t5 = t1 + t4;
        double rad_dist = Math.atan( -t5 / Math.sqrt( -t5 * t5 + 1 ) ) + 2 * Math.atan( 1 );

        return ( rad_dist * 3437.74677 * 1.1508 ) * 1.6093470878864446;
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
    private Date created;
    private Date added;
    private Double latitude;
    private Double longitude;
    private MPlace place;
    private String camera;

    private void init()
    {
    }
}
