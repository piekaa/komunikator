package pl.noip.piekaa.bondaruktuiwaniuk2.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by piekaa on 2017-06-22.
 */

public class DateTest
{

    @Test
    public void showDate()
    {
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");

        System.out.println(dateFormat.format(date));




    }


}
