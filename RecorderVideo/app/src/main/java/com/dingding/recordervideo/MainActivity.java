package com.dingding.recordervideo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_recorder;
    private Button btn_play;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_recorder = (Button) findViewById(R.id.btn_recorder);
        btn_play = (Button) findViewById(R.id.btn_play);
        videoView = (VideoView) findViewById(R.id.videoView);

        btn_recorder.setOnClickListener(this);
        btn_play.setOnClickListener(this);
    }

    private final int VIDEO_RECORDER = 1;
    private Uri outputUri;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_recorder:
//------------------ 1 ----------------------------------------------
//                //调用系统录制视频程序
//                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                startActivityForResult(intent,VIDEO_RECORDER);

//------------------ 2 ----------------------------------------------
                //自定义录制的路径
                File file = new File(Environment.getExternalStorageDirectory(),"myRecorder.mp4");
                outputUri = Uri.fromFile(file);

                //调用系统录制视频程序
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,outputUri);
                startActivityForResult(intent, VIDEO_RECORDER);

                break;
            case R.id.btn_play:

                videoView.setVideoURI(outputUri);
                videoView.start();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
//------------------ 1 ----------------------------------------------
//            outputUri = data.getData();//获取到录制视频后的uri

        }
    }
}
