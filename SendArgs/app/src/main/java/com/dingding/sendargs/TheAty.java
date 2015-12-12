package com.dingding.sendargs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TheAty extends Activity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_aty);

        Intent i = getIntent();

        tv = (TextView) findViewById(R.id.tv);

        tv.setText(i.getStringExtra("data"));//获得传递的字符串
    }

}
