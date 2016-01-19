package com.dingding.movebutton;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 绕中线Y轴的正反向翻转
 * Created by Dingding on 2016/1/18.
 */
public class ImageAnim3D extends Animation {

    private float mCenterX = 0;
    private float mCenterY = 0;
    private boolean turn; //当为false则是正常翻转，为ture则是翻转回来

    public ImageAnim3D(boolean turn) {
        this.turn = turn;
    }

    public void setCenter(float centerX, float centerY) {
        mCenterX = centerX;
        mCenterY = centerY;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix = t.getMatrix();
        Camera camera = new Camera();
        camera.save();
        if (!turn) {
            //正向翻转
            camera.rotateY(180 * interpolatedTime);
        } else {
            //反向翻转
            camera.rotateY(-180 * interpolatedTime);
        }

        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-mCenterX, (float) (-0.2 * mCenterY));
        matrix.postTranslate(mCenterX, (float) (0.2 * mCenterY));
    }

}
