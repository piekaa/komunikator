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
        };
        successingMessageService = new IMessageService()
        {
            @Override
            public Message getMessageById(Long id) throws MessageNotFoundException
            {
                return new Message();
            }
        };

    }






    @Test
    public void oneSuccessingOneFailing()
    {

        asyncMessageService = new AsyncMessageService(failingMessageService,successingMessageService);


        asyncMessageService.tryToGetMessage(0L,
                (message) -> {succeed++;}
                ,
                () -> {failed++;});

        assertEquals(succeed, 1);
        assertEquals(failed, 0);
    }

    @Test
    public void bothSuccessing()
    {
        asyncMessageService = new AsyncMessageService(successingMessageService,successingMessageService);


        asyncMessageService.tryToGetMessage(0L,
                (message) -> {succeed++;}
                ,
                () -> {failed++;});

        assertEquals(succeed, 1);
        assertEquals(failed, 0);
    }


    @Test
    public void bothFailing()
    {
        asyncMessageService = new AsyncMessageService(failingMessageService,failingMessageService);

        asyncMessageService.tryToGetMessage(0L,
                (message) -> {succeed++;}
                ,
                () -> {failed++;});

        assertEquals(succeed, 0);
        assertEquals(failed, 1);
    }



}
