package com.lzx.zhihudaily.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.eitity.Stories;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by xian on 2016/10/22.
 */

public class ThemeListAdapter extends SuperAdapter<Stories> {

    private int ITEM_TEXT = 0;
    private int ITEM_IMAGE = 1;

    public ThemeListAdapter(Context context, List<Stories> items, IMulItemViewType<Stories> mulItemViewType) {
        super(context, items, mulItemViewType);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Stories item) {
        TextView item_title_view;
        ImageView item_image_view;

        if (viewType == ITEM_TEXT) {
            item_title_view = holder.findViewById(R.id.item_title_view);
        } else {
            item_title_view = holder.findViewById(R.id.item_title_view);
            item_image_view = holder.findViewById(R.id.item_image_view);
            Glide.with(getContext())
                    .load(item.getImages().get(0))
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.color.image_default_bg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(item_image_view);
        }
        item_title_view.setText(item.getTitle());
    }

    @Override
    protected IMulItemViewType<Stories> offerMultiItemViewType() {
        return new IMulItemViewType<Stories>() {
            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position, Stories stories) {
                if (stories.getImages() == null || stories.getImages().size() == 0) {

                    return ITEM_TEXT;
                } else {
                    return ITEM_IMAGE;
                }
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == ITEM_TEXT) {
                    return R.layout.item_news_text;
                } else {
                    return R.layout.item_news;
                }
            }
        };
    }
}
