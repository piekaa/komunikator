package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.Consts;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaJsonRestClient;

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



    public void executeSendMessage(IMessageService ms1, IMessageService ms2)
    {

        asyncMessageService = new AsyncMessageService(ms1,ms2);

        asyncMessageService.tryToSendMessage(new Message("bla bla bla", 01L, 10L),
                    () -> {succeed++;}
                    ,
                    () -> {failed++;});
    }





}
