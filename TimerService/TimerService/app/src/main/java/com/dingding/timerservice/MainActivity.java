package com.dingding.timerservice;

/*
*3.使用AIDL连接TimerService
* 使用AIDL连接TimerService,实习跨应用通讯
* On 2015-12-16
*/
//---------------------修改版-----------------------------------

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private Button btnBind;
    private Button btnUnBind;
    private TextView textView;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        btnBind = (Button) findViewById(R.id.btnBind);
        btnUnBind = (Button) findViewById(R.id.btnUnBind);
        textView = (TextView) findViewById(R.id.textView);
        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.dingding.timerservice", "com.dingding.timerservice.MyService"));

        btnBind.setOnClickListener(this);
        btnUnBind.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != binder) {
            callUnRegistBinder();
            unbindService(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBind:
                bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnBind:
                if (null != binder) {
                    callUnRegistBinder();
                    unbindService(this);
                    binder = null;
                }
                break;
        }
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        binder = IMyAidlInterface.Stub.asInterface(service);

        try {
            binder.registCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        callUnRegistBinder();
    }


    private void callUnRegistBinder() {
        try {
            binder.unRegistCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private TimerServiceCallback.Stub onServiceCallback = new TimerServiceCallback.Stub() {

        @Override
        public void onTimer(int numIndex) throws RemoteException {
            Message msg = new Message();
            msg.obj = MainActivity.this;
            msg.arg1 = numIndex;
            handler.sendMessage(msg);
        }
    };

    //Handler更新
    private final MyHandler handler = new MyHandler();

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int index = msg.arg1;
            MainActivity _this = (MainActivity) msg.obj;
            _this.textView.setText("Current value is " + index);
        }
    }

    private IMyAidlInterface binder = null;
}
