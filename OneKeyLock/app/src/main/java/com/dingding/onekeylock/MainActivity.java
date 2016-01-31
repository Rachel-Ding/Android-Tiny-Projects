package com.dingding.onekeylock;

/*
 * 5.一键锁屏
 * On 2016-1-24
 *
 * ***********修改版*****************
 */

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdmin;
    private Button btnCancel;
    private Button btnLock;
    private DevicePolicyManager devicePolicyManager;
    private static final int DEVICE_POLICY_MANAGER_REQUEST_CODE = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        btnAdmin = (Button) findViewById(R.id.btnAdmin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnLock = (Button) findViewById(R.id.btnLock);

        //按钮监听
        btnAdmin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnLock.setOnClickListener(this);

        //用来判断是否已经配置权限
        if (devicePolicyManager.isAdminActive(new ComponentName(this, DeviceManagerBC.class))) {
            pageSetAdmin();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdmin:
                //获取权限
                Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this, DeviceManagerBC.class));
                //替换startActivity(i)，对取消也作出反应
                startActivityForResult(i, DEVICE_POLICY_MANAGER_REQUEST_CODE);

                break;
            case R.id.btnCancel:
                //取消权限
                devicePolicyManager.removeActiveAdmin(new ComponentName(this, DeviceManagerBC.class));

                pageLock();
                break;
            case R.id.btnLock:
                devicePolicyManager.lockNow();//锁屏
                break;
        }
    }

    /*设置界面后切换*/
    public void pageSetAdmin() {
        btnAdmin.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
        btnLock.setVisibility(View.VISIBLE);
    }

    /*取消权限后界面切换*/
    public void pageLock() {
        btnAdmin.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
        btnLock.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                pageSetAdmin();
                break;
            case RESULT_CANCELED:
                break;
        }

    }
}
