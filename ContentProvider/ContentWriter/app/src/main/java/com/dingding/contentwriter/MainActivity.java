package com.dingding.contentwriter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write();
            }
        });
    }

    public void write() {
        ContentValues values = new ContentValues();
        values.put("name","Java");
        values.put("name","Swift");
        values.put("name","Python");
        values.put("name","C#");

        //传递到数据库中
        getContentResolver().insert(MyProvider.URI,values);
    }
}
