package com.lzx.zhihudaily.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.eitity.ThemeDaily;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lzx on 2016/10/24.
 * 功能：
 */

public class ThemeDailyCollectAdapter extends SuperAdapter<ThemeDaily> {

    public ThemeDailyCollectAdapter(Context context, List<ThemeDaily> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ThemeDaily item) {
        ImageView item_image_view = holder.findViewById(R.id.item_image_view);
        TextView item_title_view = holder.findViewById(R.id.item_title_view);
        TextView item_desc_view = holder.findViewById(R.id.item_desc_view);
        Glide.with(getContext())
                .load(item.getThumbnail())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.color.image_default_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(item_image_view);
        item_title_view.setText("[" + item.getName() + "]");
        item_desc_view.setText(item.getDescription());
    }
}
