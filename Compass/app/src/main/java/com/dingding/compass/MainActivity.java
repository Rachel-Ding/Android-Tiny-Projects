package com.dingding.compass;

/*
*2.实现一个指南针
* On 2016-1-21
*/

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView imageView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float degrees, preDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //传感器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //指南针传感器
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:
                degrees = -event.values[0];//读取度数

                //在屏幕上显示，若变化大于5度就显示变化
                if (Math.abs(degrees - preDegree) > 5) {
                    RotateAnimation ra = new RotateAnimation(preDegree, degrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    ra.setDuration(200);
                    ra.setFillAfter(true);
                    imageView.startAnimation(ra);
                    preDegree = degrees;//下次的初始数据
                }

                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销注册
        sensorManager.unregisterListener(this);
    }
}
