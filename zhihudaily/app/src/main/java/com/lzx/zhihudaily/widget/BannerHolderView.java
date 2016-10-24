package com.lzx.zhihudaily.widget;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.lzx.zhihudaily.eitity.TopStories;

/**
 * Created by lzx on 2016/10/21.
 * 功能：
 */

public class BannerHolderView implements Holder<TopStories> {

    private BannerView mBannerView;

    @Override
    public View createView(Context context) {
        mBannerView = new BannerView(context);
        return mBannerView;
    }

    @Override
    public void UpdateUI(Context context, int position, TopStories data) {
        mBannerView.setImageUrl(data.getImage());
        mBannerView.setBannerTitle(data.getTitle());
    }
}
