package com.kymjs.arty.module.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by ZhangTao on 12/26/16.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    @LayoutRes
    public abstract int inflateLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = View.inflate(getActivity(), inflateLayout(), null);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }
}
