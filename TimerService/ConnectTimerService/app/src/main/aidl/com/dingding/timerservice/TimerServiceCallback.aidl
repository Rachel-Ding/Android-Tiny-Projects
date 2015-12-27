// TimerServiceCallback.aidl
package com.dingding.timerservice;

// Declare any non-default types here with import statements

interface TimerServiceCallback {

    //回调函数
    void onTimer(int numIndex);
}
