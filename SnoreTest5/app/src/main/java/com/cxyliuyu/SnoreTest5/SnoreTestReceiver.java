package com.cxyliuyu.SnoreTest5;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioRecord;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SnoreTestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("cxyliuyu.snore","接收到发送的广播了");
        //throw new UnsupportedOperationException("Not yet implemented");

        SnoreSharedPreferences ssp = new SnoreSharedPreferences();
        SnoreTestAlarmManager sManager = new SnoreTestAlarmManager();
        int times = ssp.getTimes(context);
        //判断录音次数是否达到十次
        if(times<10){
            RecordThread rt = new RecordThread(context);
            Thread thread = new Thread(rt);
            //thread.start();
            ssp.addOne(context);

            Log.i("cxyliuyu","只是第"+times+"次录音");
        }else{
            //达到的话，取消闹钟事件
            sManager.removeSnoreTestAlarmManager(context);
            //Log.i("cxyliuyu",ssp.getTimes(context)+"");
            //将裸音频文件转化为可以播放的wav文件

        }

    }
    public class RecordThread implements Runnable{
        Context context = null;
        public RecordThread(Context context){
            this.context = context;
        }
        @Override
        public void run() {
            AudioRecordUtils audioRecordUtils = new AudioRecordUtils(context);
            audioRecordUtils.startRecord();
            try {
                Thread.sleep(5000);   //设置录音时间为30秒
                audioRecordUtils.stopRecord();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
