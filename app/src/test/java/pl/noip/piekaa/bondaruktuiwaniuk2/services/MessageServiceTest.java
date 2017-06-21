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

    private Long testerSenderId=123123123L;
    private Long testerReciverId=321321312L;

    @Test
    public void marksMessageAsRead() throws MessageException
    {
        Message message = new Message("Some text", testerSenderId, testerReciverId);
        messageService.sendMessage(message);

        List<Message> messages = messageService.getUnreadMessages(testerReciverId);

        System.out.println(messages.size());

        assertTrue( messages.stream().anyMatch(m -> m.getTimestamp().equals(message.getTimestamp())) );

        Optional<Message> myMessage = messages.stream().filter( m -> m.getTimestamp().equals(message.getTimestamp())).findFirst();

        messageService.markAsRead(myMessage.get());

        messages = messageService.getUnreadMessages(testerReciverId);

        assertFalse( messages.stream().anyMatch(m -> m.getTimestamp().equals(message.getTimestamp())) );

    }


    @Test
    public void unreadEmptyList() throws MessageException
    {
        Message message = new Message("Some text", testerSenderId, testerReciverId);
        messageService.sendMessage(message);

        List<Message> messages = messageService.getUnreadMessages(21313441234L);

        System.out.println(messages.size());
        System.out.println(messages);

        for (Message m : messages)
        {
            System.out.println(m);
        }


    }


}