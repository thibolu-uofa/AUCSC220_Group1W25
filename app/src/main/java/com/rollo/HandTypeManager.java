/**
 *
 * HandTypeManager.java
 *
 * The HandTypeManager class is a class that contains the initialization of the nine
 * different hand types in Rollo, and manages changes to those hand types.
 *
 * @authors - Brett Siemens
 *
 * functions:
 * getHandByName()
 *      gets information about a hand from a string equal to its name
 * setHandTypes()
 *      currently unused
 * getAllHands()
 *      basic getter for hand types
 * upgradeHand()
 *      increase the scoring of each hand type
 **/

package com.rollo;

import java.util.ArrayList;
import java.util.List;

public class HandTypeManager {
    private List<HandType> handTypes = new ArrayList<>();

    public HandTypeManager() {
        handTypes.add(new HandType("High Die", 5, 1, 1));
        handTypes.add(new HandType("Pair", 10, 2, 1));
        handTypes.add(new HandType("Two Pair", 20, 2, 1));
        handTypes.add(new HandType("Three of a Kind", 20, 3, 1));
        handTypes.add(new HandType("Small Straight", 30, 3, 1));
        handTypes.add(new HandType("Large Straight", 40, 4, 1));
        handTypes.add(new HandType("Full House", 25, 4, 1));
        handTypes.add(new HandType("Four of a Kind", 30, 4, 1));
        handTypes.add(new HandType("Yahtzee", 50, 5, 1));
    }

    public HandTypeManager(List<HandType> handTypes){
        this.handTypes = handTypes;
    }

    public HandType getHandByName(String name) {
        for (HandType hand : handTypes) {
            if (hand.getName().equals(name)) {
                return hand;
            }
        }
        return null;
    }

    public void setHandTypes(List<HandType> handTypes) {
        this.handTypes = handTypes;
    }

    public List<HandType> getAllHands() {
        return handTypes;
    }

    public void upgradeHand(String handName, HandType newHandtype) {
        for (int i = 0; i < handTypes.size(); i++) {
            if (handTypes.get(i).getName().equalsIgnoreCase(handName)) {
                handTypes.set(i, newHandtype);
                return;
            }
        }
    }
}
