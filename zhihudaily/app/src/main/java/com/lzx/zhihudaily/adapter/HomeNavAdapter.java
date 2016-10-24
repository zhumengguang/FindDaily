package com.lzx.zhihudaily.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.eitity.ThemeDaily;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能：侧滑菜单
 */

public class HomeNavAdapter extends SuperAdapter<ThemeDaily> {

    public HomeNavAdapter(Context context, List<ThemeDaily> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ThemeDaily item) {
        ImageView icon_home_view = holder.findViewById(R.id.icon_home_view);
        ImageView icon_add = holder.findViewById(R.id.icon_add);
        TextView menu_item_view = holder.findViewById(R.id.menu_item_view);
        LinearLayout menu_layout_view = holder.findViewById(R.id.menu_layout_view);

        if (layoutPosition == 0) {
            icon_home_view.setVisibility(View.VISIBLE);
            icon_add.setVisibility(View.GONE);
            menu_item_view.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
            menu_item_view.setText(getContext().getString(R.string.menu_index));
        } else {
            icon_home_view.setVisibility(View.GONE);
            icon_add.setVisibility(View.VISIBLE);
            menu_item_view.setTextColor(Color.BLACK);
            menu_item_view.setText(item.getName());
        }
        if (item.isSelect()) {
            menu_layout_view.setBackgroundColor(Color.parseColor("#F0F0F0"));
        } else {
            menu_layout_view.setBackgroundColor(Color.WHITE);
        }

    }

}
