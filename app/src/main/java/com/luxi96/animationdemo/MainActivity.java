package com.luxi96.animationdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luxi96.animationdemo.adapter.TabPagerAdapter;
import com.luxi96.animationdemo.fragment.AdvancePropertyFragment;
import com.luxi96.animationdemo.fragment.PropertyAnimationFragment;
import com.luxi96.animationdemo.fragment.RxTestFragment;
import com.luxi96.animationdemo.fragment.ScrollFragment;
import com.luxi96.animationdemo.fragment.TextSwitchFragment;
import com.luxi96.animationdemo.fragment.TweenFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.main_pager)
    ViewPager pager;
    @BindView(R.id.main_tabs)
    TabLayout tabs;

    Unbinder unbinder;

    List<Fragment> fragmentList;

    TabPagerAdapter pagerAdapter;

    String titles[] = {"Tween","Property","Advance Property","Rx测试","文字变换","Scroll和Recycler"};

    // todo webview缓存

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initPager();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }

    void initPager(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new TweenFragment());
        fragmentList.add(new PropertyAnimationFragment());
        fragmentList.add(new AdvancePropertyFragment());
        fragmentList.add(new RxTestFragment());
        fragmentList.add(new TextSwitchFragment());
        fragmentList.add(new ScrollFragment());

        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),fragmentList,titles);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.setupWithViewPager(pager);
    }

}
