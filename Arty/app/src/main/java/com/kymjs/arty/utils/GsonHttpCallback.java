package com.kymjs.arty.utils;

import com.google.gson.Gson;
import com.kymjs.rxvolley.client.HttpCallback;

/**
 * Created by ZhangTao on 12/23/16.
 */

public abstract class GsonHttpCallback<T> extends HttpCallback {
    protected Gson gson = new Gson();
    protected T data;

    protected abstract Class<T> getDataType();

    @Override
    public void onSuccessInAsync(byte[] t) {
        super.onSuccessInAsync(t);
        if (t != null) {
            data = gson.fromJson(new String(t), getDataType());
        }
    }
}
