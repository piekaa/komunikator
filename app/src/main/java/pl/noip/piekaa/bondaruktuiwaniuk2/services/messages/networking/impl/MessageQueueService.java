package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl;

import java.util.LinkedList;
import java.util.Queue;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated.IVoidResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IAsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageQueueService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.MessageSentListener;

/**
 * Created by piekaa on 2017-04-13.
 */

public class MessageQueueService implements IMessageQueueService
{
    Queue<Message> messages;
    boolean sending;
    Message currentMessage;

    public MessageSentListener sentListener;

    public class SuccessHandler implements IVoidResponseHandler
    {
        @Override
        public void handle()
        {
            sending = false;
            sentListener.onMessageSend( currentMessage );
            messages.poll();
        }
    }

    public class FailHandler implements IVoidResponseHandler
    {

        @Override
        public void handle()
        {
            sending = false;
        }
    }


    private IAsyncMessageService asyncMessageService;
    {
        sending = false;
        messages = new LinkedList<>();
    }



    FailHandler failHandler;
    SuccessHandler successHandler;


    public MessageQueueService(IAsyncMessageService asyncMessageService)
    {
        this.asyncMessageService = asyncMessageService;
        failHandler = new FailHandler();
        successHandler = new SuccessHandler();
    }

    @Override
    public void addMessageToQueue(Message message)
    {
        messages.add(message);
    }

    @Override
    public void tryToSendMessage()
    {
        if( sending == false && !messages.isEmpty() )
        {
            currentMessage = messages.peek();
            sending = true;
            asyncMessageService.tryToSendMessage(currentMessage, successHandler, failHandler);
        }
    }



    @Override
    public void setOnMessageSentListener(MessageSentListener sentListener)
    {
        this.sentListener = sentListener;
    }
}
