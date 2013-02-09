/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.smp.action;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.vaushell.smp.FilesControllerDAO;
import com.vaushell.tools.contacts.GContacts;
import com.vaushell.tools.geo.GeoReverse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class ImportContactDAO
{
    // PUBLIC
    public ImportContactDAO()
    {
        init();
    }

    public void setDAO( FilesControllerDAO dao )
    {
        this.dao = dao;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public void run()
            throws AuthenticationException , IOException , ServiceException , ParseException
    {
        if ( username == null || password == null )
        {
            throw new NullPointerException();
        }

        logger.info( "[ImportContactDAO] begin" );

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ImportContactDAO] import contacts from Google" );
        }

        List<ContactEntry> contacts = new GContacts( username ,
                                                     password ).getAllContacts();

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ImportContactDAO] found " + contacts.size() + " contacts" );
            logger.debug( "[ImportContactDAO] sort contact by address" );
        }

        HashMap<String , ContactPlace> contactsByAddress = new HashMap<String , ContactPlace>();
        for ( ContactEntry contact : contacts )
        {
            String name = extractName( contact );
            if ( name != null && name.length() > 0 )
            {
                if ( contact.hasStructuredPostalAddresses() )
                {
                    for ( StructuredPostalAddress address : contact.getStructuredPostalAddresses() )
                    {
                        String formatted = formatAddress( address );

                        if ( formatted != null && formatted.length() > 0 )
                        {
                            GeoReverse.GeoData gd = dao.convertAddressToGeo( formatted );
                            if ( gd != null )
                            {
                                String key = gd.getLatitude() + "#" + gd.getLongitude();

                                ContactPlace place = contactsByAddress.get( key );
                                if ( place == null )
                                {
                                    place = new ContactPlace( formatted ,
                                                              gd.getLatitude() ,
                                                              gd.getLongitude() );
                                    contactsByAddress.put( key ,
                                                           place );
                                }

                                place.names.add( name );

                                String type = extractType( address );
                                if ( type != null && type.length() > 0 )
                                {
                                    place.types.add( type );
                                }
                            }
                            else
                            {
                                if ( logger.isDebugEnabled() )
                                {
                                    logger.
                                            debug(
                                            "[ImportContactDAO] cannot find location for '" + name + "', address '" + formatted + "'" );
                                }
                            }
                        }
                        else
                        {
                            if ( logger.isDebugEnabled() )
                            {
                                logger.debug( "[ImportContactDAO] cannot format address for '" + name + "'" );
                            }
                        }
                    }
                }
                else
                {
                    if ( logger.isDebugEnabled() )
                    {
                        logger.debug( "[ImportContactDAO] cannot find address for '" + name + "'" );
                    }
                }
            }
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "[ImportContactDAO] write places" );
        }

        for ( ContactPlace place : contactsByAddress.values() )
        {
            dao.createPlaceIfNecessary( place.getTitle() ,
                                        place.latitude ,
                                        place.longitude );
        }

        logger.info( "[ImportContactDAO] end" );
    }
    // PROTECTED
    // PRIVATE
    private final static Logger logger = LoggerFactory.getLogger( ImportContactDAO.class );
    private FilesControllerDAO dao;
    private String username;
    private String password;

    private void init()
    {
        this.dao = null;
        this.username = null;
        this.password = null;
    }

    private String extractName( ContactEntry contact )
    {
        if ( contact.getTitle() != null )
        {
            return contact.getTitle().getPlainText();
        }
        else
        {
            return null;
        }
    }

    private String formatAddress( StructuredPostalAddress address )
    {
        StringBuilder sb = new StringBuilder();

        if ( address.hasStreet() )
        {
            if ( sb.length() > 0 )
            {
                sb.append( ", " );
            }

            sb.append( address.getStreet().getValue() );
        }

        if ( address.hasPostcode() || address.hasCity() )
        {
            if ( sb.length() > 0 )
            {
                sb.append( ", " );
            }

            if ( address.hasPostcode() )
            {
                sb.append( address.getPostcode().getValue() );
            }

            if ( address.hasCity() )
            {
                if ( address.hasPostcode() )
                {
                    sb.append( " " );
                }

                sb.append( address.getCity().getValue() );
            }
        }

        if ( address.hasCountry() )
        {
            if ( sb.length() > 0 )
            {
                sb.append( ", " );
            }

            sb.append( address.getCountry().getValue() );
        }

        if ( sb.length() <= 0 )
        {
            return null;
        }
        else
        {
            return sb.toString();
        }
    }

    private String extractType( StructuredPostalAddress address )
    {
        if ( address.hasRel() )
        {
            if ( address.getRel().endsWith( "#home" ) )
            {
                return "home";
            }

            if ( address.getRel().endsWith( "#work" ) )
            {
                return "work";
            }
        }

        if ( address.hasLabel() )
        {
            return address.getLabel();
        }

        return null;
    }

    private static class ContactPlace
    {
        // PUBLIC
        public ContactPlace( String address ,
                             Double latitude ,
                             Double longitude )
        {
            this.address = address;
            this.latitude = latitude;
            this.longitude = longitude;

            init();
        }

        public String getTitle()
        {
            StringBuilder sb = new StringBuilder();

            sb.append( address );

            if ( names.size() > 0 )
            {
                sb.append( " - " );

                boolean first = true;
                for ( String name : names )
                {
                    if ( first )
                    {
                        sb.append( name );

                        first = false;
                    }
                    else
                    {
                        sb.append( ", " ).append( name );
                    }
                }
            }

            if ( types.size() > 0 )
            {
                sb.append( " - " );

                boolean first = true;
                for ( String type : types )
                {
                    if ( first )
                    {
                        sb.append( type );

                        first = false;
                    }
                    else
                    {
                        sb.append( ", " ).append( type );
                    }
                }
            }

            return sb.toString();
        }

        @Override
        public String toString()
        {
            // Names
            StringBuilder namesStr = new StringBuilder();
            namesStr.append( "[" );

            boolean first = true;
            for ( String name : names )
            {
                if ( first )
                {
                    namesStr.append( name );

                    first = false;
                }
                else
                {
                    namesStr.append( "," ).append( name );
                }
            }

            namesStr.append( "]" );

            // Types
            StringBuilder typesStr = new StringBuilder();
            typesStr.append( "[" );

            first = true;
            for ( String type : types )
            {
                if ( first )
                {
                    typesStr.append( type );

                    first = false;
                }
                else
                {
                    typesStr.append( "," ).append( type );
                }
            }

            typesStr.append( "]" );
            return "ContactPlace{" + "address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + ", names=" + namesStr.
                    toString() + ", types=" + typesStr.toString() + '}';
        }
        // PRIVATE
        private String address;
        private Double latitude;
        private Double longitude;
        private HashSet<String> names;
        private HashSet<String> types;

        private void init()
        {
            this.names = new HashSet<String>();
            this.types = new HashSet<String>();
        }
    }
}
