package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.AsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IAsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageNotFoundException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageSendErrorException;

import static org.junit.Assert.*;

/**
 * Created by piekaa on 2017-04-09.
 */

public class AsyncMessageServiceTest
{

    IAsyncMessageService asyncMessageService;
    int succeed;
    int failed;
    IMessageService failingMessageService;
    IMessageService successingMessageService;

    @Before
    public void init()
    {
        succeed = 0;
        failed = 0;

        failingMessageService = new IMessageService()
        {
            @Override
            public Message getMessageById(Long id) throws MessageNotFoundException
            {
                throw new MessageNotFoundException();
            }

            @Override
            public void sendMessage(Message message) throws MessageSendErrorException
            {
                throw new MessageSendErrorException();
            }

            @Override
            public List<Message> getUnreadMessages(Long reciverId) throws MessageNotFoundException
            {
                 throw new MessageNotFoundException();
            }

            @Override
            public void markAsRead(Message message) throws MessageException
            {

            }

            @Override
            public List<Message> getOldMessages(Long olderThan, int howMany, Long id1, Long id2) throws MessageNotFoundException
            {
                throw new MessageNotFoundException();
            }
        };
        successingMessageService = new IMessageService()
        {
            @Override
            public Message getMessageById(Long id) throws MessageNotFoundException
            {
                return new Message();
            }

            @Override
            public void sendMessage(Message message) throws MessageSendErrorException
            {

            }

            @Override
            public List<Message> getUnreadMessages(Long reciverId) throws MessageNotFoundException
            {
                return new LinkedList<Message>();
            }

            @Override
            public void markAsRead(Message message) throws MessageException
            {

            }

            @Override
            public List<Message> getOldMessages(Long olderThan, int howMany, Long id1, Long id2) throws MessageNotFoundException
            {
                return new LinkedList<Message>();
            }
        };

    }


    public void executeGetMessage(IMessageService ms1, IMessageService ms2)
    {
        asyncMessageService = new AsyncMessageService(ms1,ms2);

        asyncMessageService.tryToGetMessage(0L,
                (message) -> {succeed++;}
                ,
                () -> {failed++;});
    }

    @Test
    public void getMessageOneSuccessingOneFailingFirstFailing()
    {

        executeGetMessage(failingMessageService, successingMessageService);

        assertEquals(succeed, 1);
        assertEquals(failed, 0);
    }

    @Test
    public void getMessageOneSuccessingOneFailingFirstSuccessing()
    {

        executeGetMessage(successingMessageService, failingMessageService);

        assertEquals(succeed, 1);
        assertEquals(failed, 0);
    }

    @Test
    public void getMessageBothSuccessing()
    {
        executeGetMessage(successingMessageService, successingMessageService);

        assertEquals(succeed, 1);
        assertEquals(failed, 0);
    }


    @Test
    public void getMessageBothFailing()
    {
        executeGetMessage(failingMessageService, failingMessageService);

        assertEquals(succeed, 0);
        assertEquals(failed, 1);
    }




 /// sendMessageTest

    @Test
    public void sendMessageOneSuccessingOneFailingFirstFailing()
    {

        executeSendMessage(failingMessageService, successingMessageService);

        assertEquals(succeed, 1);
        assertEquals(failed, 0);
    }

    @Test
    public void sendMessageOneSuccessingOneFailingFirstSuccessing()
    {

        executeSendMessage(successingMessageService, failingMessageService);

        assertEquals(1, succeed);
        assertEquals(0, failed);
    }

    @Test
    public void sendMessageBothSuccessing()
    {
        executeSendMessage(successingMessageService, successingMessageService);

        assertEquals(1, succeed);
        assertEquals(0, failed);
    }


    @Test
    public void sendMessageBothFailing()
    {
        executeSendMessage(failingMessageService, failingMessageService);

        assertEquals(0, succeed);
        assertEquals(1, failed);
    }




    //// get unread message test

    /// sendMessageTest

    @Test
    public void getUnreadOneSuccessingOneFailingFirstFailing()
    {

        executeGetUnreadMessages(failingMessageService, successingMessageService);
        assertEquals(succeed, 1);
        assertEquals(failed, 0);
    }

    @Test
    public void getUnreadOneSuccessingOneFailingFirstSuccessing()
    {

        executeGetUnreadMessages(successingMessageService, failingMessageService);

        assertEquals(1, succeed);
        assertEquals(0, failed);
    }

    @Test
    public void getUnreadBothSuccessing()
    {
        executeGetUnreadMessages(successingMessageService, successingMessageService);

        assertEquals(1, succeed);
        assertEquals(0, failed);
    }


    @Test
    public void getUnreadBothFailing()
    {
        executeGetUnreadMessages(failingMessageService, failingMessageService);

        assertEquals(0, succeed);
        assertEquals(1, failed);
    }



    public void executeGetUnreadMessages(IMessageService ms1, IMessageService ms2)
    {

        asyncMessageService = new AsyncMessageService(ms1,ms2);

        asyncMessageService.tryToGetUnreadMessagesByReciverId(01L,
                (m) -> {succeed++;}
                ,
                () -> {failed++;});

    }



    public void executeSendMessage(IMessageService ms1, IMessageService ms2)
    {

        asyncMessageService = new AsyncMessageService(ms1,ms2);

        asyncMessageService.tryToSendMessage(new Message("bla bla bla", 01L, 10L),
                    () -> {succeed++;}
                    ,
                    () -> {failed++;});
    }





}
