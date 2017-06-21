package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages;

import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-05-12.
 */

public interface IMessageListResponse
{
    void handle(List<Message> messages);
}
