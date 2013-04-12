package com.vaushell.tools;

import java.awt.Color;

/**
 *
 * @author Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class ColorMaker
{
    // PUBLIC
    public static Color getBrightColor( int increment ,
                                        int lighter )
    {
        int number = increment * 180;
        int comp = 512 - 1 - lighter;

        return getColor(
                number ,
                lighter + ( ( number / 255 ) * 10 ) % comp );
    }

    public static Color lighter( Color source ,
                                 int delta )
    {
        double factor = ( 255.0 - (double) delta ) / 255.0;

        return new Color(
                delta + (int) ( source.getRed() * factor ) ,
                delta + (int) ( source.getGreen() * factor ) ,
                delta + (int) ( source.getBlue() * factor ) );
    }

    public static Color darker( Color source ,
                                int delta )
    {
        double factor = ( 255.0 - (double) delta ) / 255.0;

        return new Color(
                (int) ( source.getRed() * factor ) ,
                (int) ( source.getGreen() * factor ) ,
                (int) ( source.getBlue() * factor ) );
    }

    public static boolean isTooLight( Color source ,
                                      int max )
    {
        int count = 0;
        if ( source.getRed() > max )
        {
            count++;
        }

        if ( source.getGreen() > max )
        {
            count++;
        }

        if ( source.getBlue() > max )
        {
            count++;
        }

        if ( count >= 2 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isTooDark( Color source ,
                                     int min )
    {
        int count = 0;
        if ( source.getRed() < min )
        {
            count++;
        }

        if ( source.getGreen() < min )
        {
            count++;
        }

        if ( source.getBlue() < min )
        {
            count++;
        }

        if ( count >= 2 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isNear( Color a ,
                                  Color b ,
                                  int distance )
    {
        if ( Math.abs( a.getRed() - b.getRed() ) > distance )
        {
            return false;
        }

        if ( Math.abs( a.getGreen() - b.getGreen() ) > distance )
        {
            return false;
        }

        if ( Math.abs( a.getBlue() - b.getBlue() ) > distance )
        {
            return false;
        }

        return true;
    }

    // PRIVATE
    private static Color getColor( int number )
    {
        if ( number < 0 )
        {
            return getColor( -number );
        }

        if ( number >= 256 * 6 )
        {
            return getColor( number % ( 256 * 6 ) );
        }

        if ( number < 256 )
        {
            return new Color(
                    255 ,
                    number ,
                    0 );
        }

        if ( number < 256 * 2 )
        {
            return new Color(
                    256 * 2 - 1 - number ,
                    255 ,
                    0 );
        }

        if ( number < 256 * 3 )
        {
            return new Color(
                    0 ,
                    255 ,
                    number - 256 * 2 );
        }

        if ( number < 256 * 4 )
        {
            return new Color(
                    0 ,
                    256 * 4 - 1 - number ,
                    255 );
        }

        if ( number < 256 * 5 )
        {
            return new Color(
                    number - 256 * 4 ,
                    0 ,
                    255 );
        }

        if ( number < 256 * 6 )
        {
            return new Color(
                    255 ,
                    0 ,
                    256 * 6 - 1 - number );
        }

        return null;
    }

    private static Color getColor( int number ,
                                   int delta )
    {
        if ( delta < 0 )
        {
            return getColor( number ,
                             -delta );
        }

        if ( delta >= 256 * 2 )
        {
            return getColor( number ,
                             delta % ( 256 * 2 ) );
        }

        if ( delta < 256 )
        {
            return darker( getColor( number ) ,
                           255 - delta );
        }
        else
        {
            return lighter( getColor( number ) ,
                            delta - 256 );
        }
    }
}
