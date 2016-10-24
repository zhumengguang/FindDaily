package com.lzx.zhihudaily.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.eitity.Editor;
import com.lzx.zhihudaily.widget.CircleImageView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by xian on 2016/10/22.
 */

public class ThemeEditorAdapter extends SuperAdapter<Editor> {

    public ThemeEditorAdapter(Context context, List<Editor> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Editor item) {
        CircleImageView circleImageView = holder.findViewById(R.id.editor_head_view);
        Glide.with(getContext())
                .load(item.getAvatar())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.color.image_default_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleImageView);
    }
}
