package com.dingding.sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private TextView textView;
    String KEY = "MyValue";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        preferences = getPreferences(Activity.MODE_PRIVATE);
        editor = preferences.edit();

        //读取数据
        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = preferences.getString(KEY, "当前数值不存在");//若键KEY没有值存在，则显示提供的值
                textView.setText(in);
                Toast.makeText(getApplicationContext(), in, Toast.LENGTH_SHORT).show();
            }
        });

        //写入数据
        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将值放入editor中
                editor.putString(KEY, et.getText().toString());

                //将editor值放入首选项中
                if (editor.commit()) {
                    Toast.makeText(getApplicationContext(), "写入成功", Toast.LENGTH_SHORT).show();

                }

                //清除数据
//                editor.clear();
//                editor.commit();
                //移除键对应的值
//                editor.remove(KEY);
//                editor.commit();
            }
        });
    }
}
