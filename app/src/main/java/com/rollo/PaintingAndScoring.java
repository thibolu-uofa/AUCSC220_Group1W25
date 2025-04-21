package com.rollo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PaintingAndScoring {

    private HandTypeManager hands;

    public PaintingAndScoring(){
        hands = new HandTypeManager();
    }
    public PaintingAndScoring(HandTypeManager hands){
        this.hands = hands;
    }

    public void setHands(HandTypeManager hands) {
        this.hands = hands;
    }

    public HandType scoring(ArrayList<Integer> selectedValues){

        return determineHandType(selectedValues, getScoring(selectedValues));
    }

    public HashMap<Integer, Integer> getScoring(ArrayList<Integer> selectedValues){
        HashMap<Integer, Integer> scoring = new HashMap<>();
        for (int i = 1; i <= 6; i++) scoring.put(i, 0);
        for (int i = 0; i < selectedValues.size(); i++) {
            if (scoring.containsKey(selectedValues.get(i))) {
                scoring.replace(selectedValues.get(i), scoring.get(selectedValues.get(i)) + 1);
            }
        }
        return scoring;
    }
    public HandType determineHandType(ArrayList<Integer> selectedValues, HashMap<Integer, Integer> scoring) {
        ArrayList<Integer> frequencies = new ArrayList<>(scoring.values());
        Collections.sort(frequencies, Collections.reverseOrder());


        ArrayList<Integer> uniqueValues = new ArrayList<>();
        for (int i = 0; i < selectedValues.size(); i++) {
            if (i == 0 || !selectedValues.get(i).equals(selectedValues.get(i - 1))) {
                uniqueValues.add(selectedValues.get(i));
            }
        }

        if (hasLargeStraight(uniqueValues)) {
            return hands.getHandByName("Large Straight");
        }
        if (hasSmallStraight(uniqueValues)) {
            return hands.getHandByName("Small Straight");
        }
        if (frequencies.get(0) == 5) {
            return hands.getHandByName("Yahtzee");
        }
        else if (frequencies.get(0) == 4) {
            return hands.getHandByName("Four of a Kind");
        }
        else if (frequencies.get(0) == 3 && frequencies.get(1) == 2) {
            return hands.getHandByName("Full House");
        }
        else if (frequencies.get(0) == 3) {
            return hands.getHandByName("Three of a Kind");
        }
        else if (frequencies.get(0) == 2 && frequencies.get(1) == 2) {
            return hands.getHandByName("Two Pair");
        }
        else if (frequencies.get(0) == 2) {
            return hands.getHandByName("Pair");
        }
        else {
            return hands.getHandByName("High Die");
        }
    }

    private boolean hasLargeStraight(ArrayList<Integer> values) {
        for (int i = 0; i <= values.size() - 5; i++) {
            int count = 1;
            for (int j = i + 1; j < values.size(); j++) {
                if (values.get(j) == values.get(j - 1) + 1) {
                    count++;
                    if (count == 5) return true;
                } else if (values.get(j) != values.get(j - 1)) {
                    break; // sequence broken
                }
            }
        }
        return false;
    }

    private boolean hasSmallStraight(ArrayList<Integer> values) {
        for (int i = 0; i <= values.size() - 4; i++) {
            int count = 1;
            for (int j = i + 1; j < values.size(); j++) {
                if (values.get(j) == values.get(j - 1) + 1) {
                    count++;
                    if (count == 4) return true;
                } else if (values.get(j) != values.get(j - 1)) {
                    break; // sequence broken
                }
            }
        }
        return false;
    }
}
