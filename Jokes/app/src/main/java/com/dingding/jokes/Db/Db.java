package com.dingding.jokes.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dingding on 2016/2/21.
 */
public class Db extends SQLiteOpenHelper {

    public Db(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
//        db.execSQL("CREATE TABLE user(" +
//                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "pages INTEGER DEFAULT ," +
//                "ID INTEGER DEFAULT ," +
//                "date TEXT DEFAULT \"\"," +
//                "modified TEXT DEFAULT \"\"," +
//                "title TEXT DEFAULT \"\"," +
//                "content TEXT DEFAULT \"\")");

        db.execSQL("CREATE TABLE user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pages INTEGER, " +
                "ID INTEGER, " +
                "date TEXT," +
                "modified TEXT," +
                "title TEXT," +
                "content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
