package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.MessageSentListener;

/**
 * Created by piekaa on 2017-04-13.
 */

public interface IMessageQueueService
{
    void addMessageToQueue(Message message);
    void tryToSendMessage();
    void setOnMessageSentListener(MessageSentListener sentListener);
}
