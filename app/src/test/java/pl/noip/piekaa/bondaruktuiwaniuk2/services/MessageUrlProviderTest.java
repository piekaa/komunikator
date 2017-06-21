package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.Consts;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageUrlProvider;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl.MessageUrlProvider;

import static org.junit.Assert.*;

/**
 * Created by piekaa on 2017-04-08.
 */
public class MessageUrlProviderTest
{



    private IMessageUrlProvider messageUrlProvider;

    @Before
    public void init()
    {
        messageUrlProvider = new MessageUrlProvider(Consts.kobaHost);
    }

    String getMessagesId10Url = Consts.kobaHost +"messages/10";

    @Test
    public void getMessageUrlById()
    {
        String url = messageUrlProvider.getMessageByIdUrl( 10L);

        assertEquals(getMessagesId10Url, url);

    }

    String sendMessageCorrectUrl = Consts.kobaHost + "messages";
    @Test
    public void sendMessageUrl()
    {
        assertEquals(sendMessageCorrectUrl, messageUrlProvider.getSendMessageUrl());
    }

    String unreadMessages = Consts.kobaHost + "messages/unread/10";
    @Test
    public void getUnreadMessagesUrl()
    {
        assertEquals(unreadMessages, messageUrlProvider.getUnreadMessagesUrl(10L));
    }

    String markAsRead = Consts.kobaHost + "messages/10";
    @Test
    public void getMarkAsRead()
    {
        assertEquals(markAsRead, messageUrlProvider.getMarkMessageAsReadUrl(10L));
    }


    String moreMessages = Consts.kobaHost + "messages/old/123456/10/1/2";
    @Test
    public void getMoreMessages() throws Exception
    {
        assertEquals(moreMessages, messageUrlProvider.getMoreMessagesUrl(123456L,10,1L,2L));
    }

}