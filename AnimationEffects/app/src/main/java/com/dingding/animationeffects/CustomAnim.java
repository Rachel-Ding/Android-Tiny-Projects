package com.dingding.animationeffects;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Dingding on 2016/1/16.
 */
public class CustomAnim extends Animation {

    /*获取目标对象宽高*/
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) { //(补间时间，变化对象)

        //-----1------透明度变化效果
//        t.setAlpha(interpolatedTime);

        //----2-------这里是没有动画过渡的，是瞬间移动
//        t.getMatrix().setTranslate(200,200);
        //-----3------这里是一个过程
//        t.getMatrix().setTranslate(200*interpolatedTime,200*interpolatedTime);

        //------4-----左右摇摆效果
        t.getMatrix().setTranslate((float)(Math.sin(interpolatedTime*20)*50),0);

        super.applyTransformation(interpolatedTime, t);
    }
}
