package com.dingding.onekeylock;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 广播接收器，侦听当前应用是否有系统组件的能力
 * Created by Dingding on 2016/1/24.
 */
public class DeviceManagerBC extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);

        Toast.makeText(context, "已获取设备管理者权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);

        Toast.makeText(context, "您取消了设备管理者权限", Toast.LENGTH_SHORT).show();
    }
}

