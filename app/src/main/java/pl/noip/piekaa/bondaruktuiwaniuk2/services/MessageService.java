package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.IPiekaRestClient;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaHttpResponse;

/**
 * Created by piekaa on 2017-04-08.
 */

public class MessageService implements IMessageService
{

    public Message getMessageById(Long id) throws MessageNotFoundException
    {
        String url = urlProvider.getMessageUrlById(id);
        PiekaHttpResponse<Message> response = piekaRestClient.sendRequest(url, Message.class);

        if(response.getResponseCode() == -1 ) throw  new MessageNotFoundException();
        if( response.getResponseObject() == null ) throw new MessageNotFoundException();

        return response.getResponseObject();
    }

    private IPiekaRestClient piekaRestClient;
    private IMessageUrlProvider urlProvider;


    public MessageService(IPiekaRestClient piekaRestClient, IMessageUrlProvider urlProvider) {
        this.piekaRestClient = piekaRestClient;
        this.urlProvider = urlProvider;
    }
}