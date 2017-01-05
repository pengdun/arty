package com.kymjs.arty;

import android.app.Application;

import com.kymjs.arty.utils.TypefaceUtils;
import com.kymjs.common.Log;
import com.kymjs.common.SystemTool;
import com.kymjs.crash.CustomActivityOnCrash;
import com.kymjs.rxvolley.toolbox.Loger;

/**
 * Created by ZhangTao on 12/24/16.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Loger.setEnable(BuildConfig.DEBUG);
        Log.setEnable(BuildConfig.DEBUG);
        CustomActivityOnCrash.install(this);

        TypefaceUtils.justSimpleFont = true;
    }
}
