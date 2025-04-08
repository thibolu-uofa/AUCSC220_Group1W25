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
        handTypes.add(new HandType("Full House", 25, 3, 1));
        handTypes.add(new HandType("Four of a Kind", 30, 4, 1));
        handTypes.add(new HandType("Yahtzee", 50, 5, 1));
    }

    public HandTypeManager(List<HandType> handTypes){
        this.handTypes = handTypes;
    }

    public HandType getHandByName(String name) {
        return handTypes.stream()
                .filter(h -> h.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void setHandTypes(List<HandType> handTypes) {
        this.handTypes = handTypes;
    }

    public List<HandType> getAllHands() {
        return handTypes;
    }
}
