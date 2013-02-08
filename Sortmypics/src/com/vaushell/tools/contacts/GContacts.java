/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.contacts;

import com.google.gdata.client.contacts.ContactQuery;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class GContacts
{
    // PUBLIC
    public GContacts( String username ,
                      String password )
            throws AuthenticationException
    {
        this.service = new ContactsService( "VAUSHELL-SMP/1.0" );
        service.setUserCredentials( username ,
                                    password );
    }

    public List<ContactEntry> getAllContacts()
            throws IOException , ServiceException
    {
        return getAllContacts( DEFAULT_GROUP );
    }

    public List<ContactEntry> getAllContacts( String groupSystemID )
            throws IOException , ServiceException
    {
        List<ContactEntry> contacts = new ArrayList<ContactEntry>();

        String groupID = getGroupBySystemID( groupSystemID );
        if ( groupID == null )
        {
            return contacts;
        }

        URL url = new URL( DEFAULT_FEED + "contacts/default/thin" );
        ContactQuery myQuery = new ContactQuery( url );
        myQuery.setGroup( groupID );

        int i = 1;
        boolean cont = true;
        while ( cont )
        {
            myQuery.setStartIndex( i );

            ContactFeed resultFeed = service.getFeed( myQuery ,
                                                      ContactFeed.class );

            List<ContactEntry> newContacts = resultFeed.getEntries();
            if ( newContacts.isEmpty() )
            {
                cont = false;
            }
            else
            {
                contacts.addAll( newContacts );

                i = i + newContacts.size();
            }
        }

        return contacts;
    }
    // PROTECTED
    // PRIVATE
    private static final String DEFAULT_FEED = "https://www.google.com/m8/feeds/";
    private static final String DEFAULT_GROUP = "Contacts";
    private ContactsService service;

    private String getGroupBySystemID( String systemID )
            throws MalformedURLException , IOException , ServiceException
    {
        URL url = new URL( DEFAULT_FEED + "groups/default/thin" );

        ContactGroupFeed groupFeed = service.getFeed(
                url ,
                ContactGroupFeed.class );

        for ( ContactGroupEntry group : groupFeed.getEntries() )
        {
            if ( group.getSystemGroup() != null && systemID.equalsIgnoreCase( group.getSystemGroup().getId() ) )
            {
                return group.getId();
            }
        }

        return null;
    }
}
