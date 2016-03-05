package com.dingding.jokes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dingding.jokes.Db.Db;

public class ContentActivity extends AppCompatActivity {
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_content;
    private Db db;
    private SQLiteDatabase dbReader;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        init();

        //通过Intent获取传递过来的数据库"_id"的值
        Intent intent = getIntent();
        int itemId = intent.getIntExtra("db_id", -1);
        if (itemId != -1) {
            //读取"_id"对应的数据
            c = dbReader.query("user", new String[]{"date", "title", "content"}, "_id=?",
                    new String[]{itemId + ""}, null, null, null);

            if (c.moveToFirst()) {
                String title = c.getString(c.getColumnIndex("title"));
                String time = c.getString(c.getColumnIndex("date"));
                String content = c.getString(c.getColumnIndex("content"));

                tv_title.setText(title);
                tv_time.setText("发布时间：" + time);
                tv_content.setText(content);
            }
        }

        if (c != null)
            c.close();
        if (db != null)
            db.close();
    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_content = (TextView) findViewById(R.id.tv_content);
        db = new Db(this);
        dbReader = db.getReadableDatabase();
    }

    /*按返回键后，结束activity*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
