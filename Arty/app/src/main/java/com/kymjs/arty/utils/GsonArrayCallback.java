package com.kymjs.arty.utils;

import com.google.gson.Gson;
import com.kymjs.rxvolley.client.HttpCallback;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ZhangTao on 12/25/16.
 */

public abstract class GsonArrayCallback<T> extends HttpCallback {
    protected Gson gson = new Gson();
    protected List<T> data;

    protected abstract Type getType();

    @Override
    public void onSuccessInAsync(byte[] t) {
        super.onSuccessInAsync(t);
        if (t != null) {
            data = gson.fromJson(new String(t), getType());
        }
    }
}
