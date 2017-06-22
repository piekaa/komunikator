package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl;

import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated.DoubleTask;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated.IVoidResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IAsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageListResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageResponseHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageService;

/**
 * Created by piekaa on 2017-04-09.
 */

public class AsyncMessageService implements IAsyncMessageService
{
    private IMessageService messageServicefirstHost;
    private IMessageService messageServicesecondHost;

    public AsyncMessageService(IMessageService messageServicefirstHost, IMessageService messageServicesecondHost)
    {
        this.messageServicefirstHost = messageServicefirstHost;
        this.messageServicesecondHost = messageServicesecondHost;
    }


    @Override
    public void tryToGetMessage(Long id, IMessageResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
    {
        new DoubleTask().executeTaskObject(
                (e) ->
                {
                    succeedHandler.handleMassage((Message)e);
                },
                (e) ->
                {
                    failedHandler.handle();
                },
                () -> messageServicefirstHost.getMessageById(id),
                () -> messageServicesecondHost.getMessageById(id)
        );
    }

    @Override
    public void tryToSendMessage(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
    {
        new DoubleTask().executeTaskVoid(
                (e) ->
                {
                    succeedHandler.handle();
                },
                (e) ->
                {
                    failedHandler.handle();
                },
                () -> messageServicefirstHost.sendMessage(message),
                () -> messageServicesecondHost.sendMessage(message)
        );
    }


    @Override
    public void tryMarkAsRead(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
    {
        new DoubleTask().executeTaskVoid(
                (e) ->
                {
                    succeedHandler.handle();
                },
                (e) ->
                {
                    failedHandler.handle();
                },
                () -> messageServicefirstHost.markAsRead(message),
                () -> messageServicesecondHost.markAsRead(message)
        );
    }

    @Override
    public void tryToGetMoreMessages(Long olderThan, int howMany, Long id1, Long id2, IMessageListResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
    {
        new DoubleTask().executeTaskObject(
                (e) ->
                {
                    succeedHandler.handle((List<Message>)e);
                },
                (e) ->
                {
                    failedHandler.handle();
                },
                () -> messageServicefirstHost.getOldMessages(olderThan,howMany,id1,id2),
                () -> messageServicesecondHost.getOldMessages(olderThan,howMany,id1,id2)
        );
    }

    @Override
    public void tryToGetUnreadMessagesByReciverId(long myId, IMessageListResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
    {
        new DoubleTask().executeTaskObject(
                (e) ->
                {
                    succeedHandler.handle((List<Message>)e);
                },
                (e) ->
                {
                    failedHandler.handle();
                },
                () -> messageServicefirstHost.getUnreadMessages(myId),
                () -> messageServicesecondHost.getUnreadMessages(myId)
        );
    }


}
