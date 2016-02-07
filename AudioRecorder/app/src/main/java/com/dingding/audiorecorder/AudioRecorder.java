package com.dingding.audiorecorder;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**录制音频类
 * Created by Dingding on 2016/1/30.
 */
public class AudioRecorder {
    private String dir;//路径
    private String fileName;
    private MediaRecorder mediaRecorder;

//--------------- 1 -----------------------------------------
    public AudioRecorder() {
        //目录地址
        dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myrecorder/";
    }

//--------------- 2 -----------------------------------------
    /*开启录制*/
    public void start() throws IOException {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("没有可用的存储空间");
        }

        File myDir = new File(dir);
        if (!myDir.exists()) {
            myDir.mkdir();
        }

        //文件的的名字=目录名+系统时间+音频格式
        fileName = dir + System.currentTimeMillis() + ".amr";

        //实例化
        mediaRecorder = new MediaRecorder();

//        设置参数
        //录音音频来源
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //输出格式设置
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        //编码
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //采样率
        mediaRecorder.setAudioSamplingRate(8000);
        //音频的名字设置到输出文件中
        mediaRecorder.setOutputFile(fileName);

        mediaRecorder.prepare();
        mediaRecorder.start();//开启录制
    }

//--------------- 3 ---------------------------------------
    /*停止录制*/
    public void stop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();//停止
            mediaRecorder.release();//释放
            mediaRecorder = null;//置空
        }
    }

    public String getPath(){
        return fileName;
    }
}
