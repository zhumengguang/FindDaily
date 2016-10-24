package com.lzx.zhihudaily.module.collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.ArticleCollectAdapter;
import com.lzx.zhihudaily.base.BaseFragment;
import com.lzx.zhihudaily.eitity.CollectNews;
import com.lzx.zhihudaily.module.detail.DailyDetailActivity;
import com.lzx.zhihudaily.widget.EmptyView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lzx on 2016/10/24.
 * 功能：
 */

public class ArticleCollectFragment extends BaseFragment {

    public static ArticleCollectFragment newInstance() {
        return new ArticleCollectFragment();
    }

    private RecyclerView mRecyclerView;
    private EmptyView mEmptyView;
    private Realm mRealm;
    private ArticleCollectAdapter mCollectAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_collect;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEmptyView = (EmptyView) findViewById(R.id.empty_view);

        mRealm = Realm.getDefaultInstance();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RealmResults<CollectNews> results = mRealm.where(CollectNews.class).findAll();
        mCollectAdapter = new ArticleCollectAdapter(getActivity(), results, null);
        mRecyclerView.setAdapter(mCollectAdapter);
        if (results.size() == 0) {
            mEmptyView.setEmptyText("还没有收藏哦！");
        }
        mCollectAdapter.setOnItemClickListener((itemView, viewType, position) -> {
            Intent intent = new Intent(getActivity(), DailyDetailActivity.class);
            intent.putExtra("newsId", mCollectAdapter.getItem(position).getNewsId());
            intent.putExtra("smallImage", mCollectAdapter.getItem(position).getNewsImage());
            getActivity().startActivity(intent);
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
