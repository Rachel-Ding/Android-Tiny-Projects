package com.dingding.timerservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class MyService extends Service {
    private RemoteCallbackList<TimerServiceCallback> callbackList = new RemoteCallbackList<>();//回调列表（管道）

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterface.Stub() {

            @Override
            public void registCallback(TimerServiceCallback callback) throws RemoteException {
                callbackList.register(callback);
            }

            @Override
            public void unRegistCallback(TimerServiceCallback callback) throws RemoteException {
                callbackList.unregister(callback);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread() {
            @Override
            public void run() {
                super.run();

                running = true;

                for (numIndex = 0; running; ++numIndex) {

                    System.out.println(numIndex);

                    //回调
                    int count = callbackList.beginBroadcast();

                    while (count-- > 0) {
                        try {
                            callbackList.getBroadcastItem(count).onTimer(numIndex);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    callbackList.finishBroadcast();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    private boolean running = false;
    private int numIndex = 0;

}
