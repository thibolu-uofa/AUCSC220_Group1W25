/**
 *
 * Combo.java
 *
 * The Combo class is a type of upgrade which upgrades the users hand combinations.
 *
 * @authors - Brett Siemens
 *
 * functions:
 * upgradeHandType(String handType)
 *      increases score when that hand type is played
 *
 * setToJson(Context context)
 *      writes the new hand type combos into UserData json file
 *
 * selectRandomCombo
 *      picks a random combo - used in the shop to generate combo upgrades
 */

package com.rollo;

import android.content.Context;

import java.util.Random;

public class Combo {
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