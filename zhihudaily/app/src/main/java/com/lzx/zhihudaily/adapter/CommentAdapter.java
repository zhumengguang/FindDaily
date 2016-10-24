package com.lzx.zhihudaily.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.eitity.Comment;
import com.lzx.zhihudaily.utils.DateUtils;
import com.lzx.zhihudaily.widget.CircleImageView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by xian on 2016/10/23.
 */

public class CommentAdapter extends SuperAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Comment item) {
        CircleImageView circleImageView = holder.findViewById(R.id.user_head_view);
        TextView user_name_view = holder.findViewById(R.id.user_name_view);
        TextView comment_vote_view = holder.findViewById(R.id.comment_vote_view);
        TextView text_content = holder.findViewById(R.id.text_content);
        TextView comment_time = holder.findViewById(R.id.comment_time);
        Glide.with(getContext())
                .load(item.getAvatar())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.menu_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleImageView);
        user_name_view.setText(item.getAuthor());
        comment_vote_view.setText(String.valueOf(item.getLikes()));
        text_content.setText(item.getContent());
        comment_time.setText(DateUtils.getTime(item.getTime()));
    }
}
