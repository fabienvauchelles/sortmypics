/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.latitude;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.latitude.Latitude;
import com.google.api.services.latitude.LatitudeRequest;
import com.google.api.services.latitude.LatitudeRequestInitializer;
import com.google.api.services.latitude.LatitudeScopes;
import com.google.api.services.latitude.model.Location;
import com.google.api.services.latitude.model.LocationFeed;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class GLatitude
{
    // PUBLIC
    public final static int MINMAX_DURATION_IN_MINUTES = 120;

    public static GLatitude getInstance()
    {
        return GLatitudeHolder.INSTANCE;
    }

    public List<GLatitude.GLocation> getAllLocations( Integer maxResults ,
                                                      Calendar minCal ,
                                                      Calendar maxCal )
            throws IOException
    {
        List<GLatitude.GLocation> locations = new ArrayList<GLatitude.GLocation>();

        // Create request
        Latitude.Location.List request = main.location().list();
        request.setGranularity( "best" );

        if ( maxResults != null )
        {
            request.setMaxResults( "" + maxResults );
        }

        if ( minCal != null )
        {
            request.setMinTime( "" + minCal.getTimeInMillis() );
        }

        if ( maxCal != null )
        {
            request.setMaxTime( "" + maxCal.getTimeInMillis() );
        }

        LocationFeed locationFeed = request.execute();

        if ( locationFeed.getItems() != null )
        {
            for ( Location location : locationFeed.getItems() )
            {
                if ( location.getLatitude() != null && location.getLatitude() instanceof BigDecimal
                     && location.getLongitude() != null && location.getLongitude() instanceof BigDecimal
                     && location.getTimestampMs() != null && location.getTimestampMs() instanceof String )
                {
                    String s = (String) location.getTimestampMs();
                    try
                    {
                        long lg = Long.parseLong( s );

                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis( lg );

                        Double latitude = ( (BigDecimal) location.getLatitude() ).doubleValue();
                        Double longitude = ( (BigDecimal) location.getLongitude() ).doubleValue();
                         
                        Double accuracy;
                        if ( location.getAccuracy() != null && location.getAccuracy() instanceof BigDecimal )
                        {
                            accuracy = ( (BigDecimal) location.getAccuracy() ).doubleValue();
                        }
                        else
                        {
                            accuracy = null;
                        }

                        locations.add( new GLatitude.GLocation( latitude ,
                                                                longitude ,
                                                                accuracy ,
                                                                cal ) );
                    }
                    catch( NumberFormatException ex )
                    {
                    }
                }
            }
        }

        return locations;
    }

    public static class GLocation
    {
        // PUBLIC
        public GLocation( Double latitude ,
                          Double longitude ,
                          Double accuracy ,
                          Calendar calendar )
        {
            this.latitude = latitude;
            this.longitude = longitude;
            this.accuracy = accuracy;
            this.calendar = calendar;

            init();
        }

        public Double getLatitude()
        {
            return latitude;
        }

        public Double getLongitude()
        {
            return longitude;
        }

        public Double getAccuracy()
        {
            return accuracy;
        }

        public Calendar getCalendar()
        {
            return calendar;
        }

        @Override
        public String toString()
        {
            return "GLocation{" + "latitude=" + latitude + ", longitude=" + longitude + ", accuracy=" + accuracy + ", calendar=" + calendar + '}';
        }
        // PRIVATE
        private Double latitude;
        private Double longitude;
        private Double accuracy;
        private Calendar calendar;

        private void init()
        {
        }
    }
    // PRIVATE
    private static final String APPLICATION_NAME = "VAUSHELL-SMP/1.0";
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final GsonFactory JSON_FACTORY = new GsonFactory();
    private Latitude main;

    private void init()
    {
    }

    private GLatitude()
    {
        init();

        try
        {
            // load client secrets
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JSON_FACTORY ,
                    GLatitude.class.getResourceAsStream( "/client_secrets.json" ) );
            if ( clientSecrets.getDetails().getClientId().startsWith( "Enter" )
                 || clientSecrets.getDetails().getClientSecret().startsWith( "Enter " ) )
            {
                throw new IOException( "Cannot find valid client secrets" );
            }
            // set up file credential store
            FileCredentialStore credentialStore = new FileCredentialStore(
                    new File( System.getProperty( "user.home" ) ,
                              ".credentials/latitude.json" ) ,
                    JSON_FACTORY );
            // set up authorization code flow
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT ,
                    JSON_FACTORY ,
                    clientSecrets ,
                    Collections.singleton( LatitudeScopes.LATITUDE_ALL_BEST ) ).setCredentialStore( credentialStore )
                    .build();

            // authorize
            Credential credential = new AuthorizationCodeInstalledApp( flow ,
                                                                       new LocalServerReceiver() ).authorize( "user" );

            this.main = new Latitude.Builder( HTTP_TRANSPORT ,
                                              JSON_FACTORY ,
                                              credential )
                    .setApplicationName( APPLICATION_NAME )
                    .setGoogleClientRequestInitializer( new LatitudeRequestInitializer()
            {
                @Override
                public void initializeLatitudeRequest( LatitudeRequest<?> request )
                {
                    request.setPrettyPrint( true );
                }
            } ).build();
        }
        catch( IOException ex )
        {
            throw new RuntimeException( ex );
        }
    }

    private static class GLatitudeHolder
    {
        private static final GLatitude INSTANCE = new GLatitude();
    }
}
