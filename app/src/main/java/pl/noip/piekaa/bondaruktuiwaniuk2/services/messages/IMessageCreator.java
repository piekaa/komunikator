package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-05-11.
 */

public interface IMessageCreator
{
    Message createMessage(String text);
}
