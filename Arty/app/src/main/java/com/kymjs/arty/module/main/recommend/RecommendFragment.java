package com.kymjs.arty.module.main.recommend;

import com.kymjs.arty.R;
import com.kymjs.arty.api.Api;
import com.kymjs.arty.module.base.BasePullListFragment;
import com.kymjs.recycler.adapter.BasePullUpRecyclerAdapter;
import com.kymjs.recycler.adapter.RecyclerHolder;
import com.kymjs.rxvolley.RxVolley;

import java.util.List;

/**
 * 主页:推荐页
 * <p>
 * Created by ZhangTao on 12/26/16.
 */
public class RecommendFragment extends BasePullListFragment<Poem> {

    @Override
    protected BasePullUpRecyclerAdapter<Poem> getAdapter() {
        return new BasePullUpRecyclerAdapter<Poem>(
                mRecyclerView, datas, R.layout.recommend_item) {
            @Override
            public void convert(RecyclerHolder holder, Poem item, int position) {
                holder.setText(R.id.hello, item.getDescription());
            }
        };
    }

    @Override
    protected List<Poem> parserInAsync(byte[] t) {
        RecommendData recommendData = gson.fromJson(new String(t), RecommendData.class);
        return recommendData.getData();
    }

    @Override
    public void doRequest() {
        RxVolley.get(Api.getURL(Api.RECOMMEND), callBack);
    }
}
