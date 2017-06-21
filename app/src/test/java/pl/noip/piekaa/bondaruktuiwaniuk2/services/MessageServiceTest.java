package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import pl.noip.piekaa.bondaruktuiwaniuk2.Consts;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaJsonRestClient;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageNotFoundException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageSendErrorException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.MessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl.MessageUrlProvider;

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
        messageService = new MessageService(new PiekaJsonRestClient(1000), new MessageUrlProvider(Consts.kobaHost));
        badConfiguredMessageService = new MessageService(new PiekaJsonRestClient(1000), new MessageUrlProvider("http://cyckiNaGiewoncie"));
    }

    @Test
    public void getsMessageWithId40() throws MessageNotFoundException
    {
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

    @Test
    public void getsUnreadMessages() throws MessageNotFoundException
    {
        List<Message> messages =  messageService.getUnreadMessagesByReciverId(01L);

        assertNotEquals(0, messages.size());

    }

    private Long testerSenderId=123123123L;
    private Long testerReciverId=321321312L;

    @Test
    public void marksMessageAsRead() throws MessageException
    {
        Message message = new Message("Some text", testerSenderId, testerReciverId);
        messageService.sendMessage(message);

        List<Message> messages = messageService.getUnreadMessagesByReciverId(testerReciverId);

        System.out.println(messages.size());

        assertTrue( messages.stream().anyMatch(m -> m.getTimestamp().equals(message.getTimestamp())) );

        Optional<Message> myMessage = messages.stream().filter( m -> m.getTimestamp().equals(message.getTimestamp())).findFirst();

        messageService.markAsRead(myMessage.get());

        messages = messageService.getUnreadMessagesByReciverId(testerReciverId);

        assertFalse( messages.stream().anyMatch(m -> m.getTimestamp().equals(message.getTimestamp())) );




    }



}