package pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated;

import android.os.AsyncTask;

import java.util.concurrent.Executors;

import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageException;

/**
 * Created by piekaa on 2017-06-07.
 */

public class DoubleTask
{

    private boolean didFail = false;
    private boolean didSuccee = false;

    private IObjectResponseHandler successHandler;
    private IObjectResponseHandler failHandler;

    public class MessageTask extends AsyncTask<Void, Float, Object>
    {

        private boolean exceptionOccured = false;

        private IExecutable executable;
        public MessageTask(IExecutable executable)
        {
            this.executable= executable;
        }


        @Override
        protected Object doInBackground(Void... voids)
        {
            Object result = null;
            try
            {
                if( executable instanceof IExecutableObject)
                {
                    result = ((IExecutableObject) executable).execute();
                }
                else
                {
                    ((IExecutableVoid) executable).execute();
                }
            }
            catch (MessageException e)
            {
                e.printStackTrace();

//                System.out.println("DoubleTask: EXCEPTION OFFCURED!!");

                exceptionOccured = true;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Object object)
        {

//            System.out.println("DoubleTask: onPostExecute!!");

            synchronized (DoubleTask.this)
            {
                if( exceptionOccured )
                {
                    if( didFail )
                    {
                        failHandler.handle(null);
                    }
                    didFail = true;
                }
                else
                {
                    if( didSuccee == false)
                    {
                        successHandler.handle(object);
                    }
                    didSuccee = true;
                }
            }
        }
    }


    public void executeTaskObject(IObjectResponseHandler successHandler, IObjectResponseHandler failHandler, IExecutableObject executableFirst, IExecutableObject executableSecond)
    {
        this.successHandler = successHandler;
        this.failHandler = failHandler;

        new MessageTask(executableFirst).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new MessageTask(executableSecond).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void executeTaskVoid(IObjectResponseHandler successHandler, IObjectResponseHandler failHandler, IExecutableVoid executableFirst, IExecutableVoid executableSecond)
    {
        this.successHandler = successHandler;
        this.failHandler = failHandler;

        new MessageTask(executableFirst).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new MessageTask(executableSecond).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
