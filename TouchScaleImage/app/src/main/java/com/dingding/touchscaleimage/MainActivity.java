package com.dingding.touchscaleimage;

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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int mode = NONE;
    private float oldDist;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private PointF start = new PointF();
    private PointF mid = new PointF();
    Context context;
    ImageView view;
    Bitmap bitmap;
    DisplayMetrics dm;
    float minScaleR=0.1f;  //最少缩放比例
    static final float MAX_SCALE = 4f; //最大缩放比例
    float dist = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        view = (ImageView) findViewById(R.id.imageView);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); //获取分辨率


        matrix.setScale(minScaleR, minScaleR); //开始先缩小
        center();
//        matrix.postTranslate(120, 120);     //图片的位置相对于imageview的左上角往右往下各偏移120个像素
        view.setImageMatrix(matrix);


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:  //单点
                        Toast.makeText(MainActivity.this, bitmap.getWidth() + "*" + bitmap.getHeight(), Toast.LENGTH_LONG).show();
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                        mode = DRAG;
                        break;
                    case MotionEvent.ACTION_UP:  //单点弹起
                    case MotionEvent.ACTION_POINTER_UP:  //多点弹起
                        mode = NONE;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:  //多点
                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE: //按下且在拖动
                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);  //xy方向都可以拖动
                            //matrix.postTranslate(event.getX() - start.x,0); //只在x轴方向拖动 即左右拖动  上下不动
                            //matrix.postTranslate(0,event.getY() - start.y);  //只在y轴方向拖动 即上下拖动  左右不动
                        } else if (mode == ZOOM) {
                            float newDist = spacing(event);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float scale = newDist / oldDist;
                                matrix.postScale(scale, scale, mid.x, mid.y);
                            }
                        }
                        break;
                }

                view.setImageMatrix(matrix);

                CheckScale();  //限制缩放范围
                center();  //居中控制
//                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return true;
            }


            //两点的距离
            private float spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return (float) Math.sqrt(x * x + y * y);
            }

            //两点的中点
            private void midPoint(PointF point, MotionEvent event) {
                float x = event.getX(0) + event.getX(1);
                float y = event.getY(0) + event.getY(1);
                point.set(x / 2, y / 2);
            }
        });


    }

    //限制最大最小缩放比例
    protected void CheckScale()
    {
        float p[] = new float[9];
        matrix.getValues(p);
        if (mode == ZOOM)
        {
            if (p[0] < minScaleR)
            {
                matrix.setScale(minScaleR, minScaleR);
            }
            if (p[0] > MAX_SCALE)
            {
                matrix.set(savedMatrix);
            }
        }
    }

    //自动居中  左右及上下都居中
    protected void center()
    {
        center(true,true);
    }

    private void center(boolean horizontal, boolean vertical)
    {
        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);
        float height = rect.height();
        float width = rect.width();
        float deltaX = 0, deltaY = 0;
        if (vertical)
        {
            int screenHeight = dm.heightPixels;  //手机屏幕分辨率的高度
            int actionBarHeight=0;
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            }
//            int screenHeight = 400;
            if (height < screenHeight)
            {
                deltaY = (screenHeight - height)/2 - rect.top- actionBarHeight ;
            }else if (rect.top > 0)
            {
                deltaY = -rect.top;
            }else if (rect.bottom < screenHeight)
            {
                deltaY = view.getHeight() - rect.bottom;
            }
        }

        if (horizontal)
        {
            int screenWidth = dm.widthPixels;  //手机屏幕分辨率的宽度
//            int screenWidth = 400;
            if (width < screenWidth)
            {
                deltaX = (screenWidth - width)/2 - rect.left;
            }else if (rect.left > 0)
            {
                deltaX = -rect.left;
            }else if (rect.right < screenWidth)
            {
                deltaX = screenWidth - rect.right;
            }
        }
        matrix.postTranslate(deltaX, deltaY);

    }
}
