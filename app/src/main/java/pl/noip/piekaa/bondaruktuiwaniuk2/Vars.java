package pl.noip.piekaa.bondaruktuiwaniuk2;

import android.app.Activity;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by piekaa on 2017-06-21.
 */

public class Vars
{
    public static Long myId = 01L;
    public static Long reciverId = 01L;
    public static String key = "SierotkaMaRysia";
    public static Long oldestTimestamp = System.currentTimeMillis();
    public static Queue<Activity> activityQueue = new LinkedList<>();
    public static boolean isActive = false;
}
