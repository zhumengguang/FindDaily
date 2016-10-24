package com.lzx.zhihudaily.module.collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.ThemeDailyCollectAdapter;
import com.lzx.zhihudaily.base.BaseFragment;
import com.lzx.zhihudaily.eitity.ThemeDaily;
import com.lzx.zhihudaily.module.common.MainActivity;
import com.lzx.zhihudaily.utils.LogUtil;
import com.lzx.zhihudaily.widget.EmptyView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lzx on 2016/10/24.
 * 功能：
 */

public class ThemeDailyCollectFragment extends BaseFragment {

    public static ThemeDailyCollectFragment newInstance() {
        return new ThemeDailyCollectFragment();
    }

    private ThemeDailyCollectAdapter mCollectAdapter;
    private RecyclerView mRecyclerView;
    private EmptyView mEmptyView;
    private Realm mRealm;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_collect;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mRealm = Realm.getDefaultInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEmptyView = (EmptyView) findViewById(R.id.empty_view);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        RealmResults<ThemeDaily> results = mRealm.where(ThemeDaily.class).findAll();
        mCollectAdapter = new ThemeDailyCollectAdapter(getActivity(), results, R.layout.item_collect_theme);
        mRecyclerView.setAdapter(mCollectAdapter);
        if (results.size() == 0) {
            mEmptyView.setEmptyText("还没有收藏哦！");
        }
        mCollectAdapter.setOnItemClickListener((itemView, viewType, position) -> {
            LogUtil.i("ThemeDailyCollect","position = "+mCollectAdapter.getItem(position).getPosition());
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("position", mCollectAdapter.getItem(position).getPosition());
            startActivity(intent);
        });
    }

    @Override
    protected void lazyLoad() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mRealm.isClosed())
            mRealm.close();
    }

}
