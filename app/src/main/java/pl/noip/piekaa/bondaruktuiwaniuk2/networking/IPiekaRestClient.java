package pl.noip.piekaa.bondaruktuiwaniuk2.networking;

import java.lang.reflect.Type;

/**
 * Created by piekaa on 2017-04-08.
 */

public interface IPiekaRestClient
{
    <T> PiekaHttpResponse<T> sendRequest(String url, Type responseType);
    <T> PiekaHttpResponse<T> sendRequest(String url, String method, Type responseType, Object body);
}
