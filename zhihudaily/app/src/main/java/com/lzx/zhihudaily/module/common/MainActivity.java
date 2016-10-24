package com.lzx.zhihudaily.module.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.HomeNavAdapter;
import com.lzx.zhihudaily.base.BaseActivity;
import com.lzx.zhihudaily.eitity.ThemeDaily;
import com.lzx.zhihudaily.eitity.ThemeInfo;
import com.lzx.zhihudaily.module.collect.CollectActivity;
import com.lzx.zhihudaily.module.home.HomePageFragment;
import com.lzx.zhihudaily.module.home.ThemeDailyFragment;
import com.lzx.zhihudaily.network.RetrofitHelper;
import com.lzx.zhihudaily.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private TextView collect_view;
    private HomeNavAdapter mNavAdapter;
    private Fragment[] fragments;
    private ImageView user_head_view;
    private long exitTime;
    private int currentTabIndex;
    private int index;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        user_head_view = (ImageView) findViewById(R.id.user_head_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        collect_view = (TextView) findViewById(R.id.collect_view);
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initData() {
        //初始化侧滑菜单
        initNavigationView();
    }

    private void initNavigationView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mNavAdapter = new HomeNavAdapter(MainActivity.this, new ArrayList<>(), R.layout.item_navigation);
        mRecyclerView.setAdapter(mNavAdapter);
        RetrofitHelper.getZhihuDailyService().getThemeInfo()
                .compose(bindToLifecycle())
                .flatMap(new Func1<ThemeInfo, Observable<List<ThemeDaily>>>() {

                    @Override
                    public Observable<List<ThemeDaily>> call(ThemeInfo themeInfo) {
                        List<ThemeDaily> list = themeInfo.getOthers();
                        ThemeDaily themeDaily = new ThemeDaily(true, getString(R.string.menu_index));
                        list.add(0, themeDaily);
                        return Observable.just(list);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(themeDailies -> {
                    mNavAdapter.addAll(themeDailies);
                    //初始化Fragment
                    initFragments();
                });
        mNavAdapter.setOnItemClickListener((itemView, viewType, position) -> {
            changeFragment(position);
        });
    }

    private void changeFragment(int position) {
        List<ThemeDaily> themeDailies = new ArrayList<>();
        themeDailies.addAll(mNavAdapter.getData());
        for (int i = 0; i < themeDailies.size(); i++) {
            themeDailies.get(i).setSelect(position == i);
        }
        mNavAdapter.notifyItemChanged(position);
        mNavAdapter.notifyDataSetChanged();
        toggleDrawer();
        //切换Fragment
        changeFragmentIndex(position);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = intent.getIntExtra("position", 0);
        changeFragment(position);
    }

    private void initFragments() {
        fragments = new Fragment[mNavAdapter.getCount()];
        for (int i = 0; i < fragments.length; i++) {
            if (i == 0) {
                fragments[i] = HomePageFragment.newInstance(mNavAdapter.getData().get(i));
            } else {
                fragments[i] = ThemeDailyFragment.newInstance(mNavAdapter.getData().get(i), i);
            }
        }
        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragments[0])
                .show(fragments[0]).commit();
    }

    /**
     * Fragment切换
     */
    private void switchFragment() {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
//            trx.hide(fragments[currentTabIndex]);
//            if (!fragments[index].isAdded()) {
//                trx.add(R.id.container, fragments[index]);
//            }
//            trx.show(fragments[index]).commit();
            trx.replace(R.id.container,fragments[index]).addToBackStack(null).commit(); //这里使用替换，不保留状态
        }
        currentTabIndex = index;
    }


    /**
     * 切换Fragment的下标
     *
     * @param currentIndex
     */
    private void changeFragmentIndex(int currentIndex) {
        index = currentIndex;
        switchFragment();
    }

    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    /**
     * 监听back键处理DrawerLayout和SearchView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                exitApp();
            }
        }
        return true;
    }

    /**
     * 双击退出App
     */
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void initAction() {
        user_head_view.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent);
            toggleDrawer();
        });
        collect_view.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CollectActivity.class);
            startActivity(intent);
            toggleDrawer();
        });
    }
}
