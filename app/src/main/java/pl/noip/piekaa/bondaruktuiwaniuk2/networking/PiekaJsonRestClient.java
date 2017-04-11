package pl.noip.piekaa.bondaruktuiwaniuk2.networking;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by piekaa on 2017-04-08.
 */

public class PiekaJsonRestClient implements  IPiekaRestClient {



    // HTTP GET request
    public <T> PiekaHttpResponse<T> sendRequest(String url, String method, Type responseType, Object body)  {
        String parameters ="";
        System.out.println(parameters);
        URL httpUrl;

        PiekaHttpResponse<T> errorResponse = new PiekaHttpResponse<T>(-1, null);

        try {
            httpUrl = new URL(url);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return errorResponse;
        }

        HttpURLConnection con;
        try {
            con = (HttpURLConnection) httpUrl.openConnection();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return errorResponse;
        }
        // optional default is GET
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        try {
            con.setRequestMethod(method);
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return errorResponse;
        }
        con.setDoOutput(true);
        con.setDoInput(true);

        Gson g = new Gson();
        if( body != null )
        {
            // write body
            OutputStream outputStream;
            try {
                outputStream = con.getOutputStream();
                outputStream.write(  g.toJson(body).getBytes(Charset.forName("UTF-8")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return errorResponse;
            }

        }
        // get response code
        int responseCode;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return errorResponse;
        }



        BufferedReader in;
        StringBuffer response ;
        try {
            in = new BufferedReader( new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return errorResponse;
        }

        con.disconnect();
        //print result

        PiekaHttpResponse< T > result = new PiekaHttpResponse<T>(responseCode, (T)g.fromJson( response.toString(), responseType ) );
        return result;

    }



    // HTTP GET request
    public <T> PiekaHttpResponse<T> sendRequest(String url, Type responseType)  {

        return sendRequest(url, "GET", responseType, null);

    }



}