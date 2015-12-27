package com.dingding.simpleimagebrowser;

/*
* 作业2. 编写一个图片浏览器
*
*  On 2015-12-26
*/

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    //得到外部存储卡的路径
    static File sdCard= Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //将图片命名并确定存储位置
                File file = new File (sdCard.getAbsolutePath(),"picture1.ipg");
                //从资源文件中选择一张图片作为将要写入的源文件
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.picture1);
                try {
                    FileOutputStream out=new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //隐式Intent
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                startActivity(intent);

            }
        });

    }
}
