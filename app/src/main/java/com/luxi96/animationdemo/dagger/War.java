package com.luxi96.animationdemo.dagger;

import javax.inject.Inject;

import dagger.Component;

public class War {

    private Stark stark;

    private Boltons boltons;

    @Inject
    public War(Stark stark, Boltons boltons) {
        this.stark = stark;
        this.boltons = boltons;
    }

    public void prepare(){
        if(stark != null && boltons != null){
            stark.prepareForWar();
            boltons.prepareForWar();
        }
    }

    public void request(){
        if(stark != null && boltons != null){
            stark.requestForWar();
            boltons.requestForWar();
        }
    }

    public War(){

        stark = new Stark();
        boltons = new Boltons();

        stark.prepareForWar();
        boltons.prepareForWar();

        stark.requestForWar();
        boltons.requestForWar();

    }

}
