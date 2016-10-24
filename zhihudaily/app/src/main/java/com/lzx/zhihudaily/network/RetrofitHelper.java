package com.lzx.zhihudaily.network;


import com.lzx.zhihudaily.ZhihuDailyApp;
import com.lzx.zhihudaily.network.api.ZhihuDailyService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * Retrofit帮助类
 */
public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient;

    public static final String API_BASE_URL = "http://news-at.zhihu.com/";

    static {
        initOkHttpClient();
    }

    public static ZhihuDailyService getZhihuDailyService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ZhihuDailyService.class);
    }

    /**
     * 初始化OKHttpClient
     * 设置缓存
     * 设置超时时间
     * 设置打印日志
     * 设置UA拦截器
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(ZhihuDailyApp.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            //  .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            //    .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }


//    /**
//     * 添加UA拦截器
//     * B站请求API文档需要加上UA
//     */
//    private static class UserAgentInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            Request originalRequest = chain.request();
//            Request requestWithUserAgent = originalRequest.newBuilder()
//                    .removeHeader("User-Agent")
//                    .addHeader("User-Agent", COMMON_UA_STR)
//                    .build();
//            return chain.proceed(requestWithUserAgent);
//        }
//    }
}
