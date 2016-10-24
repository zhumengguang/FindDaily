package com.lzx.zhihudaily.module.comment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.CommentAdapter;
import com.lzx.zhihudaily.base.BaseFragment;
import com.lzx.zhihudaily.eitity.Comment;
import com.lzx.zhihudaily.eitity.LongOrShortComment;
import com.lzx.zhihudaily.network.RetrofitHelper;
import com.lzx.zhihudaily.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xian on 2016/10/23.
 */

public class CommonListFragment extends BaseFragment {

    private String newsId, type;

    public static CommonListFragment newInstance(String id, String type) {
        CommonListFragment commonListFragment = new CommonListFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("newsId", id);
        mBundle.putString("type", type);
        commonListFragment.setArguments(mBundle);
        return commonListFragment;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CommentAdapter mCommentAdapter;
    private EmptyView mEmptyView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comment;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEmptyView = (EmptyView) findViewById(R.id.empty_view);
        if (getArguments() == null)
            return;
        newsId = getArguments().getString("newsId");
        type = getArguments().getString("type");

        initRecyclerView();
        refresh();
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCommentAdapter = new CommentAdapter(getActivity(), new ArrayList<>(), R.layout.item_comment_list);
        mRecyclerView.setAdapter(mCommentAdapter);
    }

    private void refresh() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadCommentList);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadCommentList();
        });
    }

    private void loadCommentList() {
        mCommentAdapter.clear();
        if (type.equals("long_comment")) {
            loadLongComment();
        } else {
            loadShortComment();
        }
    }

    private void loadLongComment() {
        RetrofitHelper.getZhihuDailyService().getLongCommentsInfo(newsId)
                .compose(bindToLifecycle())
                .flatMap(new Func1<LongOrShortComment, Observable<List<Comment>>>() {

                    @Override
                    public Observable<List<Comment>> call(LongOrShortComment longOrShortComment) {
                        return Observable.just(longOrShortComment.getComments());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finishTask);
    }

    private void loadShortComment() {
        RetrofitHelper.getZhihuDailyService().getShortCommentsInfo(newsId)
                .compose(bindToLifecycle())
                .flatMap(new Func1<LongOrShortComment, Observable<List<Comment>>>() {

                    @Override
                    public Observable<List<Comment>> call(LongOrShortComment longOrShortComment) {
                        return Observable.just(longOrShortComment.getComments());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finishTask);
    }

    private void finishTask(List<Comment> comments) {
        mSwipeRefreshLayout.setRefreshing(false);
        mCommentAdapter.addAll(comments);
        if (mCommentAdapter.getCount()==0){
            mEmptyView.setEmptyImage(R.drawable.comment_empty);
            mEmptyView.setEmptyText("还没有评论哦!");
        }
    }

    @Override
    protected void lazyLoad() {

    }
}
