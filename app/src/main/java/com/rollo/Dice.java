/**
 * The Dice class represents a customizable die with a type, pip colour, and possible sides.
 * This class involves rolling the die to select a random side, retrieving its current side,
 * and modifying both attributes the possible sides of the die and the pip colour.
 */

package com.rollo;

import java.util.Random;

class Dice {
    private String diceType;
    private String pipColour;
    private int[] possibleSides;
    private int currentSide;

    /**
     * Constructor to initialize the Dice object.
     * @param diceType The type of the die.
     * @param pipColour The colour of the pips.
     * @param possibleSides The array of the die's possible sides.
     */
    public Dice(String diceType, String pipColour, int[] possibleSides) {
        this.diceType = diceType;
        this.pipColour = pipColour;
        this.possibleSides = possibleSides;
        this.currentSide = getRandomSide();
    }//Dice

    public Dice() {
        diceType = "";
        pipColour = "";
        possibleSides = new int[]{1,2,3,4,5,6};
        currentSide = getRandomSide();
    }

    /**
    * Returns the type of the die.
    */
    public String getDiceType() {
        return diceType;
    }//getDiceType

    /**
    * Returns the pip colour of the die.
=    */
    public String getPipColour() {
        return pipColour;
    }//getPipColour

    /**
    * Returns the possible values the die can roll.
=    */
    public int[] getPossibleSides() {
        return possibleSides;
    }//getPossibleSides

    /**
     * Returns the current rolled side of the die.
     */
    public int getCurrentSide() {
        return currentSide;
    }//getCurrentSide

    /**
     * Sets the type of the die.
     * @param diceType The new die type.
     */
    public void setDiceType(String diceType) {
        this.diceType = diceType;
    }//setDiceType

    /**
     * Sets the pip colour of the die.
     * @param pipColour The new pip colour.
     */
    public void setPipColour(String pipColour) {
        this.pipColour = pipColour;
    }//setPipColour

    /**
     * Sets new possible sides for the die.
     * @param possibleSides An array of new possible sides.
     */
    public void setPossibleSides(int[] possibleSides) {
        this.possibleSides = possibleSides;
    }//setPossibleSides

    /**
    * Returns the rolled side.
    */
    public int getRandomSide() {
        Random rand = new Random();
        currentSide = possibleSides[rand.nextInt(possibleSides.length)];
        return currentSide;
    }//getRandomSide
}//Dice

