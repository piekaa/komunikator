package pl.noip.piekaa.bondaruktuiwaniuk2.services.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import pl.noip.piekaa.bondaruktuiwaniuk2.MessagesActivity;
import pl.noip.piekaa.bondaruktuiwaniuk2.R;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-06-22.
 */

public class MessageNotification
{

    private static Context context;

    private static final int ID = 123123123;

    public static boolean working = true;

    public static void setContext(Context context)
    {
        MessageNotification.context = context;
    }

    public static void showNotification(Message message)
    {
        if( !working )
            return;

        Intent intent = new Intent(context, MessagesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context);

        builder
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.drawable.small_ikonka)
                .setContentTitle("Nowa wiadomość")
                .setTicker(message.getTextContent())
                .setContentText(message.getTextContent())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);



        Notification notification = builder.build();
        NotificationManagerCompat.from(context).notify(ID, notification);

    }

    public static void turnOff()
    {
        working = false;
    }
    public static void turnOn()
    {
        working = true;
    }
}
