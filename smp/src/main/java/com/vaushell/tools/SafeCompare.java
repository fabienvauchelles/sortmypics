/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class SafeCompare
{
    // PUBLIC
    public static int compareTo( Comparable o1 ,
                                     Comparable o2 )
    {
        if ( o1 == null )
        {
            if ( o2 == null )
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            if ( o2 == null )
            {
                return 1;
            }
            else
            {
                return o1.compareTo( o2 );
            }
        }
    }
}
