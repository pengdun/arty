package com.kymjs.arty.module.main.moment;

import com.kymjs.arty.api.Api;
import com.kymjs.arty.module.base.BasePullListFragment;
import com.kymjs.recycler.adapter.BasePullUpRecyclerAdapter;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.List;

/**
 * Created by ZhangTao on 12/26/16.
 */

public class MomentsFragment extends BasePullListFragment<Moment> {
    private static final int PAGE_SIZE = 20;

    @Override
    protected BasePullUpRecyclerAdapter<Moment> getAdapter() {
        return new MomentsAdapter(mRecyclerView, datas);
    }

    @Override
    protected List<Moment> parserInAsync(byte[] t) {
        MomentData momentData = gson.fromJson(new String(t), MomentData.class);
        return momentData.getData();
    }

    @Override
    public void doRequest() {
        doRequest(0);
    }

    @Override
    public void onBottom() {
        super.onBottom();
        //减2的原因是adapter.getItemCount()会多返回一个footer
        doRequest((adapter.getItemCount() + PAGE_SIZE - 2) / PAGE_SIZE);
    }

    public void doRequest(int index) {
        HttpParams params = new HttpParams();
        params.put("index", index);
        params.put("size", PAGE_SIZE);
        RxVolley.get(Api.getURL(Api.MOMENTS), params, callBack);
    }
}
