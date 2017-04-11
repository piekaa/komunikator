package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import android.os.AsyncTask;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-04-09.
 */

class AsyncMessageService implements IAsyncMessageService
{


    private AsyncMessageService lock;
    private IMessageService messageServiceKobaHost;
    private IMessageService messageServiceNotKobaHost;
    private boolean didFail;
    private boolean didSucceed;


    public AsyncMessageService(IMessageService messageServiceKobaHost, IMessageService messageServiceNotKobaHost)
    {
        this.messageServiceKobaHost = messageServiceKobaHost;
        this.messageServiceNotKobaHost = messageServiceNotKobaHost;
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
                        failedHandler.onGetMessageFailed();
                    }
                    didFail = true;
                }
                else
                {
                    if (didSucceed == false)
                    {
                        succeedHandler.onGetMessageSucceed(message);
                    }
                    didSucceed = true;
                }
            }
        }
    }

    private IOnGetMessageSucceed succeedHandler;
    private IOnGetMessageFailed failedHandler;

    @Override
    public void tryToGetMessage(Long id, IOnGetMessageSucceed succeedHandler, IOnGetMessageFailed failedHandler)
    {
        this.succeedHandler = succeedHandler;
        this.failedHandler = failedHandler;

        new GetMessageTask(messageServiceKobaHost).execute(id);
        new GetMessageTask(messageServiceNotKobaHost).execute(id);
    }
}
