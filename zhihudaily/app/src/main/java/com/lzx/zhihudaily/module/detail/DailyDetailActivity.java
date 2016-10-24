package com.lzx.zhihudaily.module.detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.base.BaseActivity;
import com.lzx.zhihudaily.eitity.CollectNews;
import com.lzx.zhihudaily.eitity.NewsDetail;
import com.lzx.zhihudaily.eitity.StoryExtra;
import com.lzx.zhihudaily.module.comment.CommentActivity;
import com.lzx.zhihudaily.network.RetrofitHelper;
import com.lzx.zhihudaily.utils.HtmlUtil;
import com.lzx.zhihudaily.utils.SnackbarUtil;
import com.lzx.zhihudaily.widget.ParallaxScrollView;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by xian on 2016/10/22.
 */

public class DailyDetailActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView detail_image, collect_view, share_view;
    private TextView detail_title, image_source, praise_view, comment_view;
    private ParallaxScrollView mParallaxScrollView;
    private RelativeLayout top_layout;
    private WebView mWebView;

    private String newsId, smallImage;
    private StoryExtra storyExtra;
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//Bar高度
    private NewsDetail newsDetail;
    private Intent intent;
    private RealmAsyncTask transaction;
    private Realm mRealm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dailydetail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        detail_image = (ImageView) findViewById(R.id.detail_image);
        collect_view = (ImageView) findViewById(R.id.collect_view);
        share_view = (ImageView) findViewById(R.id.share_view);
        detail_title = (TextView) findViewById(R.id.detail_title);
        image_source = (TextView) findViewById(R.id.image_source);
        praise_view = (TextView) findViewById(R.id.praise_view);
        comment_view = (TextView) findViewById(R.id.comment_view);
        mParallaxScrollView = (ParallaxScrollView) findViewById(R.id.scrollView);
        top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        mWebView = (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(view -> finish());

        newsId = getIntent().getStringExtra("newsId");
        smallImage = getIntent().getStringExtra("smallImage");

        RetrofitHelper.getZhihuDailyService().getExtraNewsInfo(newsId)
                .compose(bindToLifecycle())
                .flatMap(new Func1<StoryExtra, Observable<StoryExtra>>() {

                    @Override
                    public Observable<StoryExtra> call(StoryExtra storyExtra) {
                        return Observable.just(storyExtra);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initToolBarData);
    }

    private void initToolBarData(StoryExtra storyExtra) {
        this.storyExtra = storyExtra;
        comment_view.setText(storyExtra.getComments());
        praise_view.setText(storyExtra.getPopularity());
        if (hasCollNews()) {
            collect_view.setImageResource(R.drawable.collected);
        } else {
            collect_view.setImageResource(R.drawable.collect);
        }
    }

    @Override
    protected void initData() {
        mRealm = Realm.getDefaultInstance();
        headerHeight = getResources().getDimension(R.dimen.top_layout_height);
        mToolbar.post(() -> minHeaderHeight = mToolbar.getMeasuredHeight());

        RetrofitHelper.getZhihuDailyService().getNewsDetailInfo(newsId)
                .compose(bindToLifecycle())
                .flatMap(new Func1<NewsDetail, Observable<NewsDetail>>() {

                    @Override
                    public Observable<NewsDetail> call(NewsDetail newsDetail) {
                        return Observable.just(newsDetail);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finishTask);
    }

    private void finishTask(NewsDetail newsDetail) {
        this.newsDetail = newsDetail;
        if (TextUtils.isEmpty(newsDetail.getImage())) {
            top_layout.setVisibility(View.GONE);
        } else {
            top_layout.setVisibility(View.VISIBLE);
            Glide.with(DailyDetailActivity.this)
                    .load(newsDetail.getImage())
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.color.image_default_bg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(detail_image);
        }
        detail_title.setText(newsDetail.getTitle());
        image_source.setText(newsDetail.getImageSource());
        String htmlData = HtmlUtil.createHtmlData(newsDetail);
        mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    protected void initAction() {
        mParallaxScrollView.setScrollViewListener((scrollView, x, y, oldx, oldy) -> {
            top_layout.scrollTo(x, -y / 3);
        });
        comment_view.setOnClickListener(this);
        share_view.setOnClickListener(this);
        collect_view.setOnClickListener(this);
        praise_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_view:
                intent = new Intent(DailyDetailActivity.this, CommentActivity.class);
                intent.putExtra("StoryExtra", storyExtra);
                intent.putExtra("newsId", newsId);
                startActivity(intent);
                break;
            case R.id.share_view:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from) + newsDetail.getTitle() + "，http://daily.zhihu.com/story/" + newsDetail.getId());
                startActivity(Intent.createChooser(intent, newsDetail.getTitle()));
                break;
            case R.id.collect_view:
                if (hasCollNews()) {
                    cancelCollNews();  //取消收藏
                    collect_view.setImageResource(R.drawable.collect);
                } else {
                    collNews(); //收藏
                    collect_view.setImageResource(R.drawable.collected);
                }
                break;
            case R.id.praise_view:
                Drawable drawable= getResources().getDrawable(R.drawable.praised);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                praise_view.setCompoundDrawables(drawable,null,null,null);
                SnackbarUtil.showMessage(praise_view,"点赞成功");
                break;
        }
    }

    /**
     * 是否已经收藏
     */
    private boolean hasCollNews() {
        RealmResults<CollectNews> results = mRealm.where(CollectNews.class).equalTo("newsId", newsId).findAll();
        if (results.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 取消收藏
     */
    private void cancelCollNews() {
        final RealmResults<CollectNews> results = mRealm.where(CollectNews.class).equalTo("newsId", newsId).findAll();
        mRealm.executeTransaction(realm -> results.deleteAllFromRealm());
    }

    /**
     * 收藏
     */
    private void collNews() {
        CollectNews collectNews = new CollectNews(newsId, newsDetail.getTitle(), smallImage);
        transaction = mRealm.executeTransactionAsync(bgRealm -> {
            CollectNews collNew = bgRealm.copyToRealm(collectNews);
        }, error -> {
            SnackbarUtil.showMessage(collect_view, "哎呀，收藏失败了！");
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mRealm.isClosed())
            mRealm.close();
    }


}
