package com.dingding.exampleapplication;

/*
* 作业3. 编写一个应用要求特定权限才能启动
*
*  On 2015-12-26
*/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
