/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.files;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabienATvauchellesDOTcom>
 */
public class FileChooser
{
    // PUBLIC
    public static File chooseDirectory( Component parent )
    {
        return chooseDirectory( parent ,
                                null );
    }

    public static File chooseDirectory( Component parent ,
                                        File selectedDirectory )
    {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

        if ( selectedDirectory != null )
        {
            fc.setSelectedFile( selectedDirectory );
        }

        int retVal = fc.showOpenDialog( parent );
        if ( retVal == JFileChooser.APPROVE_OPTION )
        {
            return fc.getSelectedFile();
        }
        else
        {
            return null;
        }
    }

    public static File chooseFile( Component parent )
    {
        return chooseFile( parent ,
                           null );
    }

    public static File chooseFile( Component parent ,
                                   File selectedFile )
    {
        final JFileChooser fc = new JFileChooser();

        if ( selectedFile != null )
        {
            fc.setSelectedFile( selectedFile );
        }

        int retVal = fc.showOpenDialog( parent );
        if ( retVal == JFileChooser.APPROVE_OPTION )
        {
            return fc.getSelectedFile();
        }
        else
        {
            return null;
        }
    }
}
