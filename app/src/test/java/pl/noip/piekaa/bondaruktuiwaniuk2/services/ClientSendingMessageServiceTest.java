package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.Consts;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaJsonRestClient;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.AsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.ClientSendingMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IClientSendingMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageQueueService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.MessageQueueService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.MessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl.MessageUrlProvider;
import pl.noip.piekaa.bondaruktuiwaniuk2.ui.IMessagesView;

import static org.junit.Assert.*;

/**
 * Created by piekaa on 2017-05-10.
 */
public class ClientSendingMessageServiceTest
{

    IClientSendingMessageService csms;
    MyMessageView mmv;
    @Before
    public void init()
    {
        IMessageQueueService messageQueueService;

        AsyncMessageService ams = new AsyncMessageService( new MessageService(new PiekaJsonRestClient(1000), new MessageUrlProvider(Consts.kobaHost)), new MessageService(new PiekaJsonRestClient(1000), new MessageUrlProvider(Consts.notKobaHost) ));
        mmv = new MyMessageView();
        messageQueueService = new MessageQueueService(ams);
        csms = new ClientSendingMessageService(messageQueueService);
        csms.setMessagesView(mmv);
    }

    @Test
    public void sendMessage()
    {
        assertEquals("",  mmv.content);

        Message m = new Message("Text", 10L, 11L);

        csms.sendMessage(m);

        assertEquals( "FromMeFromMeSent",  mmv.content );

    }

}


class MyMessageView implements IMessagesView
{

    public String content = "";

    @Override
    public void addToMeMessage(Message message)
    {
        content+="ToMe";
    }

    @Override
    public void addFromMeMessageSending(Message message)
    {
        content+="FromMe";
    }

    @Override
    public void setFromMeMessageAsSent(Message message)
    {
        content+="FromMeSent";
    }

    @Override
    public void addOldToMeMessage(Message message)
    {

    }

    @Override
    public void addOldFromMe(Message message)
    {

    }
}