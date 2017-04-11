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
    @Before
    public void init()
    {
        messageService = new MessageService(new PiekaJsonRestClient(), new MessageUrlProvider(Consts.kobaHost));
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


}