package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking;

import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageNotFoundException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageSendErrorException;

/**
 * Created by piekaa on 2017-04-09.
 */

public interface IMessageService
{
    Message getMessageById(Long id) throws MessageNotFoundException;
    void sendMessage(Message message) throws MessageSendErrorException;
    void markAsRead(Message message) throws MessageException;
    List<Message> getOldMessages(Long olderThan, int howMany, Long id1, Long id2) throws MessageNotFoundException;
    List<Message> getUnreadMessages(Long myId) throws MessageNotFoundException;
}
