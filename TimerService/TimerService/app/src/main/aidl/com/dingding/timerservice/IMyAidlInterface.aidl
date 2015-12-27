// IMyAidlInterface.aidl
package com.dingding.timerservice;

// Declare any non-default types here with import statements

import com.dingding.timerservice.TimerServiceCallback;

interface IMyAidlInterface {

//    void setData(String data);

    void registCallback(TimerServiceCallback callback);
    void unRegistCallback(TimerServiceCallback callback);
}
