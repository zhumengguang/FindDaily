package com.lzx.zhihudaily.network.api;

import com.lzx.zhihudaily.eitity.AppVersion;
import com.lzx.zhihudaily.eitity.HotNews;
import com.lzx.zhihudaily.eitity.LatestNews;
import com.lzx.zhihudaily.eitity.LongOrShortComment;
import com.lzx.zhihudaily.eitity.NewsDetail;
import com.lzx.zhihudaily.eitity.Recommender;
import com.lzx.zhihudaily.eitity.Sections;
import com.lzx.zhihudaily.eitity.SectionsDetail;
import com.lzx.zhihudaily.eitity.StartImage;
import com.lzx.zhihudaily.eitity.StoryExtra;
import com.lzx.zhihudaily.eitity.ThemeDailyDetail;
import com.lzx.zhihudaily.eitity.ThemeInfo;
import com.lzx.zhihudaily.network.RetrofitHelper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lzx on 2016/10/21.
 * 功能：
 */

public interface ZhihuDailyService {
    /**
     * 启动界面图像获取
     */
    @GET("api/4/start-image/1080*1776")
    Observable<StartImage> getStartImageInfo();

    /**
     * 软件版本查询
     */
    @GET("api/4/version/android/2.3.0")
    Observable<AppVersion> getAppVersionInfo();

    /**
     * 最新消息
     */
    @GET("api/4/news/latest")
    Observable<LatestNews> getLatestNewsInfo();

    /**
     * 消息内容获取与离线下载
     * id是最新消息的id
     */
    @GET("api/4/news/{id}")
    Observable<NewsDetail> getNewsDetailInfo(@Path("id") String id);

    /**
     * 过往消息
     * Eg:http://news.at.zhihu.com/api/4/news/before/20131119
     * 若果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
     * 若 before 后数字小于 20130520 ，只会接收到空消息
     */
    @GET("api/4/news/before/{id}")
    Observable<LatestNews> getBeforeNewsInfo(@Path("id") String id);

    /**
     * 新闻额外信息
     * id是新闻的id
     */
    @GET("api/4/story-extra/{id}")
    Observable<StoryExtra> getExtraNewsInfo(@Path("id") String id);

    /**
     * 新闻对应长评论查看
     * 使用在 最新消息 中获得的 id
     */
    @GET("api/4/story/{id}/long-comments")
    Observable<LongOrShortComment> getLongCommentsInfo(@Path("id") String id);

    /**
     * 新闻对应短评论查看
     * 使用在 最新消息 中获得的 id
     */
    @GET("api/4/story/{id}/short-comments")
    Observable<LongOrShortComment> getShortCommentsInfo(@Path("id") String id);

    /**
     * 主题日报列表查看
     */
    @GET("api/4/themes")
    Observable<ThemeInfo> getThemeInfo();

    /**
     * 主题日报内容查看
     * 使用在 主题日报列表查看 中获得需要查看的主题日报的 id
     */
    @GET("api/4/theme/{id}")
    Observable<ThemeDailyDetail> getThemeDetailInfo(@Path("id") String id);

    /**
     * 热门消息
     * 此 API 仍可访问，但是其内容未出现在最新的『知乎日报』 App 中。
     */
    @GET("api/3/news/hot")
    Observable<HotNews> getHotNews();

    /**
     * 栏目总览
     * 此 API 仍可访问，但是其内容未出现在最新的『知乎日报』 App 中。
     */
    @GET("api/3/sections")
    Observable<Sections> getSections();

    /**
     * 栏目具体消息查看
     * 使用 『栏目总览』中相应栏目的 id
     * 此 API 仍可访问，但是其内容未出现在最新的『知乎日报』 App 中。
     */
    @GET("api/3/section/{id}")
    Observable<SectionsDetail> getSectionsDetail(@Path("id") String id);

    /**
     * 查看新闻的推荐者
     * 新闻id
     */
    @GET("api/4/story/{id}/recommenders")
    Observable<Recommender> getRecommenders(@Path("id") String id);

    /**
     * 获取某个专栏之前的新闻
     * 专栏id填入到 #{section id}, 将时间戳填入到#{timestamp}
     */
    @GET("api/4/section/{section_id}/before/{timestamp}")
    Observable<SectionsDetail> getBeforeSection(@Path("section_id") String section_id, @Path("timestamp") long timestamp);

    /**
     * 查看editor的主页
     */
    String editorUrl = RetrofitHelper.API_BASE_URL + "4/editor/{id}/profile-page/android";


}
