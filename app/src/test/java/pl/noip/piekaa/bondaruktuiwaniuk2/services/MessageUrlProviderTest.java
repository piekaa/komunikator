package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.Consts;

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
        String url = messageUrlProvider.getMessageUrlById( 10L);

        assertEquals(getMessagesId10Url, url);

    }

    String sendMessageCorrectUrl = Consts.kobaHost + "messages";
    @Test
    public void sendMessageUrl()
    {
        assertEquals(sendMessageCorrectUrl, messageUrlProvider.getSendMessageUrl());
    }

}