package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-04-13.
 */

public interface MessageSentListener
{
    void onMessageSend(Message message);
}
