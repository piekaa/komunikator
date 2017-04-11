package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.Consts;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaJsonRestClient;

import static org.junit.Assert.*;

/**
 * Created by piekaa on 2017-04-08.
 */
public class MessageServiceTest {

    IMessageService messageService;
    IMessageService badConfiguredMessageService;
    @Before
    public void init()
    {
        messageService = new MessageService(new PiekaJsonRestClient(), new MessageUrlProvider(Consts.kobaHost));
        badConfiguredMessageService = new MessageService(new PiekaJsonRestClient(), new MessageUrlProvider("http://cyckiNaGiewoncie"));
    }

    @Test
    public void getsMessageWithId40() throws MessageNotFoundException {
        Message message = messageService.getMessageById(40L);

        assertNotNull(message);

        assertEquals(new Long(40L), message.getMessageId());
    }

    @Test(expected = MessageNotFoundException.class)
    public void getsNotExistingMessageAndThrowsNotFoundException() throws MessageNotFoundException
    {
        messageService.getMessageById(1423423551L);
    }

    @Test
    public void sendMessageTest() throws MessageSendErrorException
    {
        Message message = new Message("new message", 00L, 01L);
        messageService.sendMessage(message);
    }


    @Test(expected = MessageSendErrorException.class)
    public void badConfiguredMessageServiceThrowsExceptionOnSend() throws MessageSendErrorException
    {
        Message message = new Message("new message", 00L, 01L);
        badConfiguredMessageService.sendMessage(message);
    }

}