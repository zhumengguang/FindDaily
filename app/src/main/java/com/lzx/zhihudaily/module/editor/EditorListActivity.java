package com.lzx.zhihudaily.module.editor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.EditListAdapter;
import com.lzx.zhihudaily.base.BaseActivity;
import com.lzx.zhihudaily.eitity.Editor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xian on 2016/10/22.
 * 功能：主编列表
 */

public class EditorListActivity extends BaseActivity {

    private ImageView back;
    private RecyclerView mRecyclerView;
    private EditListAdapter mEditListAdapter;
    private List<Editor> mEditors;

    @Override
    public int getLayoutId() {
        return R.layout.activity_editor_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        back = (ImageView) findViewById(R.id.back);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    protected void initToolBar() {
        back.setOnClickListener(view -> finish());
    }

    @Override
    protected void initData() {
        mEditors = getIntent().getParcelableArrayListExtra("editor_list");
        mEditListAdapter = new EditListAdapter(EditorListActivity.this, new ArrayList<>(), R.layout.item_editor_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(EditorListActivity.this));
        mRecyclerView.setAdapter(mEditListAdapter);
        if (mEditors != null)
            mEditListAdapter.addAll(mEditors);
        mEditListAdapter.setOnItemClickListener((itemView, viewType, position) -> {
            Intent intent = new Intent(EditorListActivity.this, EditorHomeActivity.class);
            intent.putExtra("url", mEditListAdapter.getItem(position).getUrl());
            startActivity(intent);
        });
    }

    @Override
    protected void initAction() {

    }
}
