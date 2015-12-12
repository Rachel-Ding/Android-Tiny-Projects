package com.dingding.sendargs;

/*
 *  1.将第一个Activity中输入的信息传给第二个Activity
 *      On 2015-11-25
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView textView; //提示语句
    private EditText editText; //输入框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);

        //Button启用
        findViewById(R.id.btnStartAty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //使用intent来传递信息
                Intent i = new Intent(MainActivity.this,TheAty.class);
                i.putExtra("data", editText.getText().toString()); //将输入信息转换为字符串
                startActivity(i);
            }

        });


    }


}
