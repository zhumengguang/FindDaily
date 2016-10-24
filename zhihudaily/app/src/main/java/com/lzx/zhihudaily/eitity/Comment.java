package com.lzx.zhihudaily.eitity;

/**
 * Created by lzx on 2016/10/21.
 * 功能：评论
 */

public class Comment {
    public String author;
    public String content;
    public String avatar; //用户头像图片的地址
    public long time;
    public String id; // 评论者的唯一标识符
    public int likes; //评论所获『赞』的数量

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getAvatar() {
        return avatar;
    }

    public long getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public int getLikes() {
        return likes;
    }
}
