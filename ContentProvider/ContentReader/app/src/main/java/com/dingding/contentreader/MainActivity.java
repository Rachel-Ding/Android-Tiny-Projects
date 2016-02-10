package com.dingding.contentreader;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Uri URI = Uri.parse("content://com.dingding.cp");//与ContentWriter中的地址要一样
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //插入
        Cursor cursor = getContentResolver().query(URI, null, null, null, null);
        cursor.moveToFirst();//将指针移至第一个

        for (int i=0;i<cursor.getCount();i++) {
            String value = cursor.getString(cursor.getColumnIndex("name"));
            Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
            cursor.moveToNext();//将指针移至下一个
        }
    }
}
