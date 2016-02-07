package com.dingding.audiorecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_recorder;
    private Button btn_stop_recorder;
    private Button btn_play_recorder;

    private AudioRecorder mRecorder;
    private AudioPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_recorder = (Button) findViewById(R.id.btn_recorder);
        btn_stop_recorder = (Button) findViewById(R.id.btn_stop_recorder);
        btn_play_recorder = (Button) findViewById(R.id.btn_play_recorder);

        btn_recorder.setOnClickListener(this);
        btn_stop_recorder.setOnClickListener(this);
        btn_play_recorder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //录制音频
            case R.id.btn_recorder:
                if (mRecorder == null) {
                    mRecorder = new AudioRecorder();
                }
                try {
                    mRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            //停止录音
            case R.id.btn_stop_recorder:
                if (mRecorder != null) {
                    mRecorder.stop();
                }
                break;
            //播放录音
            case R.id.btn_play_recorder:
                if (mPlayer == null) {
                    mPlayer = new AudioPlayer();
                    mPlayer.setPlayerPath(mRecorder.getPath());
                }
                mPlayer.play();
                break;
        }
    }
}
