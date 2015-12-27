package com.dingding.smsreceiver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {
    /*构造方法*/
    public Db(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行一个SQL语句，创建用户表
        db.execSQL("CREATE TABLE user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "address TEXT DEFAULT \"\"," +
                "body TEXT DEFAULT \"\"," +
                "data TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
