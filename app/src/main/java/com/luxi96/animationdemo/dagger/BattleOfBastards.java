package com.luxi96.animationdemo.dagger;

public class BattleOfBastards {

    public void main(String[] args){

        Cash cash = new Cash();
        Soldiers soldiers = new Soldiers();

        BattleComponent component = DaggerBattleComponent
                .builder()
                .braavosModule(new BraavosModule(cash,soldiers))
                .build();

        War war = component.getWar();

        war.prepare();
        war.request();
    }

}
