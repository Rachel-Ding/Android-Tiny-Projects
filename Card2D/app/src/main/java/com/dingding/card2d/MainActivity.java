package com.dingding.card2d;

/*
* 2.实现一个2D翻转的卡片
* 通过对动画的理解。完成2D卡片的翻转效果。
* On 2016-1-19
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    //缩放动画，以中间的Y轴为对称轴，X轴进行缩放
    private ScaleAnimation sa1 = new ScaleAnimation(1, 0, 1, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
    private ScaleAnimation sa2 = new ScaleAnimation(0, 1, 1, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        /*设置监听事件
        点击了布局后，设置图1可见或图2不可见*/
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView1.getVisibility() == View.VISIBLE) {
                    imageView1.startAnimation(sa1);
                } else {
                    imageView2.startAnimation(sa1);
                }
            }
        });

    }

    //初始化
    private void init() {
        imageView1 = (ImageView) findViewById(R.id.iv1);
        imageView2 = (ImageView) findViewById(R.id.iv2);
        showImage1();
        sa1.setDuration(1000);
        sa2.setDuration(1000);

        //设置动画监听，当缩小的动画结束后，设置下一步的动画
        sa1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (imageView1.getVisibility() == View.VISIBLE) {
                    imageView1.setAnimation(null);
                    showImage2();
                    imageView2.startAnimation(sa2);
                } else {
                    imageView2.setAnimation(null);
                    showImage1();
                    imageView1.startAnimation(sa2);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /*图1 可见，图2 不可见*/
    private void showImage1() {
        imageView1.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
    }

    /*图2 可见，图1 不可见*/
    private void showImage2() {
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.VISIBLE);
    }

}
