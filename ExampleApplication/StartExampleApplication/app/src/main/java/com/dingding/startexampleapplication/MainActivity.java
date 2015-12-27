package com.dingding.startexampleapplication;

/*
* 作业3. 编写一个应用要求特定权限才能启动
*
*  On 2015-12-26
*/

import android.content.Intent;
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
                try {
                    //启动ExampleApplication
                    startActivity(new Intent("com.dingding.exampleapplication.Intent.MainActivity"));
                } catch (Exception e){
                    //捕获异常
                    Toast.makeText(MainActivity.this,"启动ExampleApplication失败",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
