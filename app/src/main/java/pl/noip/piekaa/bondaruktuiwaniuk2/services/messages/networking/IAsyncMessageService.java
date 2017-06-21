package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated.IVoidResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageListResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl.NetworkMessageHandlerHandler;

/**
 * Created by piekaa on 2017-04-09.
 */

public interface IAsyncMessageService
{
    void tryToGetMessage(Long id, IMessageResponseHandler succeedHandler, IVoidResponseHandler failedHandler);
    void tryToSendMessage(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler);
    void tryMarkAsRead(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler);
    void tryToGetMoreMessages(Long olderThan, int howMany, Long id1, Long id2, IMessageListResponseHandler succeedHandler, IVoidResponseHandler failedHandler);
    void tryToGetUnreadMessagesByReciverId(long myId, IMessageListResponseHandler networkMessageHandlerHandler, IVoidResponseHandler networkMessageHandlerHandler1);
}
