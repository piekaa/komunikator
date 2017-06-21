package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IClientSendingMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageQueueService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.MessageSentListener;
import pl.noip.piekaa.bondaruktuiwaniuk2.ui.IMessagesView;

/**
 * Created by piekaa on 2017-05-10.
 */

public class ClientSendingMessageService implements IClientSendingMessageService, MessageSentListener
{

    IMessageQueueService messageQueue;
    IMessagesView messagesView;

    public ClientSendingMessageService(IMessageQueueService messageQueue)
    {
        this.messageQueue = messageQueue;
        messageQueue.setOnMessageSentListener(this);
    }

    @Override
    public void setMessagesView(IMessagesView messagesView)
    {
        this.messagesView = messagesView;
    }

    @Override
    public void sendMessage(Message message)
    {
        messagesView.addFromMeMessageSending(message);
        messageQueue.addMessageToQueue(message);
        messageQueue.tryToSendMessage();
    }

    @Override
    public void onMessageSend(Message message)
    {
        messagesView.setFromMeMessageAsSent(message);
    }
}
