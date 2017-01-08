package com.kymjs.arty.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kymjs.arty.R;
import com.kymjs.arty.bean.UpGradeMessage;
import com.kymjs.arty.module.base.BaseActivity;
import com.kymjs.arty.module.diary.EditDiaryActivity;
import com.kymjs.arty.module.main.moment.MomentsFragment;
import com.kymjs.arty.module.main.recommend.RecommendFragment;
import com.kymjs.arty.module.upgrade.UpgradeService;
import com.kymjs.arty.utils.TypefaceUtils;
import com.kymjs.arty.view.AlertButtonDialog;
import com.kymjs.common.SystemTool;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.toolbox.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindString(R.string.app_name)
    String mTitle;
    @BindColor(R.color.color_main_text2)
    int mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
//        onChangeTitle(getString(R.string.main_tab_1));
        initViewPagerAndTabs();
        mNavigationView.setNavigationItemSelectedListener(this);
        startService(new Intent(this, UpgradeService.class));
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(mColor);
//        mToolbar.setSubtitleTextColor(mColor);
        mToolbar.setNavigationIcon(R.drawable.ic_drawer_home);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
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
        final MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new RecommendFragment(), getString(R.string.main_tab_1));
        pagerAdapter.addFragment(new MomentsFragment(), getString(R.string.main_tab_2));
        mViewPager.setAdapter(pagerAdapter);
        mTableLayout.setupWithViewPager(mViewPager);
        //viewpager切换监听器
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                onChangeTitle(pagerAdapter.getPageTitle(position).toString());
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
    }

    @OnClick(R.id.main_fabButton)
    void onFABClick(FloatingActionButton faButton) {
        EditDiaryActivity.start(getActivity());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTitle(String title) {
        mTitle = title;
//        mToolbar.setSubtitle(mTitle);
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

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_poem:
                break;
            case R.id.nav_collection:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_setting:
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpGradeMessage(UpGradeMessage upGradeMessage) {
        AlertButtonDialog alertButtonDialog = new AlertButtonDialog(this, AlertButtonDialog.PromptIconEnumType.PROMPT,
                getString(R.string.upgrad_dialog_title), getString(R.string.upgrad_dialog_content), AlertButtonDialog.ButtonEnum.TWO,
                getString(R.string.upgrad_dialog_btn_left), getString(R.string.upgrad_dialog_btn_right),
                new AlertButtonDialog.DialogOnClickListener() {
                    @Override
                    public void onRigth() {
                        SystemTool.installApk(MainActivity.this, FileUtils.getExternalCacheDir(getString(R.string.down_app_name)));
                    }

                    @Override
                    public void onLeft() {

                    }
                });
        alertButtonDialog.setCancelable(false);
        alertButtonDialog.setCanceledOnTouchOutside(false);
        alertButtonDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, UpgradeService.class));
    }
}
