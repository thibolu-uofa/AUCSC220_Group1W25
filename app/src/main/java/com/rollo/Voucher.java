package com.rollo;

import android.content.Context;

public class Voucher extends Upgrade {
    private UserData userData;
    private GameState state;
    public Voucher(){
        this.userData = new UserData();
        this.state = userData.getGameState();
    }

    public Voucher(UserData userData){
        this.userData = userData;
        this.state = userData.getGameState();
    }

    public void upgradePlays(Context context){
        Continue cont = new Continue(context);
        cont.setPlays(context, cont.getPlaysFromJson() + 1);
    }

    public void upgradeRerolls(Context context){
        Continue cont = new Continue(context);
        cont.setRerolls(context, cont.getRerollFromJson() + 1);
    }
}
