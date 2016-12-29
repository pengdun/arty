package com.kymjs.arty.module.main.moment;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kymjs.arty.R;
import com.kymjs.arty.db.LocalPoem;
import com.kymjs.arty.db.SQLdm;
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

    private static final int TYPE_COMMON_TEXT = 1;
    private static final int TYPE_COMMON_IMAGE = 2;

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
            case TYPE_COMMON_TEXT:
                return R.layout.moment_item_text;
            case TYPE_COMMON_IMAGE:
                return R.layout.moment_item_text_img;
            default:
                return 0;
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return super.onCreateViewHolder(parent, viewType);
        } else {
            LayoutInflater inflater = LayoutInflater.from(cxt);
            View root = inflater.inflate(getItemIdByType(viewType), parent, false);
            return new RecyclerHolder(root);
        }
    }


    @Override
    public void convert(RecyclerHolder holder, Moment item, int position, int viewType) {
        switch (viewType) {
            case TYPE_COMMON_TEXT:
                inflaterCommonText(holder, item);
                break;
            case TYPE_COMMON_IMAGE:
                inflaterCommonImage(holder, item);
                break;
            default:
        }
    }

    /**
     * 纯Text的布局
     *
     * @param holder
     * @param item
     */
    private void inflaterCommonText(final RecyclerHolder holder, final Moment item) {
        CircleImageView avatar = holder.getView(R.id.moment_item_avatar);
        Bitmap bitmap = BitmapCore.getMemoryBitmap(item.getCreatedPortrait());
        if (bitmap != null) {
            avatar.setImageBitmap(bitmap);
        } else {
            new BitmapCore.Builder().view(avatar).loadResId(R.mipmap.ic_launcher)
                    .url(item.getCreatedPortrait()).doTask();
        }
        holder.setText(R.id.moment_item_name, item.getCreatedNickname());
        holder.setText(R.id.moment_item_content, item.getContent());
        holder.setText(R.id.moment_item_date, DateUtils.friendlyTime(item.getCreatedTime()));

        LocalPoem localPoem = SQLdm.sLocalPoemMap.get(item.getPoemId() + "");
        String poemName = "";
        if (localPoem != null) {
            poemName = localPoem.getName();
        }
        if (!TextUtils.isEmpty(poemName)) {
            holder.setText(R.id.moment_item_from, String.format("来自《%s》", poemName));
        } else {
            holder.setText(R.id.moment_item_from, "");
        }
    }

    /**
     * 文本图片或单图片的布局
     *
     * @param holder
     * @param item
     */
    private void inflaterCommonImage(RecyclerHolder holder, Moment item) {
        CircleImageView avatar = holder.getView(R.id.moment_item_avatar);
        Bitmap avatarBitmap = BitmapCore.getMemoryBitmap(item.getCreatedPortrait());
        if (avatarBitmap != null) {
            avatar.setImageBitmap(avatarBitmap);
        } else {
            new BitmapCore.Builder().view(avatar).loadResId(R.mipmap.ic_launcher)
                    .url(item.getCreatedPortrait()).doTask();
        }

        ImageView image = holder.getView(R.id.moment_item_image);
        Bitmap imageBitmap = BitmapCore.getMemoryBitmap(item.getImg());
        if (imageBitmap != null) {
            image.setImageBitmap(imageBitmap);
        } else {
            new BitmapCore.Builder().view(image).loadResId(R.mipmap.ic_launcher)
                    .url(item.getImg()).doTask();
        }

        holder.setText(R.id.moment_item_name, item.getCreatedNickname());
        holder.setText(R.id.moment_item_content, item.getContent());
        holder.setText(R.id.moment_item_date, DateUtils.friendlyTime(item.getCreatedTime()));
    }
}
