package com.dingding.jokes.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dingding.jokes.R;

/**
 * 下拉刷新功能
 */
public class PullRefreshView extends LinearLayout implements View.OnTouchListener {

    /*下拉状态*/
    private static final int STATUS_PULL_TO_REFRESH = 0;
    /*释放立即刷新状态*/
    private static final int STATUS_RELEASE_TO_REFRESH = 1;
    /*正在刷新状态*/
    public static final int STATUS_REFRESHING = 2;
    /*刷新完成或未刷新状态*/
    public static final int STATUS_REFRESH_FINISHED = 3;
    /*当前的状态*/
    private int currentStatus = STATUS_REFRESH_FINISHED;
    /*记录上一次的状态是什么，避免进行重复操作*/
    private int lastStatus = currentStatus;

    /*下拉头部回滚的速度*/
    public static final int SCROLL_SPEED = -20;
    /*1分钟*/
    private static final long ONE_MINUTE = 60 * 1000;
    /*1小时*/
    private static final long ONE_HOUR = 60 * 60 * 1000;
    /*1天*/
    private static final long ONE_DAY = 24 * 60 * 60 * 1000;
    /*1个月*/
    private static final long ONE_MONTH = 30 * 24 * 60 * 60 * 1000;
    /*1年*/
    private static final long ONE_YEAR = 12 * 30 * 24 * 60 * 60 * 1000;

    /*SharedPreferences存储更新的时间的键*/
    private static final String UPDATE_TIME = "update_time";

    /*下拉时显示的view*/
    private View refreshView;
    private NetworkUtils networkUtils;
    private Toast netToast;
    private Context context;
    /*刷新箭头*/
    private ImageView refresh_arrow;
    /*刷新进度*/
    private ProgressBar progressBar;
    private TextView condition;
    private TextView last_update_time;
    /*下拉时，开始进行刷新需要的手指移动距离*/
    private int touchSlop;
    /*用于存储更新的时间*/
    private SharedPreferences preferences;
    /*下拉刷新的ListView*/
    private ListView listView;
    /*用于判断是否加载过onLayout*/
    private boolean havelayout;
    /*刷新域的高度*/
    private int refreshHeight;
    /*刷新域的布局*/
    private MarginLayoutParams refreshLayoutParams;
    /*当前是否可以下拉，只有ListView滚动到头的时候才允许下拉*/
    private boolean ableToPull;

    /*上一次的更新时间*/
    private long savedTime;
    /*手指按下时的屏幕Y轴坐标*/
    private float downY;

    /*下拉刷新的回调接口*/
    private RefreshListener rlistener;


    /*构造函数，进行初始化布局*/
    public PullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        refreshView = LayoutInflater.from(context).inflate(R.layout.refresh_view, null, true);
        refresh_arrow = (ImageView) refreshView.findViewById(R.id.refresh_arrow);
        progressBar = (ProgressBar) refreshView.findViewById(R.id.progressbar);
        condition = (TextView) refreshView.findViewById(R.id.condition);
        last_update_time = (TextView) refreshView.findViewById(R.id.last_update_time);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        lastUpdateTimeShow();
        setOrientation(VERTICAL);//设为垂直布局
        addView(refreshView, 0);
    }

    /*给下拉刷新控件注册一个监听器*/
    public void setOnRefreshListener(RefreshListener listener) {
        rlistener = listener;
    }

    /*当所有的刷新逻辑完成后，记录调用一下，否则ListView将一直处于正在刷新状态*/
    public void finishRefresh() {
        currentStatus = STATUS_REFRESH_FINISHED;
        preferences.edit().putLong(UPDATE_TIME, System.currentTimeMillis()).commit();
        new HideRefreshTask().execute();
    }

    /*listView的触摸监听*/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //判断是listview的移动，还是刷新
        ifPullRefresh(event);
        if (ableToPull) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float moveY = event.getRawY();
                    int moveDistance = (int) (moveY - downY);//移动的距离
                    //若是向上滑动，且刷新域现在的距离 < 刷新域高度（刷新域是完全隐藏的），
                    //或者手指移动距离不超过需要的距离，则false
                    if ((moveDistance <= 0 && refreshLayoutParams.topMargin <= refreshHeight) || moveDistance < touchSlop) {
                        return false;
                    }

                    //判断是否联网
                    if (networkUtils.isWifi(context) || networkUtils.isNetworkConnected(context)) {
                        //当不是出于正在刷新状态
                        if (currentStatus != STATUS_REFRESHING) {
                            //当刷新域到顶部距离>0，设为释放立即刷新状态
                            //当刷新域到顶部距离<0，设为下拉状态
                            if (refreshLayoutParams.topMargin > 0) {
                                currentStatus = STATUS_RELEASE_TO_REFRESH;
                            } else {
                                currentStatus = STATUS_PULL_TO_REFRESH;
                            }
                            //通过偏移下拉头的topMargin值，来实现下拉效果
                            refreshLayoutParams.topMargin = moveDistance / 2 + refreshHeight;
                            refreshView.setLayoutParams(refreshLayoutParams);
                        }
                    } else {
                        //防止toast重复显示问题
                        if (netToast == null) {
                            netToast = Toast.makeText(context, "无法连接到网络", Toast.LENGTH_SHORT);
                        }
                        netToast.show();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                        //松手时为释放刷新状态，调用刷新异步线程
                        new RefreshTask().execute();
                    } else if (currentStatus == STATUS_PULL_TO_REFRESH) {
                        //松手时为下拉状态，就去调用隐藏刷新域的异步线程
                        new HideRefreshTask().execute();
                    }
                    break;
            }

            //记录刷新域的信息
            if (currentStatus == STATUS_PULL_TO_REFRESH || currentStatus == STATUS_RELEASE_TO_REFRESH) {
                updateRefreshView();
                //当前正处于下拉或释放状态，要让ListView失去焦点，否则被点击的那一项会一直处于选中状态
                listView.setPressed(false);
                listView.setFocusable(false);
                listView.setFocusableInTouchMode(false);
                lastStatus = currentStatus;
                //当前正处于下拉或释放状态，通过返回true屏蔽掉ListView的滚动事件
                return true;
            }
        }
        return false;
    }

    /*判断是listview的移动，还是刷新*/
    private void ifPullRefresh(MotionEvent event) {
        View firstView = listView.getChildAt(0);//获取此时listview的第一行
        if (firstView != null) {
            int firstVisiblePosition = listView.getFirstVisiblePosition();//获取当前可见的第一个Item的position
            // 如果首个元素的上边缘，距离父布局值为0，就说明ListView滚动到了最顶部，此时应该允许下拉刷新
            if (firstVisiblePosition == 0 && firstView.getTop() == 0) {
                if (!ableToPull) {
                    downY = event.getRawY();
                }
                ableToPull = true;
            } else {
                //重置控件布局，使刷新域距离父控件上方的距离为刷新域高度
                if (refreshLayoutParams.topMargin != refreshHeight) {
                    refreshLayoutParams.topMargin = refreshHeight;
                    refreshView.setLayoutParams(refreshLayoutParams);
                }
                ableToPull = false;
            }
        } else {
            //若ListView中是空的，没有元素，也可进行下拉刷新
            ableToPull = true;
        }
    }

    /*进行一些关键性的初始化操作，比如：将下拉头向上偏移进行隐藏，给ListView注册touch事件*/
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //四个参数分别代表着ViewGroup(PullRefreshView)在整个界面上的上下左右边框
        super.onLayout(changed, l, t, r, b);
        if (!havelayout && changed) {
            refreshHeight = -refreshView.getHeight();//刷新域高度（转为相反数）
            refreshLayoutParams = (MarginLayoutParams) refreshView.getLayoutParams();
            refreshLayoutParams.topMargin = refreshHeight;
            listView = (ListView) getChildAt(1);//该ViewGroup中的第二个view
            listView.setOnTouchListener(this);
            havelayout = true;
        }

    }

    /*刷新域中显示当前的状态*/
    private void updateRefreshView() {
        if (lastStatus != currentStatus) {

            if (currentStatus == STATUS_PULL_TO_REFRESH) {
                //处于下拉状态
                condition.setText(R.string.pull_refresh);
                refresh_arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                rotateArrow();//更新箭头信息
            } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                //处于释放立即刷新状态
                condition.setText(R.string.release_refresh);
                refresh_arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                rotateArrow();
            } else if (currentStatus == STATUS_REFRESHING) {
                condition.setText(R.string.refresh_now);
                refresh_arrow.clearAnimation();//清除箭头的动画效果
                refresh_arrow.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
            lastUpdateTimeShow();//更新时间显示
        }
    }

    /*根据当前的状态来旋转箭头*/
    private void rotateArrow() {
        float pivotX = refresh_arrow.getWidth() / 2f;
        float pivotY = refresh_arrow.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (currentStatus == STATUS_PULL_TO_REFRESH) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
            fromDegrees = 0f;
            toDegrees = 180f;
        }
        //箭头的旋转动画
        RotateAnimation ra = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        ra.setDuration(100);
        ra.setFillAfter(true);
        refresh_arrow.setAnimation(ra);
    }

    /*显示上次更新时间*/
    private void lastUpdateTimeShow() {
        savedTime = preferences.getLong(UPDATE_TIME, -1);//从SharedPreference中读取上次的更新时间
        long currentTime = System.currentTimeMillis();//当前系统时间
        long lastUpdateTime = currentTime - savedTime;//时间差
        String strSavedTime;
        String timeFomate = getResources().getString(R.string.last_update_time);
        if (savedTime == -1) {
            strSavedTime = getResources().getString(R.string.no_update_before);
        } else if (lastUpdateTime < 0) {
            strSavedTime = getResources().getString(R.string.error_time);
        } else if (lastUpdateTime < ONE_MINUTE) {
            strSavedTime = getResources().getString(R.string.have_updated);
        } else if (lastUpdateTime < ONE_HOUR) {
            strSavedTime = String.format(timeFomate, lastUpdateTime / ONE_MINUTE + "分钟");
        } else if (lastUpdateTime < ONE_DAY) {
            strSavedTime = String.format(timeFomate, lastUpdateTime / ONE_HOUR + "小时");
        } else if (lastUpdateTime < ONE_MONTH) {
            strSavedTime = String.format(timeFomate, lastUpdateTime / ONE_DAY + "天");
        } else if (lastUpdateTime < ONE_YEAR) {
            strSavedTime = String.format(timeFomate, lastUpdateTime / ONE_MONTH + "月");
        } else {
            strSavedTime = String.format(timeFomate, lastUpdateTime / ONE_YEAR + "年");
        }

        last_update_time.setText(strSavedTime);
    }


    /*启用异步线程，在刷新的过程中，来回调注册进来的下拉刷新监听器*/
    class RefreshTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int topMargin = refreshLayoutParams.topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;//以SCROLL_SPEED为速度
                if (topMargin <= 0) {
                    topMargin = 0;
                    break;
                }
                publishProgress(topMargin);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currentStatus = STATUS_REFRESHING;
            publishProgress(0);
            if (rlistener != null) {
                rlistener.onRefresh();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            updateRefreshView();
            refreshLayoutParams.topMargin = values[0];
            refreshView.setLayoutParams(refreshLayoutParams);
        }
    }

    /*启用异步线程，使刷新域重新隐藏*/
    class HideRefreshTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            int topMargin = refreshLayoutParams.topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;//以SCROLL_SPEED为速度
                if (topMargin <= refreshHeight) {
                    topMargin = refreshHeight;
                    break;
                }
                publishProgress(topMargin);//显示进度
                try {
                    Thread.sleep(10); //速度SCROLL_SPEED变动对应的10毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return topMargin;
        }

        /*重置刷新域的布局*/
        @Override
        protected void onProgressUpdate(Integer... values) {
            refreshLayoutParams.topMargin = values[0];
            refreshView.setLayoutParams(refreshLayoutParams);
        }

        @Override
        protected void onPostExecute(Integer values) {
            refreshLayoutParams.topMargin = values;
            refreshView.setLayoutParams(refreshLayoutParams);
            currentStatus = STATUS_REFRESH_FINISHED;
        }
    }

    /*下拉刷新的监听器，使用下拉刷新的地方应该注册此监听器来获取刷新回调。*/
    public interface RefreshListener {
        void onRefresh();
    }

}
