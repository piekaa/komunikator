package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import android.os.AsyncTask;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-04-09.
 */

class AsyncMessageService implements IAsyncMessageService
{


    private AsyncMessageService lock;
    private IMessageService messageServicefirstHost;
    private IMessageService messageServicesecondHost;
    private boolean didFail;
    private boolean didSucceed;


    public AsyncMessageService(IMessageService messageServicefirstHost, IMessageService messageServicesecondHost)
    {
        this.messageServicefirstHost = messageServicefirstHost;
        this.messageServicesecondHost = messageServicesecondHost;
        lock = this;
    }

    public class GetMessageTask extends AsyncTask<Long, Float, Message>
    {

        private IMessageService internalMessageService;

        public GetMessageTask(IMessageService internalMessageService)
        {
            this.internalMessageService = internalMessageService;
        }

        @Override
        protected Message doInBackground(Long... ids)
        {
            Long id = ids[0];
            Message result = null;
            try
            {
                result = internalMessageService.getMessageById(id);
            }
            catch (MessageNotFoundException e)
            {
                //    e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Message message)
        {
            synchronized (lock)
            {
                if (message == null)
                {
                    if (didFail == true )
                    {
                        failedGetMessageHandler.handle();
                    }
                    didFail = true;
                }
                else
                {
                    if (didSucceed == false)
                    {
                        succeedGetMessageHandler.handleMassage(message);
                    }
                    didSucceed = true;
                }
            }
        }
    }




    public class SendMessageTask extends AsyncTask<Message, Float, Void>
    {

        private IMessageService internalMessageService;
        private boolean didSendFail;
        public SendMessageTask(IMessageService internalMessageService)
        {
            this.internalMessageService = internalMessageService;
            didSendFail = false;
        }

        @Override
        protected Void doInBackground(Message... messages)
        {
            Message message = messages[0];
            Message result = null;
            try
            {
                internalMessageService.sendMessage(message);
                didSendFail = false;
            }
            catch (MessageSendErrorException e)
            {
                didSendFail = true;
                //    e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void obj)
        {
            synchronized (lock)
            {
                if (didSendFail)
                {
                    if (didFail == true )
                    {
                        failedSendMessageHandler.handle();
                    }
                    didFail = true;
                }
                else
                {
                    if (didSucceed == false)
                    {
                        succeedSendMessageHandler.handle();
                    }
                    didSucceed = true;
                }
            }
        }
    }






    private IMessageResponseHandler succeedGetMessageHandler;
    private IVoidResponseHandler failedGetMessageHandler;

    @Override
    public void tryToGetMessage(Long id, IMessageResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
    {
        didSucceed = false;
        didFail = false;

        this.succeedGetMessageHandler = succeedHandler;
        this.failedGetMessageHandler = failedHandler;

        new GetMessageTask(messageServicefirstHost).execute(id);
        new GetMessageTask(messageServicesecondHost).execute(id);
    }


    private IVoidResponseHandler succeedSendMessageHandler;
    private IVoidResponseHandler failedSendMessageHandler;

    @Override
    public void tryToSendMessage(Message message, IVoidResponseHandler succeedHandler, IVoidResponseHandler failedHandler)
    {
        didSucceed = false;
        didFail = false;

        succeedSendMessageHandler = succeedHandler;
        failedSendMessageHandler = failedHandler;


        new SendMessageTask(messageServicefirstHost).execute(message);
        new SendMessageTask(messageServicesecondHost).execute(message);

    }
}
