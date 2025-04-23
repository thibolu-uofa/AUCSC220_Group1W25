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

    /**
     * Initializes nine array types on empty constructor
     */
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

    /**
     * Uses previously initialized hand types on constructor with a parameter
     * @param handTypes
     */
    public HandTypeManager(List<HandType> handTypes){
        this.handTypes = handTypes;
    }

    /**
     * Gets a hand type from a String
     * @param name - String representing a hand type
     * @return HandType object of that hand
     */
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

    /**
     * Get list of all hand types
     * @return - List of hand types
     */
    public List<HandType> getAllHands() {
        return handTypes;
    }

    /**
     * Uses Combo class to upgrade hand type
     * @param handName - old hand type to be upgraded
     * @param newHandtype - upgraded hand type to be set in place of old one
     */
    public void upgradeHand(String handName, HandType newHandtype) {
        for (int i = 0; i < handTypes.size(); i++) {
            if (handTypes.get(i).getName().equalsIgnoreCase(handName)) {
                handTypes.set(i, newHandtype);
                return;
            }
        }
    }
}
