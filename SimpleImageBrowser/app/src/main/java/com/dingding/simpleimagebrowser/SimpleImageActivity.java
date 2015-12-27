package com.dingding.simpleimagebrowser;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class SimpleImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载图片
        ImageView imageView = new ImageView(this);
        setContentView(imageView);
        try {
            imageView.setImageURI(getIntent().getData());
        }catch (Exception e){
            Toast.makeText(this,"不能打开图片", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
