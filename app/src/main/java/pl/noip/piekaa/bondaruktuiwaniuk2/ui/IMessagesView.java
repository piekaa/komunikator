package pl.noip.piekaa.bondaruktuiwaniuk2.ui;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-05-10.
 */

public interface IMessagesView
{
    void addToMeMessage(Message message);
    void addFromMeMessageSending(Message message);
    void setFromMeMessageAsSent(Message message);

    void addOldToMeMessage(Message message);
    void addOldFromMe(Message message);

}
