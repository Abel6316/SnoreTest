package com.cxyliuyu.SnoreTest5;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by liuyu on 2015/6/3.
 */
public class SnoreSharedPreferences {
    public void setNew(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("recordTimes",context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("times",0);
        editor.commit();
        Log.i("com.cxyliuyu.snoretest","程序重新设置了录音次数");
    }
    public void addOne(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("recordTimes",context.MODE_PRIVATE);
        int times = sharedPreferences.getInt("times",-1);
        if(times<10){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("times",times+1);
            editor.commit();
        }
        Log.i("com.cxyliuyu.snoretest","录音次数+1，现在的次数是"+times);
    }
    public int getTimes(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("recordTimes",context.MODE_PRIVATE);
        int times = sharedPreferences.getInt("times",-1);
        return times;
    }
}
