package com.kymjs.arty.module.main;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kymjs.arty.module.base.BaseActivity;
import com.kymjs.arty.R;
import com.kymjs.arty.module.main.moment.MomentsFragment;
import com.kymjs.arty.module.main.recommend.RecommendFragment;
import com.kymjs.arty.utils.TypefaceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.main_tabLayout)
    android.support.design.widget.TabLayout mTableLayout;
    @BindView(R.id.main_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.main_fabButton)
    FloatingActionButton mFAButton;

    @BindString(R.string.app_name)
    String mTitle;
    @BindColor(R.color.color_main_text2)
    int mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViewPagerAndTabs();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(mColor);
        try {
            Field field = Toolbar.class.getDeclaredField("mTitleTextView");
            field.setAccessible(true);
            TextView tvTitle = (TextView) field.get(mToolbar);
            TypefaceUtils.setTypeface(tvTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewPagerAndTabs() {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new RecommendFragment(), getString(R.string.main_tab_1));
        pagerAdapter.addFragment(new MomentsFragment(), getString(R.string.main_tab_2));
        mViewPager.setAdapter(pagerAdapter);
        mTableLayout.setupWithViewPager(mViewPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTitle(String title) {
        mTitle = title;
        setTitle(mTitle);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
