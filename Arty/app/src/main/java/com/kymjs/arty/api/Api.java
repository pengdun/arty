package com.kymjs.arty.api;

import com.kymjs.common.App;
import com.kymjs.common.Log;
import com.kymjs.okhttp3.OkHttpStack;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.http.RequestQueue;

import okhttp3.OkHttpClient;


/**
 * Created by ZhangTao on 12/23/16.
 */

public class Api {

    public static final String ALPHA_HOST = "http://172.16.13.32/php";
    public static final String BETA_HOST = "http://192.168.31.155/php";
    public static final String HOST = "http://121.41.8.65";

    public static final String API_VERSION = "/api";
    public static final String SPLASH = "/splash";
    public static final String RECOMMEND = "/recommend";
    public static final String MOMENTS = "/moment";

    static {
        RxVolley.setRequestQueue(
                RequestQueue.newRequestQueue(RxVolley.CACHE_FOLDER,
                        new OkHttpStack(new OkHttpClient())));
    }

    public static String getURL(String api) {
        String url = String.format("%s%s%s", getHost(), API_VERSION, api);
        Log.d("=====发起请求::" + url);
        return url;
    }

    private static String getHost() {
        int code = App.getEnvCode();
        switch (code) {
            case App.ENV_ALPHA:
                return ALPHA_HOST;
            case App.ENV_BETA:
                return BETA_HOST;
            default:
                return HOST;
        }
    }
}
