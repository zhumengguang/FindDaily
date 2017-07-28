package com.lzx.zhihudaily.module.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.HomePageAdapter;
import com.lzx.zhihudaily.base.BaseFragment;
import com.lzx.zhihudaily.eitity.LatestNews;
import com.lzx.zhihudaily.eitity.Stories;
import com.lzx.zhihudaily.eitity.ThemeDaily;
import com.lzx.zhihudaily.eitity.TopStories;
import com.lzx.zhihudaily.listener.AutoLoadOnScrollListener;
import com.lzx.zhihudaily.module.common.AboutAppActivity;
import com.lzx.zhihudaily.module.common.MainActivity;
import com.lzx.zhihudaily.module.detail.DailyDetailActivity;
import com.lzx.zhihudaily.network.RetrofitHelper;
import com.lzx.zhihudaily.widget.BannerHolderView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lzx on 2016/10/21.
 * 功能：
 */

public class HomePageFragment extends BaseFragment {

    private ThemeDaily mThemeDaily;

    public static HomePageFragment newInstance(ThemeDaily mThemeDaily) {
        HomePageFragment homePageFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("theme_daily", mThemeDaily);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomePageAdapter mHomePageAdapter;
    private View mBannerView;
    private ConvenientBanner mBanner;
    private LinearLayoutManager mLayoutManager;
    private AutoLoadOnScrollListener mLoadOnScrollListener;
    private String currentTime;
    private Toolbar mToolbar;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setHasOptionsMenu(true);
        if (getArguments() != null)
            mThemeDaily = getArguments().getParcelable("theme_daily");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBannerView = View.inflate(getActivity(), R.layout.layout_banner, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mBanner = (ConvenientBanner) mBannerView.findViewById(R.id.convenientBanner);

        initToolBar();
        initRecyclerView();
        refresh();
        initAction();
    }

    private void initToolBar() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(getActivity().getString(R.string.menu_index));
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.bar_menu);
        mToolbar.setNavigationOnClickListener(view -> ((MainActivity) getActivity()).toggleDrawer());
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mHomePageAdapter = new HomePageAdapter(getActivity(), new ArrayList<>(), null);
        mRecyclerView.setAdapter(mHomePageAdapter);
        mHomePageAdapter.addHeaderView(mBannerView);
    }

    private void refresh() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadLastDaily);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            if (mThemeDaily.getName().equals(getString(R.string.menu_index))) {
                loadLastDaily();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            startActivity(new Intent(getActivity(), AboutAppActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private LatestNews changeData(LatestNews latestNews) {
        List<Stories> stories = latestNews.getStories();
        for (Stories s : stories) {
            s.setDate(latestNews.getDate());
        }
        latestNews.setStories(stories);
        return latestNews;
    }

    /**
     * 得到最新新闻和banner
     */
    private void loadLastDaily() {
        RetrofitHelper.getZhihuDailyService().getLatestNewsInfo()
                .compose(bindToLifecycle())
                .map(this::changeData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mHomePageAdapter.clear())
                .subscribe(this::finishTask);
    }

    private void finishTask(LatestNews latestNews) {
        currentTime = latestNews.getDate();
        mSwipeRefreshLayout.setRefreshing(false);
        mHomePageAdapter.addAll(latestNews.getStories());
        initBanner(latestNews.getTop_stories());
    }

    private void initBanner(List<TopStories> topStories) {
        mBanner.setPages(new CBViewHolderCreator<BannerHolderView>() {
            @Override
            public BannerHolderView createHolder() {
                return new BannerHolderView();
            }
        }, topStories);
        mBanner.setPageIndicator(new int[]{R.drawable.normal_indicator_color, R.drawable.selected_indicator_color});
        mBanner.startTurning(5000);
        mBanner.setOnItemClickListener(position -> {
            String newsId = topStories.get(position).getId();
            String smallImage = topStories.get(position).getImage();
            gotoNewsDetailActivity(newsId, smallImage);
        });
    }

    private void initAction() {
        mLoadOnScrollListener = new AutoLoadOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreDaily();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        };
        mRecyclerView.addOnScrollListener(mLoadOnScrollListener);
        mHomePageAdapter.setOnItemClickListener((itemView, viewType, position) -> {
            String newsId = mHomePageAdapter.getData().get(position - 1).getId();
            String smallImage = mHomePageAdapter.getData().get(position - 1).getImages().get(0);
            gotoNewsDetailActivity(newsId, smallImage);
        });
    }

    private void gotoNewsDetailActivity(String newsId, String smallImage) {
        Intent intent = new Intent(getActivity(), DailyDetailActivity.class);
        intent.putExtra("newsId", newsId);
        intent.putExtra("smallImage", smallImage);
        getActivity().startActivity(intent);
    }

    private void loadMoreDaily() {
        RetrofitHelper.getZhihuDailyService().getBeforeNewsInfo(currentTime)
                .compose(bindToLifecycle())
                .map(this::changeData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestNews -> {
                    currentTime = latestNews.getDate();
                    mHomePageAdapter.addAll(latestNews.getStories());
                    mLoadOnScrollListener.setLoading(false);
                });
    }

    @Override
    protected void lazyLoad() {

    }
}
