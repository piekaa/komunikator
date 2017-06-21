package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated.IVoidResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageListResponse;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageResponseHandler;

/**
 * Created by piekaa on 2017-04-09.
 */

public interface IAsyncMessageService
{
    void tryToGetMessage(Long id, IMessageResponseHandler succeedHandler, IVoidResponseHandler failedHandler);
    void tryToSendMessage(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler);
    void tryToGetUnreadMessageByReciverId(Long reciverId , IMessageListResponse succeedHandler, IVoidResponseHandler failedHandler);
    void tryMarkAsRead(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler);
}
