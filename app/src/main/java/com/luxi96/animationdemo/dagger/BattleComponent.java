package com.luxi96.animationdemo.dagger;

import dagger.Component;

@Component(modules = BraavosModule.class)
public interface BattleComponent {

    War getWar();

    Stark getStark();

    Boltons getBoltons();

}
