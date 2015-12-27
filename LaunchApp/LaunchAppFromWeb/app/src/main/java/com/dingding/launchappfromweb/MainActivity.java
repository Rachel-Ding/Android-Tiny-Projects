package com.dingding.launchappfromweb;

/*
* 作业1. 通过浏览器页面打开本机应用
*
*  On 2015-12-25
*/

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //隐式Intent启动activity
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("local://example.app")));
                }catch(Exception e){
                    Toast.makeText(MainActivity.this,"无法启动指定的Activity",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
