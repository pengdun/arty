package com.kymjs.arty.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by ZhangTao on 1/4/17.
 */

public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}