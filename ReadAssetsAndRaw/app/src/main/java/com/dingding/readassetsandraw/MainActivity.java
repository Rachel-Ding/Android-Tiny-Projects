package com.dingding.readassetsandraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private static final String TAGA = "ReadAssets";
    private static final String TAGR = "ReadRaw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assets数据读取
        findViewById(R.id.btn_readassets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //打开assets中文件
                    InputStream inputStream = getResources().getAssets().open("info.txt");
                    //字节流转换为字符流，( ,指定编码)
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

//                    Log.i(TAG, bufferedReader.readLine());
                    String in = "";
                    //逐行读取数据
                    while ((in = bufferedReader.readLine()) != null) {
                        Log.i(TAGA, in);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //raw数据读取
        findViewById(R.id.btn_readraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputStream is = getResources().openRawResource(R.raw.info);
                    InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                    BufferedReader br = new BufferedReader(isr);

                    String in = "";
                    while ((in = br.readLine()) != null){
                        Log.i(TAGR, in);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
