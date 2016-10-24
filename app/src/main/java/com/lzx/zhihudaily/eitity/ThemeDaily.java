package com.lzx.zhihudaily.eitity;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by lzx on 2016/10/21.
 * 功能：日报列表
 */

public class ThemeDaily extends RealmObject implements Parcelable {
    public String color; // 颜色，作用未知
    public String thumbnail; //供显示的图片地址
    public String description; //主题日报的介绍
    public String id; //该主题日报的编号
    public String name; //供显示的主题日报名称
    public boolean isSelect = false; //是否已选择
    public int position;

    public ThemeDaily(boolean isSelect, String name) {
        this.isSelect = isSelect;
        this.name = name;
    }

    public ThemeDaily() {
    }

    public String getColor() {
        return color;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);
        dest.writeString(this.thumbnail);
        dest.writeString(this.description);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.position);
    }

    protected ThemeDaily(Parcel in) {
        this.color = in.readString();
        this.thumbnail = in.readString();
        this.description = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.isSelect = in.readByte() != 0;
        this.position = in.readInt();
    }

    public static final Parcelable.Creator<ThemeDaily> CREATOR = new Parcelable.Creator<ThemeDaily>() {
        @Override
        public ThemeDaily createFromParcel(Parcel source) {
            return new ThemeDaily(source);
        }

        @Override
        public ThemeDaily[] newArray(int size) {
            return new ThemeDaily[size];
        }
    };
}
