package com.dingding.animateframe;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        //属性动画（旋转360度）
        //-----1------代码编辑（1、2、4）
//        v.animate().rotation(360).setDuration(1000).start();

        //-----2------XML编辑（1、2、4）
        //对象动画
//        ObjectAnimator animatior = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animate);
//        animatior.setTarget(v);
//        animatior.start();

        //-----3------使用动画集合----XML
//        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.animator_set);
//        animator.setTarget(v);
//        animator.start();

        //-----4-------旋转，相较于1，可订花程度较高-----代码编辑（1、2、4）
//        ObjectAnimator.ofFloat(v,"rotation",0,90,90,360).setDuration(1000).start();

        //-----5-------动画集合，设置动画顺序等--------代码编辑（3、5）
        AnimatorSet set = new AnimatorSet();
        set.setDuration(2000);
        set.playSequentially(ObjectAnimator.ofFloat(v, "alpha", 0, 1),
                ObjectAnimator.ofFloat(v, "translationY", 0, 200));
        set.start();
    }
}
