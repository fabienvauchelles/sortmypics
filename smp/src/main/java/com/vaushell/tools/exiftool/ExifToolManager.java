/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.exiftool;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class ExifToolManager
{
    // PUBLIC
    public static ExifToolManager getInstance()
    {
        return ExifToolManagerHolder.INSTANCE;
    }

    public void start( final String exifToolPath )
    {
        if ( exifToolPath == null )
        {
            throw new NullPointerException();
        }

        this.pool = new StackObjectPool<ExifTool>( new BasePoolableObjectFactory<ExifTool>()
        {
            @Override
            public ExifTool makeObject()
                    throws Exception
            {
                return new ExifTool( exifToolPath );
            }

            @Override
            public void destroyObject( ExifTool obj )
                    throws Exception
            {
                if ( obj != null )
                {
                    obj.close();
                }
            }
        } );
    }

    public ExifTool borrowTool()
    {
        if ( pool == null )
        {
            throw new NullPointerException();
        }

        try
        {
            return (ExifTool) pool.borrowObject();
        }
        catch( Exception ex )
        {
            throw new RuntimeException( ex );
        }
    }

    public void returnTool( ExifTool e )
    {
        if ( e == null )
        {
            throw new NullPointerException();
        }

        if ( pool == null )
        {
            throw new NullPointerException();
        }

        try
        {
            pool.returnObject( e );
        }
        catch( Exception ex )
        {
            throw new RuntimeException( ex );
        }
    }

    public void stop()
    {
        try
        {
            if ( pool != null )
            {
                pool.close();
            }
        }
        catch( Exception ex )
        {
            throw new RuntimeException( ex );
        }
    }
    // PRIVATE
    private ObjectPool pool;

    private ExifToolManager()
    {
        this.pool = null;
    }

    private static class ExifToolManagerHolder
    {
        private static final ExifToolManager INSTANCE = new ExifToolManager();
    }
}
