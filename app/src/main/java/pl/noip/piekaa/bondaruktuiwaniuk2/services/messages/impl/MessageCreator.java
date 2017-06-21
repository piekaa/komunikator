package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageCreator;

/**
 * Created by piekaa on 2017-05-11.
 */

public class MessageCreator implements IMessageCreator
{

    private Long senderId;
    private Long reciverId;

    public MessageCreator(Long senderId, Long reciverId)
    {
        this.senderId = senderId;
        this.reciverId = reciverId;
    }

    @Override
    public Message createMessage(String text)
    {
        return new Message(text, senderId, reciverId);
    }
}
