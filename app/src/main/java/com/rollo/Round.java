/**
 * The Round class handles all the attributes of the game's round system, from round number to
 * the score threshold generation.
 */

package com.rollo;

public class Round {

    // Constants
    private static final int DEFAULT_HANDS = 5;
    private static final int DEFAULT_REROLLS = 4;
    private static final double STARTING_THRESHOLD = 300.0;
    private static final int START_ROUND = 1;

    private static final int MAX_ROUND = 20;

    private static final double THRESHOLD_MULT = 1.5;


    // Fields
    protected int score;
    protected int numOfHands;
    protected int numOfRerolls;
    protected int roundNum;
    protected double threshold;
    private final int maxRound;

    /*
     Constructor
     */
    public Round() {
        this.score = 0;
        this.numOfHands = DEFAULT_HANDS;
        this.numOfRerolls = DEFAULT_REROLLS;
        this.roundNum = START_ROUND;
        this.threshold = STARTING_THRESHOLD;
        this.maxRound = MAX_ROUND;
    }

    /*
     Advance to the next round
     */
    public void setNextRound() {
        if (roundNum >= maxRound) return;

        this.score = 0;
        this.numOfHands = DEFAULT_HANDS;
        this.numOfRerolls = DEFAULT_REROLLS;
        this.roundNum += 1;
        setThreshold(); // Might want to adjust in future
    }

    public void addScore(int score){
        this.score += score;
    }

    /*
     Checks whether the score threshold has been reached.
     */
    public boolean isThresholdReached() {
        if (this.score >= this.threshold) {
            //GameField.enableButtons(false);
            // Trigger shop, animations, etc.
            //GameField.enableButtons(true);
            setNextRound();
            return true;
        }
        return false;
    }//chec

    /*
     Checks whether the game is finished
     */
    public boolean isGameFinished() {
        return roundNum > maxRound;
    }//isGameFinished

    /*
     Applies end-game logic
     */
    public void handleGameFinish() {
        if (isGameFinished()) {
            //GameField.enableButtons(false);
            // Display endgame UI or transition
        }//if-statement
    }//handleGameFinish

    /*
     Increases score threshold for next round
     */
    public void setThreshold() {
        this.threshold *= THRESHOLD_MULT;
    }//setThreshold

    // Optional getters
    public int getScore() { return score; }

    public int getRoundNum() { return roundNum; }

    public double getThreshold() { return threshold; }

    public int getMaxRound() { return maxRound; }

    public int getNumOfHands() { return numOfHands; }



    public int getNumOfRerolls() { return numOfRerolls; }

}//Round
