package com.lzx.zhihudaily;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by lzx on 2016/10/21.
 * 功能：
 */

public class ZhihuDailyApp extends Application {
    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("ZhihuDaily.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static Context getContext() {
        return mAppContext;
    }

}
