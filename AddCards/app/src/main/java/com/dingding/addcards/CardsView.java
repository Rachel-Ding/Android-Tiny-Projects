package com.dingding.addcards;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Dingding on 2016/1/3.
 */
public class CardsView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint = null;//画笔
    private int screenWidth, screenHeight, rectWidth, rectLeft, rectTop, rectRight, rectBottom;

    public CardsView(Context context) {
        super(context);
        paint = new Paint();

        getHolder().addCallback(this);//回调函数
    }

    public void draw() {
        Canvas canvas = getHolder().lockCanvas(); //锁定画布
        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
        rectWidth = (screenWidth - 80) / 4;

        canvas.drawColor(Color.WHITE);
        //绘制方格
        paint.setColor(Color.RED);
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 4; ++j) {

                rectLeft = 10 + j * (rectWidth + 20);
                rectTop = (screenHeight - 5 * rectWidth - 80) / 2 + i * (rectWidth + 20);
                rectRight = rectLeft + rectWidth;
                rectBottom = rectTop + rectWidth;
                canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, paint);

            }
        }

        //绘制字符
        paint.setColor(Color.WHITE);
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 4; ++j) {

                rectLeft = 10 + j * (rectWidth + 20);
                rectTop = (screenHeight - 5 * rectWidth - 80) / 2 + i * (rectWidth + 20);

                paint.setTextSize(rectWidth / 3);
                paint.setTextAlign(Paint.Align.CENTER); //字体坐标系居中

                //计算字体高度
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                float fontHeight = fontMetrics.bottom - fontMetrics.top;
                //计算文字baseline
                float textBaseY = rectTop + rectWidth / 2 + fontHeight / 2 - fontMetrics.bottom;
                canvas.drawText(String.valueOf(i * 4 + j + 1), rectLeft + rectWidth / 2, textBaseY, paint);
            }
        }


        getHolder().unlockCanvasAndPost(canvas); //解锁画布
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
