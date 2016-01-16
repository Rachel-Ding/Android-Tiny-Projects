package com.dingding.animationeffects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//=============================================================
//             1------透明动画效果
//=============================================================
        findViewById(R.id.btnAlphaMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //----1---代码编辑------------------------
//                AlphaAnimation anim_alpha = new AlphaAnimation(0,1);
//                anim_alpha.setDuration(1000);//动画时间
//                v.startAnimation(anim_alpha);//启动动画

                //----1----XML编辑---------------------------
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_alpha));
            }
        });

//==========================================================
//              2------旋转动画效果
//===========================================================
        findViewById(R.id.btnRotateMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //----2---代码编辑------------------------
                //绕默认坐标旋转（控件的左上角）
                RotateAnimation ra = new RotateAnimation(0,360);
                //还可指明绕着某一点旋转,RotateAnimation(0,360,x坐标,y坐标)
//                RotateAnimation ra = new RotateAnimation(0,360,200,500);
                //使用相对坐标
//                RotateAnimation ra = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

                ra.setDuration(1000);
                v.startAnimation(ra);

                //----2----XML编辑---------------------------
//                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_rotate));
            }
        });

//==========================================================
//              3------移动动画效果
//===========================================================
        findViewById(R.id.btnTranslateMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //----3---代码编辑------------------------
//                TranslateAnimation ta = new TranslateAnimation(0,200,0,200);//这里的200是相对位置
//                ta.setDuration(1000);
//                v.startAnimation(ta);

                //----3----XML编辑---------------------------
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_translate));
            }
        });

//==========================================================
//              4------缩放动画效果
//===========================================================
        findViewById(R.id.btnScaleMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //----4---代码编辑------------------------
                //缩放中心为控件左上角
//                ScaleAnimation sa = new ScaleAnimation(0,1,0,1);
                //从某一点开始缩放
//                ScaleAnimation sa = new ScaleAnimation(0,1,0,1,200,500);
                //相对于自身的百分比
                ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                sa.setDuration(1000);
                v.startAnimation(sa);

                //----4----XML编辑---------------------------
//                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_scale));
            }
        });

//==========================================================
//              5------动画效果混合
//===========================================================

        findViewById(R.id.btnAnimateMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //----5---代码编辑------------------------
//                AnimationSet as = new AnimationSet(true);//是否共用动画补间（匀速、加速、减速）
//                as.setDuration(1000);
//                AlphaAnimation aa = new AlphaAnimation(0,1);
//                aa.setDuration(1000);
//                as.addAnimation(aa);
//                TranslateAnimation ta = new TranslateAnimation(200,0,200,0);
//                ta.setDuration(1000);
//                as.addAnimation(ta);
//
//                v.startAnimation(as);

                //----5----XML编辑---------------------------
//                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim));



//==========================================================
//              6------动画效果侦听
//===========================================================
                Animation a = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim);
                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Toast.makeText(MainActivity.this, "Animation end", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                v.startAnimation(a);
            }
        });

//==========================================================
//              7------自定义动画效果
//===========================================================
        findViewById(R.id.btnCustomAnim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAnim ca = new CustomAnim();
                ca.setDuration(1000);
                v.startAnimation(ca);
            }
        });

    }
}
