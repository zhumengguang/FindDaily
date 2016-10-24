package com.lzx.zhihudaily.module.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.ThemeEditorAdapter;
import com.lzx.zhihudaily.adapter.ThemeListAdapter;
import com.lzx.zhihudaily.base.BaseFragment;
import com.lzx.zhihudaily.eitity.ThemeDaily;
import com.lzx.zhihudaily.eitity.ThemeDailyDetail;
import com.lzx.zhihudaily.module.common.MainActivity;
import com.lzx.zhihudaily.module.detail.DailyDetailActivity;
import com.lzx.zhihudaily.module.editor.EditorListActivity;
import com.lzx.zhihudaily.network.RetrofitHelper;
import com.lzx.zhihudaily.utils.ToastUtil;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xian on 2016/10/22.
 * 主题日报fragment
 */

public class ThemeDailyFragment extends BaseFragment {

    private ThemeDaily mThemeDaily;
    private MenuItem collectItem;
    private int position;

    public static ThemeDailyFragment newInstance(ThemeDaily mThemeDaily, int position) {
        ThemeDailyFragment dailyFragment = new ThemeDailyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("theme_daily", mThemeDaily);
        bundle.putInt("position", position);
        dailyFragment.setArguments(bundle);
        return dailyFragment;
    }

    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView, head_recycler_view;
    private ThemeListAdapter mThemeListAdapter;
    private ThemeEditorAdapter mThemeEditorAdapter;
    private View mHeaderView;
    private TextView theme_title;
    private ImageView theme_image;
    private LinearLayoutManager mLayoutManager;
    private RealmAsyncTask transaction;
    private Realm mRealm;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setHasOptionsMenu(true);
        mRealm = Realm.getDefaultInstance();

        if (getArguments() == null)
            return;
        mThemeDaily = getArguments().getParcelable("theme_daily");
        position = getArguments().getInt("position");
        mThemeDaily.setPosition(position);

        mHeaderView = View.inflate(getActivity(), R.layout.layout_theme_daily_head, null);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        theme_image = (ImageView) mHeaderView.findViewById(R.id.theme_image);
        theme_title = (TextView) mHeaderView.findViewById(R.id.theme_title);
        head_recycler_view = (RecyclerView) mHeaderView.findViewById(R.id.head_recycler_view);


        initToolBar();
        initRecyclerView();
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initToolBar() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(mThemeDaily.getName());
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.bar_menu);
        mToolbar.setNavigationOnClickListener(view -> ((MainActivity) getActivity()).toggleDrawer());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_theme, menu);
        collectItem = menu.findItem(R.id.menu_collect);

        if (hasCollTheme()) {
            collectItem.setIcon(getResources().getDrawable(R.drawable.theme_remove));
        } else {
            collectItem.setIcon(getResources().getDrawable(R.drawable.theme_add));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_collect) {
            if (hasCollTheme()) {
                cancelCollTheme(); //取消收藏
                collectItem.setIcon(getResources().getDrawable(R.drawable.theme_add));
            } else {
                collTheme(); //收藏
                collectItem.setIcon(getResources().getDrawable(R.drawable.theme_remove));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 是否已经收藏
     */
    private boolean hasCollTheme() {
        RealmResults<ThemeDaily> results = mRealm.where(ThemeDaily.class).equalTo("id", mThemeDaily.getId()).findAll();
        if (results.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 取消收藏
     */
    private void cancelCollTheme() {
        final RealmResults<ThemeDaily> results = mRealm.where(ThemeDaily.class).equalTo("id", mThemeDaily.getId()).findAll();
        mRealm.executeTransaction(realm -> results.deleteAllFromRealm());
    }

    /**
     * 收藏
     */
    private void collTheme() {
        transaction = mRealm.executeTransactionAsync(bgRealm -> {
            ThemeDaily theme = bgRealm.copyToRealm(mThemeDaily);
        }, error -> {
            ToastUtil.showToast("哎呀，收藏失败了！");
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mRealm.isClosed())
            mRealm.close();
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mThemeListAdapter = new ThemeListAdapter(getActivity(), new ArrayList<>(), null);
        mRecyclerView.setAdapter(mThemeListAdapter);
        mThemeListAdapter.addHeaderView(mHeaderView);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        head_recycler_view.setLayoutManager(manager);
        mThemeEditorAdapter = new ThemeEditorAdapter(getActivity(), new ArrayList<>(), R.layout.item_theme_editor);
        head_recycler_view.setAdapter(mThemeEditorAdapter);

        mThemeListAdapter.setOnItemClickListener((itemView, viewType, position) -> {
            String newsId = mThemeListAdapter.getItem(position - 1).getId();
            String smallImage;
            if (mThemeListAdapter.getItem(position - 1).getImages() == null || mThemeListAdapter.getItem(position - 1).getImages().size() == 0) {
                smallImage = "";
            } else {
                smallImage = mThemeListAdapter.getItem(position - 1).getImages().get(0);
            }
            gotoNewsDetailActivity(newsId, smallImage);
        });
    }

    private void refresh() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadThemeList);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadThemeList();
        });
    }

    /**
     * 加载列表
     */
    private void loadThemeList() {
        RetrofitHelper.getZhihuDailyService().getThemeDetailInfo(mThemeDaily.getId())
                .compose(bindToLifecycle())
                .flatMap(new Func1<ThemeDailyDetail, Observable<ThemeDailyDetail>>() {

                    @Override
                    public Observable<ThemeDailyDetail> call(ThemeDailyDetail themeDailyDetail) {
                        return Observable.just(themeDailyDetail);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    mThemeListAdapter.clear();
                    mThemeEditorAdapter.clear();
                })
                .subscribe(themeDailyDetail -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    initHeaderView(themeDailyDetail);
                    mThemeListAdapter.addAll(themeDailyDetail.getStories());
                });
    }

    private void initHeaderView(ThemeDailyDetail detail) {
        Glide.with(getContext())
                .load(detail.getBackground())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.color.image_default_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(theme_image);
        theme_title.setText(detail.getDescription());
        mThemeEditorAdapter.addAll(detail.getEditors());
        mThemeEditorAdapter.setOnItemClickListener((itemView, viewType, position) -> {
            Intent intent = new Intent(getActivity(), EditorListActivity.class);
            intent.putParcelableArrayListExtra("editor_list", (ArrayList<? extends Parcelable>) detail.getEditors());
            getActivity().startActivity(intent);
        });
    }

    private void gotoNewsDetailActivity(String newsId, String smallImage) {
        Intent intent = new Intent(getActivity(), DailyDetailActivity.class);
        intent.putExtra("newsId", newsId);
        intent.putExtra("smallImage", smallImage);
        getActivity().startActivity(intent);
    }

    @Override
    protected void lazyLoad() {

    }
}
