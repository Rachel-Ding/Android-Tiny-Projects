package com.dingding.readwriteinternaldata;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private String filename = "text";
    private EditText et;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= (EditText) findViewById(R.id.et);
        show = (TextView) findViewById(R.id.show);

        //写入文件操作
        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //文件输出流
                    FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    //转换为字符输入格式（字节流包装成字符流）
                    OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
                    //写入
                    osw.write(et.getText().toString());
                    //刷新，确保都存入
                    osw.flush();
                    fos.flush();
                    //关闭
                    osw.close();
                    fos.close();
                    Toast.makeText(getApplicationContext(),"写入完成",Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //读取文件操作
        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = openFileInput(filename);
                    InputStreamReader isr = new InputStreamReader(fis,"UTF-8");

                    //文件的可用长度：fis.available()
                    char input[]= new char[fis.available()];
                    //读取
                    isr.read(input);
                    //关闭
                    isr.close();
                    fis.close();
                    //显示
                    String readStr = new String(input);
                    show.setText(readStr);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
