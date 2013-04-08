/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class Reference<T>
{
    // PUBLIC
    public Reference( T o )
    {
        this.o = o;
    }
    public T o;
}
