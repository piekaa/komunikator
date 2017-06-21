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


    private int timeout;

    public PiekaJsonRestClient(int timeout)
    {
        this.timeout = timeout;
    }

    // HTTP GET request
    public <T> PiekaHttpResponse<T> sendRequest(String url, String method, Type responseType, Object body)  {


        URL httpUrl;



//        System.out.println("PiekaJsonRestClient -> sendRequest -> " + url + " method: " + method);

     //   System.out.println("Beginning of function...");

        PiekaHttpResponse<T> errorResponse = new PiekaHttpResponse<T>(-1, null);

        try {
            httpUrl = new URL(url);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return errorResponse;
        }

   //     System.out.println("after first try");

        HttpURLConnection con;
        try {
            con = (HttpURLConnection) httpUrl.openConnection();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return errorResponse;
        }
      //  System.out.println("after second try");

        // optional default is GET
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        try {
            con.setRequestMethod(method);
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            System.out.println("Setting request method didn't work?!");
            e.printStackTrace();
            return errorResponse;
        }

   //     System.out.println("after third try");


        if( !method.equals("GET"))
            con.setDoOutput(true);
        con.setDoInput(true);

//        System.out.println("timeout is: " + timeout);
//        System.out.println("Method: " + con.getRequestMethod() );

        con.setConnectTimeout(timeout);

        Gson g = new Gson();


        try
        {
    //        System.out.println("Before connect");
            con.connect();
    //        System.out.println("After connect");
        }
        catch (IOException e)
        {
     //       System.out.println("Cannot connect! " + e.getMessage());
            e.printStackTrace();
        }

    //    System.out.println("After connect");

        if( body != null )
        {

      //      System.out.println("Body not null");
            // write body
            OutputStream outputStream;
            try {
                outputStream = con.getOutputStream();
                outputStream.write(  g.toJson(body).getBytes(Charset.forName("UTF-8")));
            } catch (IOException e) {

       //         System.out.println("Catched exception " + e.getMessage());

                // TODO Auto-generated catch block
                e.printStackTrace();
                return errorResponse;
            }

        }


  //      System.out.println("after 4th try");

        // get response code
        int responseCode;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return errorResponse;
        }


    //    System.out.println("after 5th try");


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

    //    System.out.println("after 6th try");

        con.disconnect();

        //print result

        PiekaHttpResponse< T > result = new PiekaHttpResponse<T>(responseCode, (T)g.fromJson( response.toString(), responseType ) );
        return result;

    }



    // HTTP GET request
    public <T> PiekaHttpResponse<T> sendRequest(String url, Type responseType)  {


//        System.out.println("PiekaJsonRestClient -> sendRequest -> " + url);

        return sendRequest(url, "GET", responseType, null);

    }



}