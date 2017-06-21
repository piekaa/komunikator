package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.ui.IMessagesView;

/**
 * Created by piekaa on 2017-05-10.
 */

public interface IClientSendingMessageService
{
    public void setMessagesView(IMessagesView messagesView);
    void sendMessage(Message message);
}
