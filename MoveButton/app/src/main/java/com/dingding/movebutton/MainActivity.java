package com.dingding.movebutton;

/*
* 1.使用视图动画及属性动画控制按钮运动
* 通过对视图动画和属性动画的理解完成控制按钮运动效果。
* On 2016-1-19
*/

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化监听
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.imageView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //-----ViewAnimation---XML---------
            case R.id.button1:
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_button));
                break;
            //-----ViewAnimation---Java Code-------
            case R.id.button2:
                viewAnimation(v);
                break;
            //-----FrameAnimator---XML---------
            case R.id.button3:
                frameAnimatorXML(v);
                break;
            //-----FrameAnimator---Java Code---------
            case R.id.button4:
                frameAnimatorCode(v);
                break;
            //-----图片翻转-----------------------------
            case R.id.imageView:
                //属性动画
                imageAnimateFrame(v);
                //视图动画
//                imageAnimate(v);
                break;
            default:
                break;
        }
    }

    /* ViewAnimation---Java Code */
    private void viewAnimation(View v) {

        AnimationSet as = new AnimationSet(true);
        TranslateAnimation ta1 = new TranslateAnimation(0,200,0,0);
        ta1.setDuration(1000);
        as.addAnimation(ta1);
        TranslateAnimation ta2 = new TranslateAnimation(0,0,0,200);
        ta2.setDuration(1000);
        ta2.setStartOffset(1000);
        as.addAnimation(ta2);

        v.startAnimation(as);
    }

    /*FrameAnimator---XML*/
    private void frameAnimatorXML(View v) {
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.animator_button);
        animator.setTarget(v);
        animator.start();
    }

    /*FrameAnimator---Java Code*/
    private void frameAnimatorCode(View v) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playSequentially(ObjectAnimator.ofFloat(v, "translationX", 0, 200),
                ObjectAnimator.ofFloat(v, "translationY", 0, 200),
                ObjectAnimator.ofFloat(v, "translationY", 200, 0),
                ObjectAnimator.ofFloat(v, "translationX", 200, 0));
        set.start();
    }

    /*图片翻转----FrameAnimatorAnimation*/
    private void imageAnimateFrame(View v){
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playSequentially(ObjectAnimator.ofFloat(v, "rotationY", 0f, 180f),
                ObjectAnimator.ofFloat(v, "rotationY",180f,0f));
        set.start();
    }

    /*图片翻转----ViewAnimation*/
    private void imageAnimate(View v) {

        AnimationSet as = new AnimationSet(true);
        //首次翻转，使用自定义动画 ImageAnim3D
        ImageAnim3D imgAnim3D = new ImageAnim3D(false);
        imgAnim3D.setDuration(1000);
        v.measure(0, 0);
        imgAnim3D.setCenter(v.getWidth() / 2, v.getHeight() / 2);
        imgAnim3D.setFillAfter(true);
        as.addAnimation(imgAnim3D);
        //翻转回来
        ImageAnim3D imgAnim3D2 = new ImageAnim3D(true);
        imgAnim3D2.setDuration(1000);
        imgAnim3D2.setStartOffset(1000);
        v.measure(0, 0);
        imgAnim3D2.setCenter(v.getWidth() / 2, v.getHeight() / 2);
        as.addAnimation(imgAnim3D2);

        v.startAnimation(as);

    }

}
