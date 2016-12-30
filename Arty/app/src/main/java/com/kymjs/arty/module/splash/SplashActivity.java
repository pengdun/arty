package com.kymjs.arty.module.splash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kymjs.arty.Constant;
import com.kymjs.arty.R;
import com.kymjs.arty.api.Api;
import com.kymjs.arty.db.SQLdm;
import com.kymjs.arty.module.base.BaseActivity;
import com.kymjs.arty.module.main.MainActivity;
import com.kymjs.arty.utils.GsonArrayCallback;
import com.kymjs.common.Log;
import com.kymjs.common.function.ThreadSwitch;
import com.kymjs.core.bitmap.client.BitmapCore;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ZhangTao on 12/23/16.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.splash_avatar)
    ImageView splashAvatar;
    @BindView(R.id.splash_text)
    TextView splashText;
    @BindView(R.id.splash_skip)
    TextView splashSkip;

    public static long CLICK = System.currentTimeMillis();
    public static final long CLICK_SPACE = 500L;

    private CountDownTimer mCountDownTimer;
    private long remainingTime = 3000;
    private boolean isTimerPaused = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showSplash();
        splashSkip.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.skip_text));

        ThreadSwitch.get(8).io(new ThreadSwitch.IO() {
            @Override
            public void run() {
                try {
                    SQLdm.copyAssetDB(getActivity(), Constant.POEM_DB);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SQLdm.initLocalPoemMap();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isTimerPaused) {
            isTimerPaused = false;
            startTimer(remainingTime, 1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTimerPaused = true;
        cancelTimer();
    }

    /**
     * 取消倒计时
     */
    public void cancelTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @OnClick(R.id.splash_skip)
    public void clickSkip() {
        if (CLICK + CLICK_SPACE > System.currentTimeMillis()) {
            return;
        }
        CLICK = System.currentTimeMillis();
        cancelTimer();
        jump();
    }


    /**
     * 启动倒计时
     */
    private void startTimer(long millisInFuture, long countDownInterval) {
        mCountDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long l) {
                remainingTime = l;
            }

            @Override
            public void onFinish() {
                if (!isTimerPaused) {
                    jump();
                }
            }
        };
        mCountDownTimer.start();
    }

    /**
     * 从网络或缓存加载闪屏页
     */
    public void showSplash() {
        RxVolley.get(Api.getURL(Api.SPLASH), new GsonArrayCallback<SplashItem>() {

            @Override
            protected Type getType() {
                return new TypeToken<List<SplashItem>>() {
                }.getType();
            }

            @Override
            public void onSuccess(String t) {
                SplashItem item = data.get(0);
                for (SplashItem temp : data) {
                    if (temp.getOrder() > item.getOrder()) {
                        item = temp;
                    }
                }
                splashText.setText(item.getDescription());
                Log.d("======闪屏图片::" + item.getImageUrl());
                new BitmapCore.Builder()
                        .view(splashImage)
                        .url(item.getImageUrl())
                        .callback(new HttpCallback() {
                            @Override
                            public void onSuccess(Map<String, String> headers, Bitmap bitmap) {
                                super.onSuccess(headers, bitmap);
                                splashImage.setAnimation(AnimationUtils.loadAnimation
                                        (SplashActivity.this, R.anim.enteralpha));
                            }
                        })
                        .doTask();
            }
        });
    }

    private void jump() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
