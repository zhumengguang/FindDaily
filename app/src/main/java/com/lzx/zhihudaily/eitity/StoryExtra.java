package com.lzx.zhihudaily.eitity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lzx on 2016/10/21.
 * 功能：新闻额外信息
 */

public class StoryExtra implements Parcelable {
    public String long_comments; // 长评论总数
    public String popularity; //点赞总数
    public String short_comments; //短评论总数
    public String comments; // 评论总数

    public String getLong_comments() {
        return long_comments;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getShort_comments() {
        return short_comments;
    }

    public String getComments() {
        return comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.long_comments);
        dest.writeString(this.popularity);
        dest.writeString(this.short_comments);
        dest.writeString(this.comments);
    }

    public StoryExtra() {
    }

    protected StoryExtra(Parcel in) {
        this.long_comments = in.readString();
        this.popularity = in.readString();
        this.short_comments = in.readString();
        this.comments = in.readString();
    }

    public static final Parcelable.Creator<StoryExtra> CREATOR = new Parcelable.Creator<StoryExtra>() {
        @Override
        public StoryExtra createFromParcel(Parcel source) {
            return new StoryExtra(source);
        }

        @Override
        public StoryExtra[] newArray(int size) {
            return new StoryExtra[size];
        }
    };
}
