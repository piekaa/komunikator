package pl.noip.piekaa.bondaruktuiwaniuk2.androidServices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import pl.noip.piekaa.bondaruktuiwaniuk2.core.Core;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.INetworkMessageListener;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageQueueService;

public class ScheduledService extends Service implements Runnable
{
    IMessageQueueService queueService;

    INetworkMessageListener networkMessageListener;

    public ScheduledService()
    {
        queueService = Core.getInstance().getMessageQueueService();
        networkMessageListener = Core.getInstance().getNetworkMessageListener();
        new Thread(this).start();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private int frequency = 100;

    private int nmlFrequency = 50;
    private int nmlCounter = nmlFrequency;

    @Override
    public void run()
    {
        for (; ; )
        {

            queueService.tryToSendMessage();

            if( nmlCounter <= 0 ) // every 5 seconds
            {
             //   System.out.println("Trying to get unreaded message");

                nmlCounter = nmlFrequency;
                networkMessageListener.tryGetUnreadedMessages();


            }



            try
            {
                Thread.sleep(frequency);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            nmlCounter--;

        }
    }
}
