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

    /**
     * Constructor
     * @param manager
     */
    public Combo(HandTypeManager manager){
        this.manager = manager;
    }

    /**
     * Upgrades a hand type from the name
     * @param handType - the name of a handtype to be upgraded
     * @return - new, upgraded handtype
     */
    public HandType upgradeHandType(String handType) {
        HandType type = manager.getHandByName(handType);

        int pipsIncrease = 0;
        int multIncrease = 0;

        switch (handType) {
            case "High Die":
                pipsIncrease = 5;
                multIncrease = 1;
                break;
            case "Pair":
                pipsIncrease = 10;
                multIncrease = 1;
                break;
            case "Two Pair":
                pipsIncrease = 10;
                multIncrease = 2;
                break;
            case "Three of a Kind":
                pipsIncrease = 10;
                multIncrease = 1;
                break;
            case "Small Straight":
                pipsIncrease = 10;
                multIncrease = 2;
                break;
            case "Large Straight":
                pipsIncrease = 15;
                multIncrease = 2;
                break;
            case "Full House":
                pipsIncrease = 20;
                multIncrease = 2;
                break;
            case "Four of a Kind":
                pipsIncrease = 20;
                multIncrease = 2;
                break;
            case "Yahtzee":
                pipsIncrease = 30;
                multIncrease = 3;
                break;
        }

        type.setPips(type.getPips() + pipsIncrease);
        type.setMult(type.getMult() + multIncrease);
        type.setLevel(type.getLevel() + 1);
        return type;
    }

    /**
     * setToJson
     * (Now an Unused Function)
     * @param context
     */
    public void setToJson(Context context){
        Continue cont = new Continue(context);
        cont.setHandtypes(context, manager.getAllHands());
    }

    /**
     * Given string array of names of hand types, selects a random one
     * @return some random combo
     */
    public String selectRandomCombo(){
        String[] possibleCombo = new String[]{"High Die", "Pair", "Two Pair",
                "Three of a Kind", "Small Straight",
                "Large Straight", "Full House", "Four of a Kind", "Yahtzee"};
        Random rand = new Random();
        return possibleCombo[rand.nextInt(possibleCombo.length)];
    }
}