package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages;

import pl.noip.piekaa.bondaruktuiwaniuk2.ui.IMessagesView;

/**
 * Created by piekaa on 2017-05-12.
 */

public interface INetworkMessageProvider
{
    void setMessagesView(IMessagesView messageView);
    void addOnMessageListener(OnMessageListener listener);
    void removeOnMessageListener(OnMessageListener listener);
}
