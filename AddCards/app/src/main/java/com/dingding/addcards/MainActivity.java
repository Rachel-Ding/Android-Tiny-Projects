package com.dingding.addcards;

/*
*  作业2.用代码分4列5行添加20个数字
*  On 2016-1-4
* */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CardsView(this));
    }
}
