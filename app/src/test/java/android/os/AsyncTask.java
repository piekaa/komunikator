package android.os;

import java.util.concurrent.Executor;

/**
 * Created by piekaa on 2017-04-09.
 */

public abstract class AsyncTask<Params, Progress, Result> {

    public static Executor THREAD_POOL_EXECUTOR;


    protected abstract Result doInBackground(Params... params);

    protected void onPostExecute(Result result) {
    }

    protected void onProgressUpdate(Progress... values) {
    }

    public AsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params... params) {


        Result result = doInBackground(params);
        onPostExecute(result);
        return this;
    }


    public AsyncTask<Params, Progress, Result> execute(Params... params) {


        Result result = doInBackground(params);
        onPostExecute(result);
        return this;
    }
}
