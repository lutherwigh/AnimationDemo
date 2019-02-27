package com.luxi96.animationdemo.fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.luxi96.animationdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TextSwitchFragment extends BaseFragment {

    @BindView(R.id.flipper)
    ViewFlipper flipper;
    @BindView(R.id.flipper_item_1)
    TextView item1;
    @BindView(R.id.flipper_item_2)
    TextView item2;

    List<TextView> textViewList;

    @Override
    public int getResId() {
        return R.layout.fragment_text_switch;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        textViewList = getData();

        flipper.setInAnimation(getContext(),R.anim.push_up_in);
        flipper.setOutAnimation(getContext(),R.anim.push_up_out);
        flipper.startFlipping();
    }

    List<TextView> getData(){
        List<TextView> list = new ArrayList<>();
        for (int i =0;i<100;i++){
            TextView textView = new TextView(getContext());
            textView.setText(String.valueOf(i * 100));
            list.add(textView);
        }
        return list;
    }

}
