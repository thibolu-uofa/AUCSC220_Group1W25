/**
 * PaintingAndScoring.Java
 *
 * Author: Miron Nekhoroshkov
 * with code written by Brett Siemens
 *
 * this file will handle all scoring from gamefield, and will implement some
 * basic paintings
 *
 * functions:
 *      setHands(HandTypeManager hands)
 *          set the needed hands
 *      setPaintings(String[] paintings)
 *          if the user has some paintings added,
 *          we would like to update them through this
 *          method
 *      scoring(ArrayList<Integer> selectedValues)
 *          returns the hand of the selected dice which
 *          will be used to calculate the score
 *      getScoring(ArrayList<Integer> selectedValues)
 *          returns a frequency table of each die value
 *          selected
 *      determineHandType(ArrayList<Integer> selectedValues,
 *              HashMap<Integer, Integer> scoring)
 *          finds the hand that was used for scoring
 *      hasLargeStraight(ArrayList<Integer> values)
 *          finds out if the selected hand played
 *          contains a large straight
 *      hasSmallStraight(ArrayList<Integer> values)
 *          finds out if the selected hand played
 *          contains a small straight
 *      HighRoller(ArrayList<Integer> selectedValues)
 *          gives the amount mult should be added if
 *          high roller painting was selected
 *      allOrNothing(ArrayList<Integer> selectedValues)
 *          finds out if the allOrNothing painting can be
 *          used
 */

package com.rollo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PaintingAndScoring {

    private HandTypeManager hands;

    public String[] paintings;

    /**
     * PaintingAndScoring()
     *
     * This and the following three constructors ensure a quick
     * and easy initialization of variables that will be used. It
     * also provides a quick way to test this file.
     */
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

    /**
     * setHands
     *
     * If the hands used in the game need to be updated, this
     * would be the way to do it
     *
     * @param hands
     */
    public void setHands(HandTypeManager hands) { this.hands = hands; }

    /**
     * setPaintings
     *
     *  If the paintings used in the game need to be updated, this
     *  would be the way to do it.
     *
     * @param paintings
     */
    public void setPaintings(String[] paintings) { this.paintings = paintings;}

    /**
     * scoring
     *
     * This function is taken from Brett, it will help determine
     * the hand that was played and the impact specific paintings
     * have on the scoring
     *
     * @param selectedValues
     * @return the hand that the player is playing
     */
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

    /**
     * getScoring
     *
     * A way to return the frequency of each value from the dice hand selected
     *
     * @param selectedValues
     * @return a hashmap with the dice value and its occurrence
     */
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

    /**
     * determineHandType
     *
     * this function helps identify the handtype that is selected from
     * the frequency table.
     *
     * @param selectedValues
     * @param scoring
     * @return the handtype selected
     */
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

    /**
     * hasLargeStraight
     *
     * easy way to identify if a hand selected is a Large Straight,
     * by moving through the list and counting if the list has a
     * continuously growing ints
     *
     * @param values
     * @return true if there is a large straight, if not then it is false
     */
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

    /**
     * hasLargeStraight
     *
     * easy way to identify if a hand selected is a small Straight,
     * by moving through the list and counting if the list has a
     * continuously growing ints
     *
     * @param values
     * @return true if it is a small straight, false if it isn't
     */
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

    /**
     * HighRoller
     *
     * is a function that is used to update the score if a certain
     * painting was purchased, it will add to a certain hands multiplier
     * if selected.
     *
     * @param selectedValues
     * @return the amount a multiplier should be increased
     */
    public int HighRoller(ArrayList<Integer> selectedValues){
        int increasedMult = 0;
        for (int i = 0; i < selectedValues.size(); i++) {
            if (selectedValues.get(i) == 5 || selectedValues.get(i) == 6){
                increasedMult++;
            }
        }
        return increasedMult;
    }

    /**
     * allOrNothing
     *
     * it will check if a selected hand has all even's
     *
     * @param selectedValues
     * @return true if the hand is selected, otherwise it is false
     */
    public boolean allOrNothing(ArrayList<Integer> selectedValues){
        for (int i = 0; i < selectedValues.size(); i++) {
            if (selectedValues.get(i) % 2 != 0){
                return false;
            }
        }
        return true;
    }
}
