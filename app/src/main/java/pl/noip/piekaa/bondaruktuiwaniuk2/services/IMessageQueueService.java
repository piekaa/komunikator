package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-04-13.
 */

public interface IMessageQueueService
{
    void addMessageToQueue(Message message);
    void tryToSendMessage();
    void setOnMessageSentListener(MessageSentListener sentListener);
}
