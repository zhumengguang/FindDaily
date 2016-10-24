package com.lzx.zhihudaily.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by lzx on 2016/10/12.
 * 功能：监听滑动到底部
 */

public class OnScrollUtil {
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
