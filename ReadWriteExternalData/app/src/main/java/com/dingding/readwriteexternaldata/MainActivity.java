package com.dingding.readwriteexternaldata;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private TextView show;
    File sdcard = Environment.getExternalStorageDirectory();//外部存储sd卡的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.et);
        show = (TextView) findViewById(R.id.show);

        //写入数据
        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据存储文件
                File myfile = new File(sdcard, "this my file.txt");

                //判断sd卡是否存在
                if (!sdcard.exists()) {
                    Toast.makeText(getApplicationContext(), "当前系统不具备sd卡目录", Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.println(sdcard.getAbsolutePath());

                try {
                    //创建文件
                    myfile.createNewFile();
                    Toast.makeText(getApplicationContext(), "文件创建完成", Toast.LENGTH_SHORT).show();
                    //写数据到文件,将数据转换成字节并将其保存到文件
                    FileOutputStream fos = new FileOutputStream(myfile);
                    //字节流转为字符流
                    OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
                    //写入
                    osw.write(et.getText().toString());
                    //刷新
                    osw.flush();
                    fos.flush();
                    //关闭
                    osw.close();
                    fos.close();
                    Toast.makeText(getApplicationContext(), "文件写入完成", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //读取数据
        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据存储文件
                File myfile = new File(sdcard, "this my file.txt");
                if (myfile.exists()) {
                    try {
                        FileInputStream fis = new FileInputStream(myfile);
                        InputStreamReader isr = new InputStreamReader(fis,"UTF-8");

                        char input[] = new char[fis.available()];
                        //读取
                        isr.read(input);
                        //关闭
                        isr.close();
                        fis.close();

                        String str = new String(input);
                        //显示
                        show.setText(str);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
