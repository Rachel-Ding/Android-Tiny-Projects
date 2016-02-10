package com.dingding.contentwriter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Dingding on 2015/12/11.
 */
//为外部应用提供数据
public class MyProvider extends ContentProvider{
    public static final Uri URI = Uri.parse("content://com.dingding.cp");//AndroidManifest.xml中Provider的地址

    SQLiteDatabase database ;

    @Override
    public boolean onCreate() {
        //设置数据库
        database = getContext().openOrCreateDatabase("mycp.db3", Context.MODE_PRIVATE,null);
        //注：（文件名，只能被当前类调用，...）

        // 为数据库创建一个数据表
        database.execSQL("create table tab( id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL)");
        //注：（tab（id 自增变量，name 是text型非空的）


        return true;
    }

    //查询
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor =database.query("tab",null,null,null,null,null,null);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //插入
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {//插入的是ContentValues
        database.insert("tab","_id",values);
//        database.close();
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
