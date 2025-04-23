/**
 * The Voucher class is a type of upgrade that increases the number of
 * rolls and plays available to the user.
 *
 * functions:
 * upgradePlays()
 *      upgrades the number of plays available to the user
 * upgradeRerolls()
 *      upgrades the number of rerolls available to the user
 *
 * @author - Brett Siemens
 */

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
