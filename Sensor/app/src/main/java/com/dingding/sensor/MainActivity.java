package com.dingding.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //传感器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_PROXIMITY:
                System.out.println(event.values[0]);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                System.out.format("x=%f,y=%f,z=%f\n", event.values[0], event.values[1], event.values[2]);
                break;
            case Sensor.TYPE_ORIENTATION:
                System.out.format("value:%f\n", event.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        //距离传感器
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        //传感器监听器
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        //加速度传感器
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        //指南针传感器
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //注销注册
        sensorManager.unregisterListener(this);
    }
}
