package com.kymjs.arty.api;

import android.os.Build;

import com.kymjs.common.App;
import com.kymjs.common.DateUtils;
import com.kymjs.common.Log;
import com.kymjs.common.SystemTool;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

/**
 * Created by ZhangTao on 12/31/16.
 */

public class BI {
    public static void postEvent(String event) {
        HttpParams params = new HttpParams();
        params.put("device_time", DateUtils.getDataTime("yyyy-MM-dd HH:mm"));
        params.put("user_id", "123");
        params.put("device_id", SystemTool.getPhoneIMEI(App.INSTANCE));
        params.put("device_name", Build.DEVICE);
        params.put("event", event);
        params.put("app_version", SystemTool.getAppVersionName(App.INSTANCE));
        params.put("android_version", SystemTool.getSystemVersion());

        RxVolley.post(Api.getURL(Api.BI), params, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Log.d("=========bi::" + t);
            }
        });
    }
}
