package com.luxi96.animationdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class TweenFragment extends Fragment {

    TextView tv_alpha;
    // todo 循环展示各种动画效果

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_tween_fragment,null,false);
        tv_alpha = view.findViewById(R.id.tween_alpha);
        Animation alpha = AnimationUtils.loadAnimation(getContext(),R.anim.tween_alpha);
        tv_alpha.startAnimation(alpha);
        return view;
    }

}
