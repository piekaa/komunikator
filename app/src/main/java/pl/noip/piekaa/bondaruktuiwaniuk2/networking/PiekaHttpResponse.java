package pl.noip.piekaa.bondaruktuiwaniuk2.networking;

/**
 * Created by piekaa on 2017-04-08.
 */

public class PiekaHttpResponse<T>
{
    private int responseCode;
    private T responseObject;
    public int getResponseCode() {
        return responseCode;
    }
    public T getResponseObject() {
        return responseObject;
    }
    public PiekaHttpResponse(int responseCode, T responseObject) {
        super();
        this.responseCode = responseCode;
        this.responseObject = responseObject;
    }
    public PiekaHttpResponse() {
        super();
    }




}