package com.luxi96.animationdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luxi96.animationdemo.adapter.TabPagerAdapter;
import com.luxi96.animationdemo.fragment.PropertyAnimationFragment;
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

    String titles[] = {"Tween","Property"};

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

        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),fragmentList,titles);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.setupWithViewPager(pager);
    }



}
