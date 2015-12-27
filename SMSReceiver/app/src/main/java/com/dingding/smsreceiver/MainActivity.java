package com.dingding.smsreceiver;

/*
* 垃圾短信拦截工具(模拟器版本为Android 4.3及以下)，要求如下:
* a)编辑关键字
* b)通过关键字屏蔽短信
* c)编辑屏蔽的号码
* d)通过号码屏蔽短信
* e)将屏蔽的短信保存
*
*    On 2015-12-21
*/
//-----------------修改版-二--------------------------------

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int ONE_ID = Menu.FIRST + 1;
    private ListView listViewSMS;
    private SimpleCursorAdapter adapter;
    private Db db;
    private SQLiteDatabase dbRead, dbWrite;
    SMSReceiver smsReceiver;
    Context context;
    private ArrayList<String> listAddress, listBody;
    private boolean listBodyMatch = false;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        adapter = new SimpleCursorAdapter(this, R.layout.list_cell_sms, null, new String[]{"address", "body", "data"},
                new int[]{R.id.tvAddress, R.id.tvBody, R.id.tvDate});
        listViewSMS.setAdapter(adapter);

        /*接收广播器传来的数据*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        smsReceiver = new SMSReceiver();
        registerReceiver(smsReceiver, intentFilter);
        refreshListView();

        /*长按删除*/
        listViewSMS.setOnItemLongClickListener(onItemLongClickListener);
    }

    /*初始化*/
    private void init() {
        listViewSMS = (ListView) findViewById(R.id.lvSMS);
        db = new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();
        context = MainActivity.this;
    }

    private void refreshListView() {
        c = dbRead.query("user", null, null, null, null, null, null);
        adapter.changeCursor(c);
//        c.close();
    }

    /*写入数据库*/
    private void DBInsert(String fromAddress, String messageDate, String messageBody) {
        ContentValues cv = new ContentValues();
        cv.put("address", fromAddress);
        cv.put("body", messageBody);
        cv.put("data", messageDate);
        dbWrite.insert("user", null, cv);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, ONE_ID, Menu.NONE, "设置");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { //获取Id
            case ONE_ID:
                Intent intent = new Intent(MainActivity.this, ChooseTypeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*广播器*/
    private class SMSReceiver extends BroadcastReceiver {

        final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_SMS_RECEIVED.equals(intent.getAction())) {


                Bundle extras = intent.getExtras();
                if (extras == null)
                    return;
                //获取短信内容
                Object[] pdus = (Object[]) extras.get("pdus");

                listAddress = new ArrayList<>();
                listBody = new ArrayList<>();
                loadAddress(listAddress);
                loadBody(listBody);

//                System.out.println(listAddress);

                for (int i = 0; i < pdus.length; i++) {
                    // 获取单条短信内容，以pdu格式存，并生成短信对象
                    SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String fromAddress = message.getOriginatingAddress(); //发送号码
                    String messageBody = message.getMessageBody();//短信内容

                    SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
                    Date sendDate = new Date(message.getTimestampMillis());
                    String messageDate = dataFormat.format(sendDate);//短信日期

                    for (i = 0; i < listBody.size(); ++i) {
                        if (messageBody.contains(listBody.get(i))) {
                            listBodyMatch = true;
                            break;
                        } else listBodyMatch = false;
                    }

                    if (listAddress.contains(fromAddress) || listBodyMatch) {

                        DBInsert(fromAddress, messageDate, messageBody);//加入数据库
                        refreshListView();
                        this.abortBroadcast();
                    } else
                        return;

//                    System.out.format("发送者：%s，信息内容：%s\n", fromAddress, messageBody);
                }
            }
        }
    }


    /*取出联系人数组list*/
    public void loadAddress(ArrayList<String> list) {
        SharedPreferences sp = this.getSharedPreferences("listAddress", MODE_WORLD_READABLE);
        list.clear();
        int size = sp.getInt("list_size", 0);

        for (int i = 0; i < size; ++i) {
            list.add(sp.getString("list_" + i, null));
        }
    }

    /*取出关键字数组list*/
    public void loadBody(ArrayList<String> list) {
        SharedPreferences sp = this.getSharedPreferences("listBody", MODE_WORLD_READABLE);
        list.clear();
        int size = sp.getInt("list_size", 0);

        for (int i = 0; i < size; ++i) {
            list.add(sp.getString("list_" + i, null));
        }
    }

    /*长按删除*/
    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(context);
            deleteBuilder.setTitle("提醒");
            deleteBuilder.setMessage("你确定要删除这一项吗？");
            deleteBuilder.setNegativeButton("取消", null);
            deleteBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Cursor c = adapter.getCursor();
                    c.moveToPosition(position);

                    int itemId = c.getInt(c.getColumnIndex("_id")); //列的Id
                    dbWrite.delete("user", "_id=?", new String[]{itemId + ""}); //删除
                    refreshListView();
                }
            });

            deleteBuilder.show();
            return true;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
        if (c != null)
            c.close();
        if (db != null)
            db.close();
    }

}

