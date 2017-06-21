package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated.IVoidResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IAsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageListResponse;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageQueueService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.MessageQueueService;

import static org.junit.Assert.*;
/**
 * Created by piekaa on 2017-04-13.
 */


public class MessageQueueServiceTest
{
    IMessageQueueService queueService;

    IMessageQueueService failingQueueService;

    int succeedCount;
    Message message;
    @Before
    public void init()
    {

        queueService = new MessageQueueService(new IAsyncMessageService()
        {
            @Override
            public void tryToGetMessage(Long id, IMessageResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
            {

            }

            @Override
            public void tryToSendMessage(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
            {
                succeedHandler.handle();
            }

            @Override
            public void tryToGetUnreadMessageByReciverId(Long reciverId, IMessageListResponse succeedHandler, IVoidResponseHandler failedHandler)
            {

            }

            @Override
            public void tryMarkAsRead(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
            {

            }
        });


        failingQueueService = new MessageQueueService(new IAsyncMessageService()
        {
            @Override
            public void tryToGetMessage(Long id, IMessageResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
            {

            }

            @Override
            public void tryToSendMessage(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
            {
                failedHandler.handle();
            }

            @Override
            public void tryToGetUnreadMessageByReciverId(Long reciverId, IMessageListResponse succeedHandler, IVoidResponseHandler failedHandler)
            {

            }

            @Override
            public void tryMarkAsRead(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
            {

            }
        });


        message = new Message("myk myk myk disco polo", 01L, 100L);
        succeedCount = 0;
        queueService.setOnMessageSentListener( (m) -> succeedCount++  );
        failingQueueService.setOnMessageSentListener( (m) -> succeedCount++  );
    }


    @Test
    public void shouldSendMessage()
    {

        queueService.addMessageToQueue(message);

        queueService.tryToSendMessage();
        assertEquals(1, succeedCount);
    }


    @Test
    public void shouldNotSendMessage()
    {

        failingQueueService.addMessageToQueue(message);
        failingQueueService.addMessageToQueue(message);
        failingQueueService.addMessageToQueue(message);



        failingQueueService.tryToSendMessage();
        assertEquals(0, succeedCount);
    }

    @Test
    public void shouldSend5Messages()
    {
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);



        queueService.tryToSendMessage();
        queueService.tryToSendMessage();
        queueService.tryToSendMessage();
        queueService.tryToSendMessage();
        queueService.tryToSendMessage();
        assertEquals(5, succeedCount);
    }


    @Test
    public void shouldSend5MessagesMixedOrder()
    {

        queueService.tryToSendMessage();
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.tryToSendMessage();
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.tryToSendMessage();
        queueService.tryToSendMessage();
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.addMessageToQueue(message);
        queueService.tryToSendMessage();
        queueService.tryToSendMessage();
        assertEquals(5, succeedCount);
    }




}
