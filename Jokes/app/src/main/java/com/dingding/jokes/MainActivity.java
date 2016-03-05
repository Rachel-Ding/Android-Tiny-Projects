package com.dingding.jokes;

/*开发一个笑话连连看应用，要求如下：
 *  a)离线时可以查看缓存的数据
 *  b)在线时自动更新并缓存数据
 *  c)列表翻页功能
 *  d)列表下拉刷新功能
 *  e)通信时数据格式为JSON
 *
 *  On 2016-3-3
 *  */
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.dingding.jokes.Db.Db;
import com.dingding.jokes.control.NetworkUtils;
import com.dingding.jokes.control.PullRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private PullRefreshView pullRefreshView;
    private ListView listView;
    private View pullUpFooter;//分页加载提示View
    private SimpleAdapter adapter;
    private List<Map<String, Object>> listMap;
    private Map<String, Object> map;
    private Db db;
    private SQLiteDatabase dbRead, dbWrite;
    private Cursor c;
    StringBuilder builder;
    private long dataCount;//数据库条数
    private NetworkUtils networkUtils;//联网判断

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//初始化

        pullRefreshView.setOnRefreshListener(new PullRefreshView.RefreshListener() {

            @Override
            public void onRefresh() {
                //判断是否联网
                if (networkUtils.isNetworkConnected(getApplicationContext()) || networkUtils.isWifi(getApplicationContext())) {

                    //启用异步线程，来获取网络数据
                    new GetDataTask().execute("http://dingding9.applinzi.com/latestposts.php");
                    try {

                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                pullRefreshView.finishRefresh();
            }
        });


        listView.setOnItemClickListener(listClickListener);
    }

    /*初始化*/
    private void init() {
        pullRefreshView = (PullRefreshView) findViewById(R.id.pull_refresh_view);
        listView = (ListView) findViewById(R.id.listView);
        listMap = new ArrayList<Map<String, Object>>();
        pullUpFooter = MainActivity.this.getLayoutInflater().inflate(R.layout.pull_up_footer, null);
        listView.setOnScrollListener(new scrollListener());
        //初始化数据库
        db = new Db(this);
        dbWrite = db.getWritableDatabase();
        dbRead = db.getReadableDatabase();
        //获取数据库条数
        dataCount = getCount();
        //获取最大页数
        maxpage = (int) (dataCount % number == 0 ? dataCount / number : dataCount / number + 1);

        //加载缓存数据
        refreshListView(1);
        //初始化listView适配器
        adapter = new SimpleAdapter(this, listMap, R.layout.list_item, new String[]{"title", "date"}, new int[]{R.id.tvTitle, R.id.tvTime});

        /* 在适配器之前加页脚，这样适配器会重新被封装成 '有页脚的适配器' */
        listView.addFooterView(pullUpFooter);
        listView.setAdapter(adapter);
        if (dataCount == 0) {
            listView.removeFooterView(pullUpFooter);
        }

    }

    /*将信息存入数据库*/
    private void DBInsert(Integer page, Integer ID, String date, String modified, String title, String content) {
        ContentValues cv = new ContentValues();
        cv.put("pages", page);
        cv.put("ID", ID);
        cv.put("date", date);
        cv.put("modified", modified);
        cv.put("title", title);
        cv.put("content", content);
        dbWrite.insert("user", null, cv);
    }

    /*刷新listView*/
    private void refreshListView(int showPage) {
        c = dbRead.rawQuery("SELECT * FROM user WHERE pages = ?", new String[]{String.valueOf(showPage)});
        while (c.moveToNext()) {
            for (int i = 0; i < c.getCount(); ++i) {
                c.moveToPosition(i);
                map = new HashMap<String, Object>();
                map.put("title", c.getString(c.getColumnIndex("title")));
                map.put("date", c.getString(c.getColumnIndex("date")));
                map.put("sql_id", c.getInt(c.getColumnIndex("_id")));
                listMap.add(map);
            }
        }
    }

    /*查询数据库记录总数*/
    public long getCount() {
        Cursor cursor = dbRead.rawQuery("select count(*)from user", null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    /*启用异步线程，来获取网络数据*/
    class GetDataTask extends AsyncTask<String, Void, StringBuilder> {
        @Override
        protected StringBuilder doInBackground(String... params) {
            try {
                //URL中数据的读取
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line;
                builder = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }

                br.close();
                isr.close();
                is.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(StringBuilder builder) {
            try {
                dbWrite.delete("user", null, null);//清空user表
                JSONArray jsonArray = new JSONArray(builder.toString());
                dataCount = jsonArray.length();//总共的条数
                int page = 1;
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jo = jsonArray.getJSONObject(i);

                    page = i / number + 1;//将之按10个每页来分页
                    int ID = jo.getInt("ID");//自增唯一ID
                    String post_date = jo.getString("post_date");//发布时间
                    String post_modified = jo.getString("post_modified");//修改时间
                    String post_title = jo.getString("post_title");//标题
                    String post_content = jo.getString("post_content");//正文

                    //写入数据库
                    DBInsert(page, ID, post_date, post_modified, post_title, post_content);
                }
                maxpage = page;
                listMap.clear();

                refreshListView(1);//刷新
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(builder);

        }
    }

    /*listView点击事件，进入内容界面，并传递所点item的"_id"值*/
    private AdapterView.OnItemClickListener listClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                ListView listView = (ListView) parent;
                HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                int itemId = (int) data.get("sql_id");
                if (itemId > 0) {
                    Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                    intent.putExtra("db_id", itemId);
                    startActivity(intent);
                }
            } catch (Exception e) {
                //捕获异常，为防止点击最后footerView的情况
                e.printStackTrace();
            }
        }
    };

    /*关闭数据库与光标*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (c != null)
            c.close();
        if (db != null)
            db.close();
    }

    private int number = 10; // 每次获取多少条数据
    private int maxpage = 1; // 总共有多少页
    private boolean loadfinish = true; // 指示数据是否加载完成
    private static final int SHOW_FOOTER = 1;//继续加载
    private static final int NONE_FOOTER = 0;//已经到达最底部，加载完成
    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItCount;       // 当前窗口可见项总数

    private final class scrollListener implements AbsListView.OnScrollListener {

        /**
         * 滑动状态改变时被调用
         */
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
//            int itemsLastIndex = adapter.getCount()-1;  //数据集最后一项的索引
//            int lastIndex = itemsLastIndex + 1;
//            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
//                    && visibleLastIndex == lastIndex) {
//                // 如果是自动加载,可以在这里放置异步加载数据的代码
//            }
        }

        /**
         * 滑动时被调用
         */
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            //如果所有的记录选项等于数据集的条数，则移除列表底部视图
            if (totalItemCount == dataCount + 1 && dataCount != 0) {
                listView.removeFooterView(pullUpFooter);
                Toast.makeText(MainActivity.this, "数据全部加载完!", Toast.LENGTH_LONG).show();
            }

            int lastItemId = firstVisibleItem + visibleItemCount - 1;

            // 达到数据的最后一条记录
            if (lastItemId + 1 == totalItemCount && dataCount != 0) {
                if (lastItemId > 0) {
                    int currentPage = lastItemId % number == 0 ? lastItemId / number
                            : lastItemId / number + 1;
                    final int nextPage = currentPage + 1;

                    if (nextPage <= maxpage && loadfinish) {
                        loadfinish = false;
                        listView.addFooterView(pullUpFooter);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                handler.sendMessage(handler.obtainMessage(SHOW_FOOTER, nextPage));

                            }
                        }).start();
                    }
                }
            }
        }
    }

    private Handler handler = new Handler() {
        // 告诉ListView数据已经发生改变，要求ListView更新界面显示
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_FOOTER:
                    refreshListView((Integer) msg.obj);

                    adapter.notifyDataSetChanged();


                    if (listView.getFooterViewsCount() > 0) { // 如果有底部视图
                        listView.removeFooterView(pullUpFooter);
                    }
                    loadfinish = true; // 加载完成
                    break;
//                case NONE_FOOTER:
//                    Toast.makeText(MainActivity.this,"已经全部加载完成", Toast.LENGTH_SHORT).show();
//                    break;
                default:
                    break;
            }
        }
    };
}





