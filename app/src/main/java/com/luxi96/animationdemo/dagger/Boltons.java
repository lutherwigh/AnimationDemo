package com.luxi96.animationdemo.dagger;

import com.luxi96.animationdemo.util.LogUtils;

import javax.inject.Inject;

public class Boltons implements  House {

    @Inject
    public Boltons() {

    }

    @Override
    public void prepareForWar() {
        LogUtils.d(getClass().getSimpleName() + "prepare for war");
    }

    @Override
    public void requestForWar() {
        LogUtils.d(getClass().getSimpleName() + "request for war");
    }
}
