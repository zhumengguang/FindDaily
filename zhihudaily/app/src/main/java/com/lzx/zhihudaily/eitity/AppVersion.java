package com.lzx.zhihudaily.eitity;

/**
 * Created by lzx on 2016/10/21.
 * 功能：软件版本
 */

public class AppVersion {
    public String status;  // 0 代表软件为最新版本，1 代表软件需要升级
    public String msg; // 仅出现在软件需要升级的情形下，提示用户升级软件的对话框中显示的消息
    public String url;
    public String latest; //软件最新版本的版本号（数字的第二段会比最新的版本号低 1）
}
