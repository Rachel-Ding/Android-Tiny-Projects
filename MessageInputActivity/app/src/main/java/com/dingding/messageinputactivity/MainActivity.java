package com.dingding.messageinputactivity;

/*
 *  任务五
 *  1.启动一个Activity用于输入信息
 *      启动一个Activity用于输入信息并获取其返回值和返回状态
 *    On 2015-12-7
 */
//---------------修改版-----------------

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnActivity;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        textView = (TextView) findViewById(R.id.textView);
        btnActivity = (Button) findViewById(R.id.btnActivity);

        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MessageInputActivity.class);
                startActivityForResult(intent, 1); //代替startActivity
            }
        });

    }

    //获取传输的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent i) {

        if(requestCode==1) {
            switch (resultCode) {
                case 2:
                    textView.setText(i.getStringExtra("data"));
                    break;
                case 3:
                    textView.setText("");
                    Toast.makeText(this,"您取消了许愿操作",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
