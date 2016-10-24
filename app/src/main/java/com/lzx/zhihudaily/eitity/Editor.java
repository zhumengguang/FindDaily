package com.lzx.zhihudaily.eitity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lzx on 2016/10/21.
 * 功能：主题日报的编辑
 */

public class Editor implements Parcelable {
    public String url;  //主编的知乎用户主页
    public String bio;  //主编的个人简介
    public String id;  // 数据库中的唯一表示符
    public String avatar;
    public String name;

    public String getUrl() {
        return url;
    }

    public String getBio() {
        return bio;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.bio);
        dest.writeString(this.id);
        dest.writeString(this.avatar);
        dest.writeString(this.name);
    }

    public Editor() {
    }

    protected Editor(Parcel in) {
        this.url = in.readString();
        this.bio = in.readString();
        this.id = in.readString();
        this.avatar = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Editor> CREATOR = new Parcelable.Creator<Editor>() {
        @Override
        public Editor createFromParcel(Parcel source) {
            return new Editor(source);
        }

        @Override
        public Editor[] newArray(int size) {
            return new Editor[size];
        }
    };
}
