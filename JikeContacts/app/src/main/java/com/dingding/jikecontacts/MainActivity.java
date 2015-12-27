package com.dingding.jikecontacts;

/*    注：测试模拟器和手机需sdk23以下（不包含23）
*       1.实现简单通讯录，可以读取显示系统联系人，可以添加联系人到系统通讯录
*       2.可以启动发信息和打电话
*       3.实现多语言显示
*
*     版本：API 21
*       On 2016-12-13
*/

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private View addLayout;
    private EditText etName=null, etTel=null;
    private Button btnAdd;
    //    private Db db;
//    private SQLiteDatabase dbRead, dbWrite;
    private SimpleAdapter adapter;
    private List<HashMap<String, Object>> data;
//    private SimpleCursorAdapter


    Context mContext = null;
    //获取Phone表的 { 联系人姓名、电话号码、联系人ID }
    private static final String[] PHONES_PROJECTION = new String[]{
            Phone.DISPLAY_NAME, Phone.NUMBER, Phone.CONTACT_ID};
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;//联系人姓名
    private static final int PHONES_NUMBER_INDEX = 1;//电话号码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//初始化


        //适配器
//        adapter = new SimpleCursorAdapter(this, R.layout.user_list_cell, null, new String[]{"name", "tel"},
//                new int[]{R.id.tvName, R.id.tvTel});

//        data = new ArrayList<HashMap<String,Object>>();
        data = new ArrayList<>();

        adapter = new SimpleAdapter(this, data, R.layout.user_list_cell, new String[]{"name", "tel"},
                new int[]{R.id.tvName, R.id.tvTel});

        listView.setAdapter(adapter);

        getPhoneContacts();//获取手机通讯录的联系人信息

//        refreshListView();//更新listView

        //添加按钮
        btnAdd.setOnClickListener(btnAddListener);

        //长按删除
        listView.setOnItemLongClickListener(listViewItemLongClickListener);

        //拨打电话、发短信等
        listView.setOnItemClickListener(listViewItemClickListener);

    }

    //初始化
    public void init() {
        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
//        db = new Db(this);
//        dbRead = db.getReadableDatabase();
//        dbWrite = db.getWritableDatabase();

        mContext = this;
    }

    //更新listView
//    private void refreshListView() {
////        Cursor c = dbRead.query("user", null, null, null, null, null, null);
////        adapter.changeCursor(c);
//        adapter.notifyDataChanged(data);
//
//    }

    //按钮监听器，添加联系人
    protected View.OnClickListener btnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Dialog输入框
            addLayout = getLayoutInflater().inflate(R.layout.add_dialog, null);
            etName = (EditText) addLayout.findViewById(R.id.etName);
            etTel = (EditText) addLayout.findViewById(R.id.etTel);

            AlertDialog.Builder addBuilder = new AlertDialog.Builder(MainActivity.this);
            addBuilder.setTitle(getString(R.string.addContact));
            addBuilder.setView(addLayout);
            addBuilder.setNegativeButton(getString(R.string.cancel), null);
            addBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

//                    ContentValues cv = new ContentValues();
//                    cv.put("name", etName.getText().toString());
//                    cv.put("tel", etTel.getText().toString());
////                    dbWrite.insert("user", null, cv);
////                    refreshListView();
//                    listView.setAdapter();
                    //先进行空字符校验
                    if (!TextUtils.isEmpty(etName.getText())&& !TextUtils.isEmpty(etTel.getText())) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", etName.getText().toString());
                        map.put("tel", etTel.getText().toString());
                        data.add(map);
//                    adapter.notifyDataChanged(data);
                        adapter.notifyDataSetChanged();
                        insertPhoneContacts(mContext, etName.getText().toString(), etTel.getText().toString());

                    }

                }

            });

            addBuilder.show();

        }
    };

    //监听器，长按删除
    private AdapterView.OnItemLongClickListener listViewItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            //这里position定义为final常量

            //确认取消对话框
            new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.remind)).setMessage(getString(R.string.ensure_delete)).
                    setNegativeButton(getString(R.string.cancel), null).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Cursor c = adapter.getCursor();
//                    c.moveToPosition(position);
//
//                    int itemId = c.getInt(c.getColumnIndex("_id")); //列的Id
//                    dbWrite.delete("user", "_id=?", new String[]{itemId + ""}); //删除
//                    refreshListView();

                    data.remove(position);
                    adapter.notifyDataSetChanged();

                }
            }).show();

            return true; //长按是否成功（系统会有反馈，振动等）
        }
    };

    //监听器，拨打电话、发短信等
    private AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            //这里position定义为final常量
            
            CharSequence[] items = {getString(R.string.phone_call), getString(R.string.send_sms)};

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.choose));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //获取当前位置的电话号码
//                    Cursor c = adapter.getCursor();
//                    c.moveToPosition(position);
                    HashMap<String, Object> map = (HashMap<String, Object>) adapter.getItem(position);


//                    String tel = c.getString(c.getColumnIndex("tel"));
                    String tel = (String) map.get("tel");
                    if (0 == which) {

                        //拨打电话
                        Intent telIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));

                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(telIntent);

                    } else {
                        //发短信
                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));

                        startActivity(smsIntent);
                    }
                }
            });

            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.show();
        }
    };

    //获取手机通讯录的联系人信息
    private void getPhoneContacts() {
        ContentResolver resolver = mContext.getContentResolver();

        //获取手机联系人（CONTENT_URI对应contacts表 和   raw_contacts表 以及 data表，）
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                //添加到数据库
//                ContentValues cv = new ContentValues();
//                cv.put("name", contactName);
//                cv.put("likestel", phoneNumber); //重复的号码不添加
//                dbWrite.insert("user", null, cv);
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", contactName);
                map.put("tel", phoneNumber);
                data.add(map);
                adapter.notifyDataSetChanged();

            }

            phoneCursor.close();
        }
    }

    //向通讯录中添加联系人
    private void insertPhoneContacts(Context mContext, String name, String tel) {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentValues values = new ContentValues();
        // 向raw_contacts插入一条除了ID之外, 其他全部为NULL的记录, ID是自动生成的
        long id = ContentUris.parseId(resolver.insert(uri, values));
        // 添加联系人姓名
        uri = Uri.parse("content://com.android.contacts/data");
        values.put("raw_contact_id", id);
        values.put("data2", name);
        values.put("mimetype", "vnd.android.cursor.item/name");
        resolver.insert(uri, values);
        // 添加联系人电话
        values.clear(); // 清空上次的数据
        values.put("raw_contact_id", id);
        values.put("data1", tel);
        values.put("data2", "2");
        values.put("mimetype", "vnd.android.cursor.item/phone_v2");
        resolver.insert(uri, values);
    }

}
