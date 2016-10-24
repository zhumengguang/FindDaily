package com.lzx.zhihudaily.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.eitity.CollectNews;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lzx on 2016/10/24.
 * 功能：
 */

public class ArticleCollectAdapter extends SuperAdapter<CollectNews> {

    private int ITEM_TEXT = 0;
    private int ITEM_IMAGE = 1;

    public ArticleCollectAdapter(Context context, List<CollectNews> items, IMulItemViewType<CollectNews> mulItemViewType) {
        super(context, items, mulItemViewType);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CollectNews item) {
        TextView item_title_view;
        ImageView item_image_view;

        if (viewType == ITEM_TEXT) {
            item_title_view = holder.findViewById(R.id.item_title_view);
        } else {
            item_title_view = holder.findViewById(R.id.item_title_view);
            item_image_view = holder.findViewById(R.id.item_image_view);
            Glide.with(getContext())
                    .load(item.getNewsImage())
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.color.image_default_bg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(item_image_view);
        }
        item_title_view.setText(item.getNewsTitle());
    }

    @Override
    protected IMulItemViewType<CollectNews> offerMultiItemViewType() {
        return new IMulItemViewType<CollectNews>() {
            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position, CollectNews collectNews) {
                if (TextUtils.isEmpty(collectNews.getNewsImage())) {
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
