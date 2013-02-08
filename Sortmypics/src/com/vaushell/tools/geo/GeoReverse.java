/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaushell.tools.geo;

import com.google.gdata.data.extensions.StructuredPostalAddress;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class GeoReverse
{
    // PUBLIC
    public static GeoReverse getInstance()
    {
        return GeoReverseHolder.INSTANCE;
    }

    public GeoData convertAddressToGeo( String address )
            throws IOException , ParseException
    {
        HttpEntity responseEntity = null;
        GeoData result = null;
        try
        {
            // Exec request
            HttpGet get = new HttpGet(
                    "http://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode( address ,
                                                                                                     "UTF-8" ) + "&sensor=false" );
            HttpResponse response = client.execute( get );
            responseEntity = response.getEntity();

            StatusLine sl = response.getStatusLine();
            if ( sl.getStatusCode() != 200 )
            {
                throw new IOException( sl.getReasonPhrase() );
            }

            InputStream is = null;
            try
            {
                is = responseEntity.getContent();

                JSONParser parser = new JSONParser();

                Object root = parser.parse( new InputStreamReader( is ) );
                if ( root != null && root instanceof JSONObject )
                {
                    Object oResultsArray = ( (JSONObject) root ).get( "results" );
                    if ( oResultsArray != null && oResultsArray instanceof JSONArray )
                    {
                        JSONArray resultsArray = (JSONArray) oResultsArray;
                        if ( resultsArray.size() > 0 )
                        {
                            Object results = resultsArray.get( 0 );
                            if ( results != null && results instanceof JSONObject )
                            {
                                Object geometry = ( (JSONObject) results ).get( "geometry" );
                                if ( geometry != null && geometry instanceof JSONObject )
                                {
                                    Object oLocation = ( (JSONObject) geometry ).get( "location" );
                                    if ( oLocation != null && oLocation instanceof JSONObject )
                                    {
                                        JSONObject location = (JSONObject) oLocation;

                                        Object longitude = location.get( "lng" );
                                        Object latitude = location.get( "lat" );

                                        if ( longitude != null && longitude instanceof Double
                                             && latitude != null && latitude instanceof Double )
                                        {
                                            result = new GeoData( (Double) latitude ,
                                                                  (Double) longitude );
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                is.close();
                
                try
                {
                    Thread.sleep( 110 );
                    // Google policy : max 10 QPS
                }
                catch( InterruptedException ex )
                {
                    // Nothing
                }

            }
            finally
            {
                if ( is != null )
                {
                    is.close();
                }
            }
        }
        finally
        {
            if ( responseEntity != null )
            {
                try
                {
                    EntityUtils.consume( responseEntity );
                }
                catch( IOException ex )
                {
                    throw new RuntimeException( ex );
                }
            }
        }

        return result;
    }

    public String convertGeoToAddress( GeoData geo )
            throws IOException , ParseException
    {
        HashMap<String , String> rz = new HashMap<String , String>();

        HttpEntity responseEntity = null;
        try
        {
            // Exec request
            HttpGet get = new HttpGet(
                    "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + geo.getLatitude() + "," + geo.getLongitude() + "&sensor=false" );
            HttpResponse response = client.execute( get );
            responseEntity = response.getEntity();

            StatusLine sl = response.getStatusLine();
            if ( sl.getStatusCode() != 200 )
            {
                throw new IOException( sl.getReasonPhrase() );
            }

            InputStream is = null;
            try
            {
                is = responseEntity.getContent();

                JSONParser parser = new JSONParser();

                Object root = parser.parse( new InputStreamReader( is ) );
                if ( root != null && root instanceof JSONObject )
                {
                    Object oResultsArray = ( (JSONObject) root ).get( "results" );
                    if ( oResultsArray != null && oResultsArray instanceof JSONArray )
                    {
                        JSONArray resultsArray = (JSONArray) oResultsArray;
                        if ( resultsArray.size() > 0 )
                        {
                            Object results = resultsArray.get( 0 );
                            if ( results != null && results instanceof JSONObject )
                            {
                                Object addressComponents = ( (JSONObject) results ).get( "address_components" );
                                if ( addressComponents != null && addressComponents instanceof JSONArray )
                                {
                                    for ( Object oAddressComponent : (JSONArray) addressComponents )
                                    {
                                        if ( oAddressComponent instanceof JSONObject )
                                        {
                                            JSONObject addressComponent = (JSONObject) oAddressComponent;

                                            Object name = addressComponent.get( "long_name" );

                                            if ( name != null && name instanceof String )
                                            {
                                                Object oTypes = addressComponent.get( "types" );
                                                if ( oTypes != null && oTypes instanceof JSONArray )
                                                {
                                                    JSONArray types = (JSONArray) oTypes;
                                                    if ( types.size() > 0 )
                                                    {
                                                        Object type = types.get( 0 );
                                                        if ( type instanceof String )
                                                        {
                                                            rz.put( (String) type ,
                                                                    (String) name );
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                is.close();

            }
            finally
            {
                if ( is != null )
                {
                    is.close();
                }
            }
        }
        finally
        {
            if ( responseEntity != null )
            {
                try
                {
                    EntityUtils.consume( responseEntity );
                }
                catch( IOException ex )
                {
                    throw new RuntimeException( ex );
                }
            }
        }

        return convert( rz );
    }

    public static class GeoData
    {
        // PUBLIC
        public GeoData( double latitude ,
                        double longitude )
        {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude()
        {
            return latitude;
        }

        public double getLongitude()
        {
            return longitude;
        }

        @Override
        public String toString()
        {
            return "GeoData{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
        }
        // PRIVATE
        private double latitude;
        private double longitude;
    }
    // PRIVATE
    private HttpClient client;

    private void init()
    {
    }

    private GeoReverse()
    {
        init();

        SchemeRegistry sr = new SchemeRegistry();

        sr.register( new Scheme( "http" ,
                                 80 ,
                                 PlainSocketFactory.getSocketFactory() ) );

        // TODO insert https from http://stackoverflow.com/questions/1217141/self-signed-ssl-acceptance-android

        ClientConnectionManager cm = new PoolingClientConnectionManager( sr );
        this.client = new DefaultHttpClient( cm );
    }

    private static String convert( HashMap<String , String> map )
    {
        StringBuilder sb = new StringBuilder();

        if ( map.containsKey( "street_number" ) || map.containsKey( "route" ) )
        {
            if ( map.containsKey( "street_number" ) )
            {
                sb.append( map.get( "street_number" ) );
            }

            if ( map.containsKey( "route" ) )
            {
                if ( map.containsKey( "street_number" ) )
                {
                    sb.append( " " );
                }

                sb.append( map.get( "route" ) );
            }
        }

        if ( map.containsKey( "postal_code" ) || map.containsKey( "locality" ) )
        {
            if ( sb.length() > 0 )
            {
                sb.append( ", " );
            }

            if ( map.containsKey( "postal_code" ) )
            {
                sb.append( map.get( "postal_code" ) );
            }

            if ( map.containsKey( "locality" ) )
            {
                if ( map.containsKey( "postal_code" ) )
                {
                    sb.append( " " );
                }

                sb.append( map.get( "locality" ) );
            }
        }

        if ( map.containsKey( "country" ) )
        {
            if ( sb.length() > 0 )
            {
                sb.append( ", " );
            }

            sb.append( map.get( "country" ) );
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

    private static class GeoReverseHolder
    {
        private static final GeoReverse INSTANCE = new GeoReverse();
    }
}
