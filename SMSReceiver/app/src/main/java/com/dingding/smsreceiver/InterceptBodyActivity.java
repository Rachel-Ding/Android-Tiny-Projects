package com.dingding.smsreceiver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class InterceptBodyActivity extends AppCompatActivity {
    private ListView listViewBody;
    private Button btnAdd;
    private ArrayAdapter<String> arrayAdapter;
    private EditText etBody;
    private ArrayList<String> list;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intercept_body);

        init();

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, R.id.tvIntercept, list);
        listViewBody.setAdapter(arrayAdapter);

        //添加屏蔽号码按钮
        btnAdd.setOnClickListener(btnAddListener);
        //长按删除
        listViewBody.setOnItemLongClickListener(onItemLongClickListener);
    }

    /*初始化*/
    private void init() {
        listViewBody = (ListView) findViewById(R.id.lvBody);
        btnAdd = (Button) findViewById(R.id.btnAddBody);
        context = InterceptBodyActivity.this;
        list = new ArrayList<String>();
        loadArray(list);
    }

    /*添加按钮*/
    protected View.OnClickListener btnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //号码输入编辑
            View addLayout = getLayoutInflater().inflate(R.layout.list_cell_body, null);
            etBody = (EditText) addLayout.findViewById(R.id.etBody);

            //Dialog弹窗
            AlertDialog.Builder addBuilder = new AlertDialog.Builder(context);
            addBuilder.setTitle("请输入内容");
            addBuilder.setView(addLayout);
            addBuilder.setNegativeButton("取消", null);
            addBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strBody = etBody.getText().toString().trim();//去除字符串前后空格

                    //判断字符串非空以及之前是否输入过
                    if ((!strBody.equals("")) && (!list.contains(strBody))) {
                        list.add(strBody);
                        saveArray(list);
                        arrayAdapter.notifyDataSetChanged();

                    } else if (strBody.equals("")) {
                        Toast.makeText(context, "输入为空!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "重复输入!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            addBuilder.show();
        }
    };

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

                    arrayAdapter.remove(arrayAdapter.getItem(position));
                    arrayAdapter.notifyDataSetChanged();

                    saveArray(list);
                }
            });
            deleteBuilder.show();
            return true;
        }
    };


    /*使用SharedPreferences存储联系人数组list*/
    public boolean saveArray(ArrayList<String> list) {
        SharedPreferences sp = this.getSharedPreferences("listBody", MODE_APPEND);
        SharedPreferences.Editor et = sp.edit();
        et.putInt("list_size", list.size());

        for (int i = 0; i < list.size(); ++i) {
            et.remove("list_" + i);
            et.putString("list_" + i, list.get(i));
        }
        return et.commit();
    }

    /*取出联系人数组list*/
    public void loadArray(ArrayList<String> list) {
        SharedPreferences sp = this.getSharedPreferences("listBody", MODE_WORLD_READABLE);
        list.clear();
        int size = sp.getInt("list_size", 0);

        for (int i = 0; i < size; ++i) {
            list.add(sp.getString("list_" + i, null));
        }
    }
}
