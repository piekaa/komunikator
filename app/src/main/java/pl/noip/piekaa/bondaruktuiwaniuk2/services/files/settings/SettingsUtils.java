package pl.noip.piekaa.bondaruktuiwaniuk2.services.files.settings;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.noip.piekaa.bondaruktuiwaniuk2.Vars;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Settings;

/**
 * Created by piekaa on 2017-06-22.
 */

public class SettingsUtils
{
    public static void loadSettings(Context context)
    {
        File file = new File(context.getFilesDir(), "settings.bin");
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream( file );
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return;
        }
        ObjectInputStream is = null;
        try
        {
            is = new ObjectInputStream(fis);
            Settings settings = (Settings) is.readObject();
            is.close();
            fis.close();
            Vars.myId = settings.getSenderId();
            Vars.reciverId = settings.getReciverId();
            Vars.key = settings.getKey();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public static void saveSettings(Context context)
    {
        File file = new File(context.getFilesDir(), "settings.bin");

        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        ObjectOutputStream os = null;
        try
        {
            os = new ObjectOutputStream(fos);
            Settings settings = new Settings();
            settings.setSenderId( Vars.myId );
            settings.setReciverId(Vars.reciverId);
            settings.setKey(Vars.key);
            os.writeObject(settings);
            os.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
