package pl.noip.piekaa.bondaruktuiwaniuk2.core;

import android.content.Context;
import android.content.Intent;

import pl.noip.piekaa.bondaruktuiwaniuk2.Consts;
import pl.noip.piekaa.bondaruktuiwaniuk2.Vars;
import pl.noip.piekaa.bondaruktuiwaniuk2.androidServices.ScheduledService;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.IPiekaRestClient;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaJsonRestClient;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.INetworkMessageListener;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.INetworkMessageProvider;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl.NetworkMessageHandlerHandler;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.AsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.ClientSendingMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IAsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IClientSendingMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageCreator;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageQueueService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageUrlProvider;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl.MessageCreator;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.MessageQueueService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl.MessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl.MessageUrlProvider;

/**
 * Created by piekaa on 2017-04-08.
 */

public class Core
{


    private static Core instance = new Core();
    {
        initialize();
    }

    public static Core getInstance()
    {
        return instance;
    }

    private IMessageQueueService messageQueueService;

    private IAsyncMessageService asyncMessageService;

    private IMessageService kobaMessageService;
    private  IMessageService notKobaMessageService;

    private IMessageUrlProvider kobaUrlProvider;
    private  IMessageUrlProvider notKobaUrlProvider;

    private  IPiekaRestClient piekaRestClient;

    public IAsyncMessageService getAsyncMessageService()
    {
        return asyncMessageService;
    }

    private IClientSendingMessageService clientSendingMessageService;

    private IMessageCreator messageCreator;

    private  boolean initialized = false;

    private INetworkMessageListener networkMessageListener;
    private INetworkMessageProvider networkMessageProvider;

    private  void initialize()
    {
        if( !initialized)
        {
            handleDependencies();
            initialized = true;
        }
    }

    private final static int timeout = 2000;

    private  void handleDependencies()
    {

        kobaUrlProvider = new MessageUrlProvider(Consts.kobaHost);
        notKobaUrlProvider = new MessageUrlProvider(Consts.notKobaHost);


        System.out.println("Creating pieka rest json clinet with timeout: " + timeout);
        piekaRestClient = new PiekaJsonRestClient(timeout);

        kobaMessageService = new MessageService(piekaRestClient, kobaUrlProvider);
        notKobaMessageService = new MessageService(piekaRestClient, notKobaUrlProvider);

        asyncMessageService = new AsyncMessageService(notKobaMessageService, kobaMessageService);

        messageQueueService = new MessageQueueService(asyncMessageService);

        clientSendingMessageService = new ClientSendingMessageService(messageQueueService);


        messageCreator = new MessageCreator(01L, 01L);

        NetworkMessageHandlerHandler networkMessageHandler = new NetworkMessageHandlerHandler(asyncMessageService, 01L);
        networkMessageListener = networkMessageHandler;
        networkMessageProvider = networkMessageHandler;

        Vars.oldestTimestamp = System.currentTimeMillis();

    }


    private  int QueueServiceId = 12131;
    public  void startQueueService(Context context)
    {
        context.startService(new Intent(context, ScheduledService.class));
    }


    public  IMessageQueueService getMessageQueueService()
    {
        return messageQueueService;
    }

    public  IClientSendingMessageService getClientSendingMessageService()
    {
        return clientSendingMessageService;
    }

    public  IMessageCreator getMessageCreator()
    {
        return messageCreator;
    }

    public INetworkMessageListener getNetworkMessageListener()
    {
        return networkMessageListener;
    }

    public INetworkMessageProvider getNetworkMessageProvider()
    {
        return networkMessageProvider;
    }
}
