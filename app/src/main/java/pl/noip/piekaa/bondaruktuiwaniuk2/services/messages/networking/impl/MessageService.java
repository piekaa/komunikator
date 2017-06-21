package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl;

import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.MessageList;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.IPiekaRestClient;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaHttpResponse;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageNotFoundException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageSendErrorException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageUrlProvider;

/**
 * Created by piekaa on 2017-04-08.
 */

public class MessageService implements IMessageService
{

    public Message getMessageById(Long id) throws MessageNotFoundException
    {
        String url = urlProvider.getMessageByIdUrl(id);
        PiekaHttpResponse<Message> response = piekaRestClient.sendRequest(url, Message.class);

        if(response.getResponseCode() == -1 ) throw  new MessageNotFoundException();
        if( response.getResponseObject() == null ) throw new MessageNotFoundException();

        return response.getResponseObject();
    }

    @Override
    public void sendMessage(Message message) throws MessageSendErrorException
    {
     //   System.out.println("Sending message...");

        String url = urlProvider.getSendMessageUrl();


     //   System.out.println("Before send");
        PiekaHttpResponse<Object> response = piekaRestClient.sendRequest(url, "POST",  Object.class, message);
     //   System.out.println("After send");

      //  System.out.println("Is it null?" + response.getResponseCode());

        if(response.getResponseCode() == -1 ) throw  new MessageSendErrorException();


    }

    @Override
    public List<Message> getUnreadMessagesByReciverId(Long reciverId) throws MessageNotFoundException
    {
        String url = urlProvider.getUnreadMessagesByReciverIDUrl(reciverId);

//        System.out.println("Trying to get unreaded messages by revicer id: " + url);

        PiekaHttpResponse<MessageList> response = piekaRestClient.sendRequest(url, MessageList.class);

        if(response.getResponseCode() == -1 ) throw  new MessageNotFoundException();
        if( response.getResponseObject() == null ) throw new MessageNotFoundException();

        return response.getResponseObject().getMessages();
    }

    @Override
    public void markAsRead(Message message) throws MessageException
    {
        String url = urlProvider.getMarkMessageAsReadUrl(message.getMessageId());
        PiekaHttpResponse<Object> response = piekaRestClient.sendRequest(url, "PUT",  Object.class, message);
        if(response.getResponseCode() == -1 ) throw  new MessageSendErrorException();
    }


    private IPiekaRestClient piekaRestClient;
    private IMessageUrlProvider urlProvider;


    public MessageService(IPiekaRestClient piekaRestClient, IMessageUrlProvider urlProvider) {
        this.piekaRestClient = piekaRestClient;
        this.urlProvider = urlProvider;
    }
}
