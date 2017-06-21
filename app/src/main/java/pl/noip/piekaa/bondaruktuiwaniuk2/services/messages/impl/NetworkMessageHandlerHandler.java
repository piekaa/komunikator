package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl;

import java.util.LinkedList;
import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated.IVoidResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageListResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IAsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.INetworkMessageListener;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.INetworkMessageProvider;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.OnMessageListener;
import pl.noip.piekaa.bondaruktuiwaniuk2.ui.IMessagesView;

/**
 * Created by piekaa on 2017-05-12.
 */

public class NetworkMessageHandlerHandler implements INetworkMessageProvider, INetworkMessageListener, IMessageListResponseHandler, IVoidResponseHandler
{

    IMessagesView messagesView;

    private IAsyncMessageService asyncMessageService;
    private long myId;
    private boolean processing;

    public NetworkMessageHandlerHandler(IAsyncMessageService asyncMessageService, long myId)
    {
        this.asyncMessageService = asyncMessageService;
        this.myId = myId;
        processing = false;
    }

    List<OnMessageListener> listeners = new LinkedList<>();

    @Override
    public void setMessagesView(IMessagesView messagesView)
    {
        this.messagesView = messagesView;
    }

    @Override
    public void addOnMessageListener(OnMessageListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void removeOnMessageListener(OnMessageListener listener)
    {
        listeners.remove(listener);
    }

    @Override
    public void tryGetUnreadedMessages()
    {
     //   System.out.println("NetworkMessageHandlerHandler: tryGetUnreadedMessages() -> 1st");
        if( processing == false )
        {
            System.out.println("NetworkMessageHandlerHandler: tryGetUnreadedMessages() -> 2nd");
            processing = true;
            asyncMessageService.tryToGetUnreadMessagesByReciverId(myId,this, this);
        }

    }

    @Override
    public void handle()
    {
//        System.out.println("Fail Handle");
        processing = false;
    }

    @Override
    public void handle(List<Message> messages)
    {

//        System.out.println("Success handle");


//        System.out.println("Messages size: " + messages.size());
//        System.out.println(messages);


        for(Message message : messages)
        {
            for(OnMessageListener listener : listeners)
            {
                if( listener != null )
                    listener.onMessage(message);
            }

            if( messagesView != null )
                messagesView.addToMeMessage(message);

            asyncMessageService.tryMarkAsRead(message, () -> {}, () -> {});

        }

        System.out.println("Success handle end");

        processing = false;
    }
}
