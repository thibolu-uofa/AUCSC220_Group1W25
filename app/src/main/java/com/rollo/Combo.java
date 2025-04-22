package com.rollo;

import android.content.Context;

import java.util.Random;

public class Combo extends Upgrade {
    private HandTypeManager manager;

    public Combo() {
        this.manager = new HandTypeManager();
    }

    public Combo(HandTypeManager manager){
        this.manager = manager;
    }

    public HandType upgradeHandType(String handType) {
        HandType type;
        type = manager.getHandByName(handType);
        type.setPips(type.getPips() + 10);
        type.setMult(type.getMult() + 2);
        type.setLevel(type.getLevel() + 1);
        return type;
    }

    public void setToJson(Context context){
        Continue cont = new Continue(context);
        cont.setHandtypes(context, manager.getAllHands());
    }

    public String selectRandomCombo(){
        String[] possibleCombo = new String[]{"High Die", "Pair", "Two Pair",
                "Three of a Kind", "Small Straight",
                "Large Straight", "Full House", "Four of a Kind", "Yahtzee"};
        Random rand = new Random();
        return possibleCombo[rand.nextInt(possibleCombo.length)];
    }
}