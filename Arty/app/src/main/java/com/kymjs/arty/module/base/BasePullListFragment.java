package com.kymjs.arty.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.kymjs.arty.R;
import com.kymjs.arty.interf.IRequestVo;
import com.kymjs.common.Log;
import com.kymjs.recycler.adapter.BasePullUpRecyclerAdapter;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.view.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 自带下拉刷新的Fragment基类
 * <p>
 * Created by ZhangTao on 12/26/16.
 */
public abstract class BasePullListFragment<T> extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, IRequestVo {

    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    @BindView(R.id.swiperefreshlayout)
    public SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.emptyLayout)
    public EmptyLayout mEmptyLayout;

    protected BasePullUpRecyclerAdapter<T> adapter;
    protected List<T> datas = new ArrayList<>();
    protected Gson gson = new Gson();

    protected abstract BasePullUpRecyclerAdapter<T> getAdapter();

    protected abstract List<T> parserInAsync(byte[] t);

    protected HttpCallback callBack = new HttpCallback() {
        private List<T> tempDatas;

        @Override
        public void onSuccessInAsync(byte[] t) {
            super.onSuccessInAsync(t);
            try {
                tempDatas = parserInAsync(t);
            } catch (Exception e) {
                tempDatas = null;
            }
        }

        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            Log.d("===列表网络请求:" + t);
            if (tempDatas == null || tempDatas.isEmpty() || adapter == null || adapter
                    .getItemCount() < 1) {
                mEmptyLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mEmptyLayout.dismiss();
                adapter.refresh(tempDatas);
                datas = tempDatas;
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            Log.d("====网络请求异常" + strMsg);
            //有可能界面已经关闭网络请求仍然返回
            if (mEmptyLayout != null && adapter != null) {
                if (adapter.getItemCount() > 1) {
                    mEmptyLayout.dismiss();
                } else {
                    mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                }
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            setSwipeRefreshLoadedState();
        }
    };

    @Override
    public int inflateLayout() {
        return R.layout.base_list_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initWidget();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doRequest();
        adapter = getAdapter();
        adapter.getFooterView().setNoMoreText(null);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //滚动到底部的监听
                    LinearLayoutManager layoutManager = getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastItems = layoutManager.findFirstVisibleItemPosition();
                    if ((pastItems + visibleItemCount) >= totalItemCount) {
                        onBottom();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void initWidget() {
        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequest();
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_contrast);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
    }

    public void setSwipeRefreshLoadingState() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setEnabled(false);
    }

    public void setSwipeRefreshLoadedState() {
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(true);
    }

    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) mRecyclerView.getLayoutManager();
    }

    @Override
    public void onRefresh() {
        setSwipeRefreshLoadingState();
        doRequest();
    }

    public void onBottom() {
        adapter.setState(BasePullUpRecyclerAdapter.STATE_NO_MORE);
    }
}
