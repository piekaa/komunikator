package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages;

import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-05-12.
 */

public interface OnMessagesListener
{
    void onMessages(List<Message> messages);
}
