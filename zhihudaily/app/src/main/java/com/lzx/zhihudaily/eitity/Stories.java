package com.lzx.zhihudaily.eitity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能： 当日新闻
 */

public class Stories implements Parcelable {
    public String title;
    @SerializedName("ga_prefix")
    public String gaPrefix;  //供 Google Analytics 使用
    public List<String> images;
    public boolean multipic; //消息是否包含多张图片（仅出现在包含多图的新闻中）
    public int type;
    public String id; //url 与 share_url 中最后的数字（应为内容的 id）
    public String date; //日期

    public String getTitle() {
        return title;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public List<String> getImages() {
        return images;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public int getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.gaPrefix);
        dest.writeStringList(this.images);
        dest.writeByte(this.multipic ? (byte) 1 : (byte) 0);
        dest.writeInt(this.type);
        dest.writeString(this.id);
        dest.writeString(this.date);
    }

    public Stories() {
    }

    protected Stories(Parcel in) {
        this.title = in.readString();
        this.gaPrefix = in.readString();
        this.images = in.createStringArrayList();
        this.multipic = in.readByte() != 0;
        this.type = in.readInt();
        this.id = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Stories> CREATOR = new Parcelable.Creator<Stories>() {
        @Override
        public Stories createFromParcel(Parcel source) {
            return new Stories(source);
        }

        @Override
        public Stories[] newArray(int size) {
            return new Stories[size];
        }
    };
}
