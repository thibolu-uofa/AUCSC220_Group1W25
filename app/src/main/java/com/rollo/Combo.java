package com.rollo;

import android.content.Context;

public class Combo extends Upgrade {
    private HandTypeManager manager;

    public Combo() {
        this.manager = new HandTypeManager();
    }

    public Combo(HandTypeManager manager){
        this.manager = manager;
    }

    public void upgradeHandType(String handType) {
        HandType type;
        type = manager.getHandByName(handType);
        type.setPips(type.getPips() + 10);
        type.setMult(type.getMult() + 2);
        type.setLevel(type.getLevel() + 1);
    }

    public void setToJson(Context context){
        Continue cont = new Continue(context);
        cont.setHandtypes(context, manager.getAllHands());
    }
}