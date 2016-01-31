package com.dingding.onekeyspeedup;

/* 4.一键加速
 * 完成一键加速，点击应用图标，Toast显示清理内存大小
 *   On 2016-1-25
 *
 ****************修改版****************
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long preAvaiMemory = getAvailMemory(MainActivity.this);//加速之前的系统内存

        Context context = MainActivity.this;

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取后台运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        //获取本项目的进程
        String currentProcess = context.getApplicationInfo().processName;

        /*也可以设置需要清理的后台程序的importance的程度
        * if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
        */

        //对系统中所有正在运行的进程进行迭代，如果进程名不是当前进程，则Kill掉
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {

            String processName = appProcessInfo.processName;
            //取得各个进程的包
            String[] pkgList = appProcessInfo.pkgList;
            if (!processName.equals(currentProcess)) {
                for (int i = 0; i < pkgList.length; ++i) {
                    activityManager.killBackgroundProcesses(pkgList[i]);
                }
            }
        }

        long newAvaiMemory = getAvailMemory(MainActivity.this);//加速之后的系统内存
        Toast.makeText(getApplicationContext(), "为您节省了" + (newAvaiMemory - preAvaiMemory) + "M内存", Toast.LENGTH_SHORT).show();
        //用完即关闭，为下次打开能够顺利开启
        finish();
    }

    /*获取可用内存大小*/
    private long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / (1024 * 1024);//返回值以 M 为单位
    }
}
