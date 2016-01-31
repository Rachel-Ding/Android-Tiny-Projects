package com.dingding.playsoundandlrc;

/*
 * 1.同步播放歌词
 *  On 2016-1-28
 */

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private TextView textView;
    private String nowText = "";
    private ArrayList<LrcContent> lrcContentList;//存放歌词列表
    private int index = 0; //lrc检索值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//初始化

        getLrc();//读取歌词文件

        new Thread(new runable()).start();//新线程处理歌词同步
    }

    /*初始化*/
    public void init() {
        lrcContentList = new ArrayList<LrcContent>();
        textView = (TextView) findViewById(R.id.text);

        mediaPlayer = MediaPlayer.create(this, R.raw.nianlun);
        mediaPlayer.start();//播放音乐
    }

    /*读取歌词文件*/
    public void getLrc() {
        try {

            InputStreamReader inputReader = new InputStreamReader(getResources().openRawResource(R.raw.nianlun_lrc));

            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";

            while ((line = bufReader.readLine()) != null) {
                line = line.replace("[", "");
                String[] splitLine = line.split("\\]");//分隔符，此处以"]"为分隔
                if (splitLine.length > 1) {
                    LrcContent lrcContent = new LrcContent();
                    //取得该行的时间
                    int beginTime = getLrcTime(splitLine[0]);
                    lrcContent.setLrcTime(beginTime);
                    lrcContent.setLrcStr(splitLine[1]);
                    //添加到列表当中
                    lrcContentList.add(lrcContent);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*获取Lrc文件中每行的时间值*/
    public int getLrcTime(String strTime) {
        //将"."替换为":"
        strTime = strTime.replace(".", ":");
        //以":"为分隔符
        String[] timeData = strTime.split("\\:");
//        String[] timeData = strTime.split("@");
        //获取分、秒、毫秒
        int minute = Integer.parseInt(timeData[0]);
        int second = Integer.parseInt(timeData[1]);
        int mSecond = Integer.parseInt(timeData[2]);
        //当前时间
        int nowTime = minute * 60 * 1000 + second * 1000 + mSecond * 10;
        return nowTime;
    }

    /*另起线程来根据音乐播放时间来同步歌词*/
    private class runable implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (mediaPlayer.isPlaying()) {
                    nowText = lrcContentList.get(lrcIndex()).getLrcStr();
                    //使用handler来更新UI
                    handler.post(updateTextview);
                }
            }
        }
    }

    /*根据时间获取歌词显示的索引值*/
    public int lrcIndex() {

        //音乐目前的播放时间和总长
        int currentTime = mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();

        if (currentTime < duration) {
            for (int i = 0; i < lrcContentList.size(); i++) {
                if (i < lrcContentList.size() - 1) {
                    //当开始显示第一句
                    if (currentTime < lrcContentList.get(i).getLrcTime() && i == 0) {
                        index = i;
                    }
                    if (currentTime > lrcContentList.get(i).getLrcTime()
                            && currentTime < lrcContentList.get(i + 1).getLrcTime()) {
                        index = i;
                    }
                }
                //当显示最后一句
                if (i == lrcContentList.size() - 1 && currentTime > lrcContentList.get(i).getLrcTime()) {
                    index = i;
                }
            }
        }
        return index;
    }

    /*使用handler来更新UI界面*/
    Handler handler = new Handler();

    Runnable updateTextview = new Runnable() {
        @Override
        public void run() {
            textView.setText(nowText);
        }
    };
}
