package com.rollo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PaintingAndScoring {

    private HandTypeManager hands;

    public String[] paintings;

    public PaintingAndScoring(){
        hands = new HandTypeManager();
        paintings = new String[]{"", "", "", ""};
    }
    public PaintingAndScoring(HandTypeManager hands){
        this.hands = hands;
        paintings = new String[]{"", "", "", ""};
    }
    public PaintingAndScoring(HandTypeManager hands, String[] paintings){
        this.hands = hands;
        this.paintings = paintings;
    }

    public PaintingAndScoring(String[] paintings){
        hands = new HandTypeManager();
        this.paintings = paintings;
    }

    public void setHands(HandTypeManager hands) {
        this.hands = hands;
    }
    public void setPaintings(String[] paintings) { this.paintings = paintings;}

    public HandType scoring(ArrayList<Integer> selectedValues){
        ArrayList<Integer> confirmedValues = selectedValues;

        HandType selectedHand = determineHandType(confirmedValues, getScoring(confirmedValues));

        for (int i = 0; i < paintings.length; i++) {
            if (paintings[i].equals("High Roller")){
                int increasedMult = HighRoller(confirmedValues);
                selectedHand.setMult(selectedHand.getMult() + increasedMult);
            }
            else if (paintings[i].equals("Pair Magnet")){
                if(selectedHand.getName().equals("Pair") ||
                        selectedHand.getName().equals("Two Pair")){
                    selectedHand.setMult(selectedHand.getMult() + 3);
                }
            }
            else if (paintings[i].equals("Straight Shooter")){
                if(selectedHand.getName().equals("Small Straight")){
                    selectedHand = hands.getHandByName("Large Straight");
                }
            }
            else if (paintings[i].equals("House Flipper")){
                if(selectedHand.getName().equals("Full House")){
                    selectedHand.setPips(selectedHand.getMult() + 15);
                }
            }
            else if (paintings[i].equals("All or Nothing")){

                if(allOrNothing(confirmedValues)){
                    selectedHand.setMult(selectedHand.getMult() + 4);
                }
                else{
                    selectedHand.setMult(selectedHand.getMult() - 1);
                }
            }
        }

        return selectedHand;
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

    public int HighRoller(ArrayList<Integer> selectedValues){
        int increasedMult = 0;
        for (int i = 0; i < selectedValues.size(); i++) {
            if (selectedValues.get(i) == 5 || selectedValues.get(i) == 6){
                increasedMult++;
            }
        }
        return increasedMult;
    }

    public boolean allOrNothing(ArrayList<Integer> selectedValues){
        for (int i = 0; i < selectedValues.size(); i++) {
            if (selectedValues.get(i) % 2 != 0){
                return false;
            }
        }
        return true;
    }
}
