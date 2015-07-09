package com.cxyliuyu.SnoreTest5;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liuyu on 2015/5/17.
 */
public class AudioRecordUtils {

    Context context = null;
    //音频获取源
    private int audioSource = MediaRecorder.AudioSource.MIC;
    //设置音频采样率，44100是目前标准，弹某些设备仍然支持22050,1600,11025
    private static int sampleRateInHz = 22050;
    //设置音频的录制声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
    private static int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
    //音频数据格式：PCM16位每个样本。保证设备支持。
    private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    //缓冲区大小
    private int bufferSizeInBytes =0;
    private AudioRecord audioRecord;
    private boolean isRecord = false;   //设置正在录制的状态

    //音频文件保存的位置
    private static final String folder = "/sdcard/SnoreTest";
    //AudioName 裸音频数据文件
    private static final String AudioName ="/sdcard/Snoretest/mySnore.raw";
    //NewAudioName可播放的音频文件
    private static final String NewAudioName = "/sdcard/Snoretest/mySnore.wav";

    public AudioRecordUtils(Context context){
        //Log.i("cxyliuyu","00001");
        this.context = context;
        createAudioRecord();
    }


    private void createAudioRecord(){
        //Log.i("cxyliuyu","00002");
        //获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,channelConfig,audioFormat);
        //创建AudioRecord对象
        audioRecord = new AudioRecord(audioSource,sampleRateInHz,channelConfig,audioFormat,bufferSizeInBytes);
        //Log.i("cxyliuyu","00003");
    }

    public void startRecord(){
        audioRecord.startRecording();
        //让录制状态为true
        isRecord = true;
        //开启音频文件写入线程
        new Thread(new AudioRecordThread()).start();
    }

    public void stopRecord(){
        isRecord = false;
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
    }

    class AudioRecordThread implements Runnable{
        @Override
        public void run() {
            writeDateToFile();
        }
        private void writeDateToFile(){
            //new一个byte数组用来存一些字节数据，大小为缓冲区大小
            byte[] audiodata = new byte[bufferSizeInBytes];
            FileOutputStream fos = null;
            int readsize = 0;
            try{
                File dir = new File("/sdcard/SnoreTest/");
                if(!dir.exists()){
                    dir.mkdir();
                }
                if(dir.exists()&&dir.canWrite()){
                    File file = new File(dir.getAbsolutePath()+"/"+"mySnore.raw");
                    SnoreSharedPreferences ssp = new SnoreSharedPreferences();
                    int times = ssp.getTimes(context);
                    if(file.exists()&&times==0){
                        file.delete();
                    }
                    fos = new FileOutputStream(file,true);//建立一个可存取字节的文件
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            while(isRecord == true){
                readsize = audioRecord.read(audiodata,0,bufferSizeInBytes);
                if(AudioRecord.ERROR_INVALID_OPERATION != readsize){
                    try{
                        fos.write(audiodata);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            try{
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void getWavFile(){

    }
    class ToWavFile implements Runnable{
        @Override
        public void run() {
            int sampleRateInHz = 22050;
            FileInputStream in = null;
            FileOutputStream out = null;
            long totalAudioLen = 0;
            long totalDataLen = totalAudioLen +36;
            long longSampleRate = sampleRateInHz;
            int channels = 2;
            long byteRate = 16*sampleRateInHz*channels/8;
            byte[]
            File infile = new File("/sdcard/SnoreTest/mySnore.raw");
            File outfile = new File("/sdcard/SnoreTest/mySnore.wav");
            try {
                in = new FileInputStream(infile);
                out = new FileOutputStream(outfile);
                totalAudioLen = in.getChannel().size();
                totalDataLen = totalAudioLen + 36;
                while(in.read())
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
