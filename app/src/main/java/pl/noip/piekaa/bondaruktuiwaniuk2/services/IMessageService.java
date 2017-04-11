package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-04-09.
 */

public interface IMessageService
{
    Message getMessageById(Long id) throws MessageNotFoundException;
    void sendMessage(Message message) throws MessageSendErrorException;
}
