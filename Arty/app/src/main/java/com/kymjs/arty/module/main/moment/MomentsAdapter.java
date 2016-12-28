package com.kymjs.arty.module.main.moment;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import com.kymjs.arty.R;
import com.kymjs.arty.utils.DateUtils;
import com.kymjs.core.bitmap.client.BitmapCore;
import com.kymjs.recycler.adapter.BasePullMultiTypeRecyclerAdapter;
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
        Bitmap bitmap = BitmapCore.getMemoryBitmap(item.getCreatedPortrait());
        if (bitmap != null) {
            avatar.setImageBitmap(bitmap);
        } else {
            new BitmapCore.Builder().view(avatar).loadResId(R.mipmap.ic_launcher)
                    .url(item.getCreatedPortrait()).doTask();
        }
        holder.setText(R.id.moment_item_name, item.getCreatedNickname());

        holder.setText(R.id.moment_item_date, DateUtils.friendlyTime(item.getCreatedTime()));

    }
}
