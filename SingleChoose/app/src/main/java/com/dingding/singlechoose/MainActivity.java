package com.dingding.singlechoose;

/*
* 1.做一个单项选择题程序
* On 2015-11-29
*/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnSubmit;
    private RadioButton rbA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化提交按钮和单选正确项
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        rbA = (RadioButton) findViewById(R.id.rbA);

        //监听按钮，并弹出通知Toast
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbA.isChecked()){
                    Toast.makeText(MainActivity.this,"选择正确",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"选择错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
