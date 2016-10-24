package com.lzx.zhihudaily.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.eitity.Stories;
import com.lzx.zhihudaily.utils.DateUtils;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能：
 */

public class HomePageAdapter extends SuperAdapter<Stories> {

    private int ITEM_WIDTH_DATE = 0;
    private int ITEM_WIDTH_IMAGE = 1;
    private int ITEM_NO_IMAGE = 2;
    private int nowYear;
    private int nowMonth;
    private int nowDay;

    public HomePageAdapter(Context context, List<Stories> items, IMulItemViewType<Stories> mulItemViewType) {
        super(context, items, mulItemViewType);
        Calendar c = Calendar.getInstance();
        nowYear = c.get(Calendar.YEAR);
        nowMonth = c.get(Calendar.MONTH) + 1;  //月份是从0开始的
        nowDay = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Stories item) {

        TextView item_title_view, data_view;
        ImageView item_image_view;

        if (viewType == ITEM_WIDTH_DATE) {
            data_view = holder.findViewById(R.id.data_view);
            item_title_view = holder.findViewById(R.id.item_title_view);
            item_image_view = holder.findViewById(R.id.item_image_view);

            if ((nowYear + "" + nowMonth + "" + nowDay).equals(item.getDate())) {
                data_view.setText(getContext().getString(R.string.today_hot_new));
            } else {
                data_view.setText(DateUtils.formatDate(item.getDate()) + " " + DateUtils.getDayOfweek(item.getDate()));
            }
            initItem(item_title_view, item_image_view, item.getTitle(), item.getImages().get(0));
        } else {
            item_title_view = holder.findViewById(R.id.item_title_view);
            item_image_view = holder.findViewById(R.id.item_image_view);
            initItem(item_title_view, item_image_view, item.getTitle(), item.getImages().get(0));
        }
    }

    private void initItem(TextView titleView, ImageView imageView, String title, String url) {
        titleView.setText(title);
        Glide.with(getContext())
                .load(url)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.color.image_default_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
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
                if (position == 0) {
                    return ITEM_WIDTH_DATE;
                } else {
                    int index = position - 1;
                    if (!getData().get(index).getDate().equals(stories.getDate())) {
                        return ITEM_WIDTH_DATE;
                    } else {
                        return ITEM_WIDTH_IMAGE;
                    }
                }
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == ITEM_WIDTH_DATE)
                    return R.layout.item_news_width_date;
                else
                    return R.layout.item_news;
            }
        };
    }
}
