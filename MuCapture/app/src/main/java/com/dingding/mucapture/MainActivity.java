package com.dingding.mucapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_capture;
    private ImageView img_capture;
    private Uri outputUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_capture = (Button) findViewById(R.id.btn_capture);
        img_capture = (ImageView) findViewById(R.id.img_capture);

        btn_capture.setOnClickListener(this);
    }

    private final int TAKE_CAPTURE = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //获取系统拍照功能
            case R.id.btn_capture:
//------------------ 1 ----------------------------------------------
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, TAKE_CAPTURE);

//------------------ 2 ----------------------------------------------
                //自定义拍照路径
                File file = new File(Environment.getExternalStorageDirectory(), "my.png");
                outputUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                startActivityForResult(intent, TAKE_CAPTURE);
                break;
        }
    }

    /*
     * 获取照片
     * 覆写startActivityForResult中的方法*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_CAPTURE) {
//------------------ 1 ----------------------------------------------
                //解析照片
                if (data != null) {
                    //解析数据
                    if (data.hasExtra("data")) {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        img_capture.setImageBitmap(bitmap);
                    }
                }
//------------------ 2 ----------------------------------------------
                //读取图片，并进行缩放
                else {
                    int width = img_capture.getWidth();
                    int height = img_capture.getHeight();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(outputUri.getPath(), options);

                    //图片真正的宽高
                    int imgWidth = options.outWidth;
                    int imgHeight = options.outHeight;
                    //缩放系数
                    int scale = Math.min(imgWidth / width, imgHeight / height);
                    scale = scale == 0 ? 1 : scale;

                    options.inJustDecodeBounds = false;
                    options.inSampleSize = scale;

                    Bitmap bitmap = BitmapFactory.decodeFile(outputUri.getPath(), options);
                    img_capture.setImageBitmap(bitmap);
                }
            }
        }
    }
}
