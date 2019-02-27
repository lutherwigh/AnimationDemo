package com.luxi96.animationdemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luxi96.animationdemo.R;
import com.luxi96.animationdemo.adapter.ScrollRecyclerViewAdapter;
import com.luxi96.animationdemo.bean.ScrollBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ScrollFragment extends BaseFragment {

    @BindView(R.id.scroll_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    ScrollRecyclerViewAdapter mAdapter;

    List<ScrollBean> list;

    @Override
    public int getResId() {
        return R.layout.fragment_scroll;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        list = new ArrayList<>();

        for(int i = 0;i<100;i++){
            list.add(new ScrollBean("第" + i + "个",i));
        }

        mAdapter = new ScrollRecyclerViewAdapter(R.layout.item_scroll_recycler,list);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toast("click " + position);
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        TextView head = new TextView(getContext());
        head.setText("head");
//        mAdapter.setHeaderView(head);
        head.setText("footer");
//        mAdapter.setFooterView(head);

    }
}
