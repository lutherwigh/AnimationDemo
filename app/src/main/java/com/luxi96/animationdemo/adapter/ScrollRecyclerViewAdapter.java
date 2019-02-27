package com.luxi96.animationdemo.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luxi96.animationdemo.R;
import com.luxi96.animationdemo.bean.ScrollBean;

import java.util.List;

public class ScrollRecyclerViewAdapter extends BaseQuickAdapter<ScrollBean,BaseViewHolder> {


    public ScrollRecyclerViewAdapter(int layoutResId, @Nullable List<ScrollBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScrollBean item) {
        helper.setText(R.id.item_scroll_id,String.valueOf(item.getId()))
                .setText(R.id.item_scroll_name,item.getText());
    }

}
