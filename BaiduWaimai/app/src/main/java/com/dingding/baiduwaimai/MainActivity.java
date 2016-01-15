package com.dingding.baiduwaimai;

/*
 * 作业1.实现百度外卖菜单列表
 *   On 2016-1-14
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.dingding.baiduwaimai.Fragment.DingdanFragment;
import com.dingding.baiduwaimai.Fragment.HomeFragment;
import com.dingding.baiduwaimai.Fragment.MeFragment;
import com.dingding.baiduwaimai.UI.GradientIconView;
import com.dingding.baiduwaimai.UI.GradientTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<GradientIconView> mTabIconIndicator = new ArrayList<GradientIconView>();
    private List<GradientTextView> mTabTextIndicator = new ArrayList<GradientTextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.container);

        //适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            /*打开相应Fragment*/
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new HomeFragment();
                    case 1:
                        return new DingdanFragment();
                    case 2:
                        return new MeFragment();
                }
                return null;
            }
        };

        initTabIndicator(); //设置底部tab

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

    }

    /*初始化tab相关项*/
    private void initTabIndicator() {
        //icon项
        GradientIconView iconHome = (GradientIconView) findViewById(R.id.id_icon_home);
        GradientIconView iconDingdan = (GradientIconView) findViewById(R.id.id_icon_dingdan);
        GradientIconView iconMe = (GradientIconView) findViewById(R.id.id_icon_me);
        //字符项
        GradientTextView textHome = (GradientTextView) findViewById(R.id.id_text_home);
        GradientTextView textDingdan = (GradientTextView) findViewById(R.id.id_text_dingdan);
        GradientTextView textMe = (GradientTextView) findViewById(R.id.id_text_me);

        mTabIconIndicator.add(iconHome);
        mTabIconIndicator.add(iconDingdan);
        mTabIconIndicator.add(iconMe);

        mTabTextIndicator.add(textHome);
        mTabTextIndicator.add(textDingdan);
        mTabTextIndicator.add(textMe);

        iconHome.setOnClickListener(this);
        iconDingdan.setOnClickListener(this);
        iconMe.setOnClickListener(this);

        textHome.setOnClickListener(this);
        textDingdan.setOnClickListener(this);
        textMe.setOnClickListener(this);

        iconHome.setIconAlpha(1.0f);
        textHome.setTextViewAlpha(1.0f);
    }

    @Override
    public void onClick(View v) {

        resetOtherTabs();

        switch (v.getId()) {
            case R.id.id_icon_home:
            case R.id.id_text_home:
                mTabIconIndicator.get(0).setIconAlpha(1.0f);
                mTabTextIndicator.get(0).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_icon_dingdan:
            case R.id.id_text_dingdan:
                mTabIconIndicator.get(1).setIconAlpha(1.0f);
                mTabTextIndicator.get(1).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_icon_me:
            case R.id.id_text_me:
                mTabIconIndicator.get(2).setIconAlpha(1.0f);
                mTabTextIndicator.get(2).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
        }
    }

    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIconIndicator.size(); i++) {
            mTabIconIndicator.get(i).setIconAlpha(0);
        }
        for (int i = 0; i < mTabTextIndicator.size(); i++) {
            mTabTextIndicator.get(i).setTextViewAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            GradientIconView iconLeft = mTabIconIndicator.get(position);
            GradientIconView iconRight = mTabIconIndicator.get(position + 1);

            GradientTextView textLeft = mTabTextIndicator.get(position);
            GradientTextView textRight = mTabTextIndicator.get(position + 1);

            iconLeft.setIconAlpha(1 - positionOffset);
            textLeft.setTextViewAlpha(1 - positionOffset);
            iconRight.setIconAlpha(positionOffset);
            textRight.setTextViewAlpha(positionOffset);
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
