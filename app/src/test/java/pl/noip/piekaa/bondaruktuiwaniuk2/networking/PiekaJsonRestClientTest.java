package pl.noip.piekaa.bondaruktuiwaniuk2.networking;

/**
 * Created by piekaa on 2017-04-08.
 */
import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.networking.IPiekaRestClient;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaHttpResponse;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaJsonRestClient;

import static org.junit.Assert.*;


public class PiekaJsonRestClientTest
{

    IPiekaRestClient restClient;

    String jsonPlaceholderUrl = "https://jsonplaceholder.typicode.com/posts/1";

    @Before
    public void init()
    {
        restClient = new PiekaJsonRestClient(1000);
    }



    public class PlaceholderObject
    {
        String userId;
        String id;
        String title;
        String body;


        @Override
        public String toString() {
            return "PlaceholderObject{" +
                    "userId='" + userId + '\'' +
                    ", id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }

    @Test
    public void simpleJsonPlaceholderGetNotEmpty()
    {
        PiekaHttpResponse<PlaceholderObject> response = restClient.sendRequest(jsonPlaceholderUrl, PlaceholderObject.class);

        assertNotEquals(-1, response.getResponseCode());

        System.out.println( response.getResponseObject().toString());
    }


    @Test
    public void badUrlResponseCodeMinusOneAndReponseNull()
    {
        PiekaHttpResponse<Object> response = restClient.sendRequest("http://osdafiojfr", Object.class);

        assertEquals(response.getResponseCode(), -1);
        assertNull(response.getResponseObject());

    }





}
