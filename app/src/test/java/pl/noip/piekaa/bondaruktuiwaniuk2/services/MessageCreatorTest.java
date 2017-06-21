package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageCreator;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl.MessageCreator;

import static org.junit.Assert.*;

/**
 * Created by piekaa on 2017-05-11.
 */
public class MessageCreatorTest
{
    IMessageCreator messageCreator;

    Long senderId;
    Long reciverId;

    @Before
    public void init()
    {
        senderId = 01L;
        reciverId = 02L;
        messageCreator = new MessageCreator(senderId, reciverId);
    }

    @Test
    public void createMessage()
    {
        String textContent = "test";
        Message message = messageCreator.createMessage(textContent);

        assertEquals(textContent, message.getTextContent());
        assertEquals(senderId, message.getSenderId());
        assertEquals(reciverId, message.getReciverId());

    }

}