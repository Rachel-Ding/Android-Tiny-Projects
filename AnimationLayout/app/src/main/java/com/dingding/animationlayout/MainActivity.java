package com.dingding.animationlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //为布局添加动画效果
        LinearLayout rootView = (LinearLayout) findViewById(R.id.linearLayout);
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1);
        sa.setDuration(5000);

        //-----1-----延迟时间为0，布局中的控件同时开始动画
//        LayoutAnimationController lac = new LayoutAnimationController(sa,0);//( ，延迟的时间)
        //-----2-----延迟时间为前一个控件出现了一半，第二个开始出现
        LayoutAnimationController lac = new LayoutAnimationController(sa, 0.5f);

        //设置控件出现的顺序
        //ORDER_NORMAL--顺序,ORDER_RANDOM--随机,ORDER_REVERSE--逆序
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);

        rootView.setLayoutAnimation(lac);


    }
}
