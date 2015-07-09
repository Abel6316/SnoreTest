package com.cxyliuyu.SnoreTest5;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by liuyu on 2015/6/1.
 * 用于设置一个定时启动的闹钟
 */
public class SnoreTestAlarmManager {

    public final int PENDINGINTENTID = 1020093037;

    public void addSnoreTestAlarmManager(Context context){
        //Log.i("com.cxyliuyu.snoretest","设置闹钟前");
        Intent intent = new Intent();
        intent.setAction("com.cxyliuyu.snoretest.action");
        PendingIntent pi = PendingIntent.getBroadcast(context,PENDINGINTENTID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(0,System.currentTimeMillis(),1000,pi);//设置程序的唤醒周期为半个小时
        SnoreSharedPreferences ssp = new SnoreSharedPreferences();
        ssp.setNew(context);        //并初始化录音次数
        //Log.i("com.cxyliuyu.snoretest","设置闹钟后");

    }
    public void removeSnoreTestAlarmManager(Context context){
        Intent intent = new Intent();
        intent.setAction("com.cxyliuyu.snoretest.action");
        PendingIntent pi = PendingIntent.getBroadcast(context,PENDINGINTENTID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);
    }
}
