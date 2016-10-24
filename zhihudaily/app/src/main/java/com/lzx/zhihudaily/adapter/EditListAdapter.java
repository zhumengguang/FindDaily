package com.lzx.zhihudaily.adapter;

import android.content.Context;
import android.widget.TextView;

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

public class EditListAdapter extends SuperAdapter<Editor> {

    public EditListAdapter(Context context, List<Editor> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Editor item) {
        CircleImageView circleImageView = holder.findViewById(R.id.editor_head_view);
        TextView editor_name_view = holder.findViewById(R.id.editor_name_view);
        TextView editor_bio_view = holder.findViewById(R.id.editor_bio_view);
        Glide.with(getContext())
                .load(item.getAvatar())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.color.image_default_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleImageView);
        editor_name_view.setText(item.getName());
        editor_bio_view.setText(item.getBio());
    }
}
