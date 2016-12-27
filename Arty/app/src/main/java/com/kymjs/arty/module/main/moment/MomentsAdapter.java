package com.kymjs.arty.module.main.moment;

import android.support.v7.widget.RecyclerView;

import com.kymjs.arty.R;
import com.kymjs.arty.module.base.BasePullMultiTypeRecyclerAdapter;
import com.kymjs.core.bitmap.client.BitmapCore;
import com.kymjs.recycler.adapter.RecyclerHolder;

import java.util.Collection;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ZhangTao on 12/27/16.
 */

public class MomentsAdapter extends BasePullMultiTypeRecyclerAdapter<Moment> {

    private static final int TYPE_COMMON_REMOTE = 1;

    public MomentsAdapter(RecyclerView v, Collection<Moment> datas) {
        super(v, datas);
    }

    @Override
    public int getItemType(int position) {
        return realDatas.get(position).getType();
    }

    @Override
    public int getItemIdByType(int viewType) {
        switch (viewType) {
            case TYPE_COMMON_REMOTE:
                return R.layout.moment_item;
            default:
                return 0;
        }
    }

    @Override
    public void convert(RecyclerHolder holder, Moment item, int position, int viewType) {
        switch (viewType) {
            case TYPE_COMMON_REMOTE:
                inflaterCommon(holder, item);
                break;
            default:
        }
    }

    private void inflaterCommon(RecyclerHolder holder, Moment item) {
        CircleImageView avatar = holder.getView(R.id.moment_item_avatar);
        new BitmapCore.Builder().view(avatar).url(item.getCreatedPortrait()).doTask();
    }
}
