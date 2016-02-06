package com.dingding.scaleimage;

/* 3.实现双指缩放图片
 * 使用多点触摸相关的API实现双指缩放图片的效果
 *  On 2016-1-26
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private int screenWidth, screenHeight;//屏幕宽高
    private Bitmap bitmap;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private int mode = 0;
    private float distance;
    private float preDistance;
    private PointF mid = new PointF();//两指中点
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        imageView = (ImageView) findViewById(R.id.imageView);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);



        matrix.setScale(0.5f, 0.5f); //显示先缩小一些
        center();//缩小后居中
        imageView.setImageMatrix(matrix);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    //单个手指触摸
                    case MotionEvent.ACTION_DOWN:
                        mode = 1;
                        break;
                    //两指触摸
                    case MotionEvent.ACTION_POINTER_DOWN:
                        preDistance = getDistance(event);
                        //当两指间距大于10时，计算两指中心点
                        if (preDistance > 10f) {
                            mid = getMid(event);
                            savedMatrix.set(matrix);
                            mode = 2;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mode = 0;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //当两指缩放，计算缩放比例
                        if (mode == 2) {
                            distance = getDistance(event);
                            if (distance > 10f) {
                                matrix.set(savedMatrix);
                                float scale = distance / preDistance;
                                matrix.postScale(scale, scale, mid.x, mid.y);//缩放比例和中心点坐标
                            }

                        }
                        break;
                }
                view.setImageMatrix(matrix);

                center();  //回弹，令图片居中
                return true;
            }
        });


    }

    /*获取两指之间的距离*/
    private float getDistance(MotionEvent event) {
        float x = event.getX(1) - event.getX(0);
        float y = event.getY(1) - event.getY(0);
        float distance = (float) Math.sqrt(x * x + y * y);//两点间的距离
        return distance;
    }

    /*使图片居中*/
    private void center() {
        Matrix m = new Matrix();
        m.set(matrix);
        //绘制图片矩形
        //这样rect.left，rect.right,rect.top,rect.bottom分别就就是当前屏幕离图片的边界的距离
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();
        float deltaX = 0, deltaY = 0;

        //屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); //获取屏幕分辨率
        screenWidth = dm.widthPixels;  //屏幕宽度
        screenHeight = dm.heightPixels;  //屏幕高度

        //获取ActionBar的高度
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }

        //计算Y到中心的距离
        if (height < screenHeight) {
            deltaY = (screenHeight - height) / 2 - rect.top - actionBarHeight;
        } else if (rect.top > 0) {
            deltaY = -rect.top;
        } else if (rect.bottom < screenHeight) {
            deltaY = imageView.getHeight() - rect.bottom;
        }

        //计算X到中心的距离
        if (width < screenWidth) {
            deltaX = (screenWidth - width) / 2 - rect.left;
        } else if (rect.left > 0) {
            deltaX = -rect.left;
        } else if (rect.right < screenWidth) {
            deltaX = screenWidth - rect.right;
        }
        matrix.postTranslate(deltaX, deltaY);

    }

    /*取两指的中心点坐标*/
    public static PointF getMid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }
}

