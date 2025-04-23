package com.rollo;

import java.util.List;

public class UserData {
    List<HandType> handTypes;
    GameState gameState;
    Dice[] dice;

    String[] paintings;

    public UserData(){
        HandTypeManager handTypeManager = new HandTypeManager();
        this.handTypes = handTypeManager.getAllHands();
        this.gameState = new GameState();
        this.dice = new Dice[]{new Dice(), new Dice(), new Dice(),
                                new Dice(), new Dice(), new Dice()};
        paintings = new String[]{"", "", "", ""};
    }


    public List<HandType> getHandTypes() {
        return handTypes;
    }

    public void setHandTypes(List<HandType> handTypes) {
        this.handTypes = handTypes;
    }
    public void setDice(Dice[] dice) {
        this.dice = dice;
    }
    public void setPaintings(String[] paintings) {
        this.paintings = paintings;
    }

    public String[] getPaintings(){ return paintings; }

    public GameState getGameState() { return gameState; }
    public Dice[] getDice() {
        return dice;
    }
}
class GameState {
    private int round;
    private int runHighScore;
    private int rerolls;
    private int plays;
    private int currentScore;
    private int scoreToBeat;
    private int allTimeHighScore;

    public GameState(){
        this.round = 0;
        this.runHighScore = 0;
        this.plays = 4;
        this.rerolls = 5;
        this.allTimeHighScore = 0;
    }

    public int getRunHighScore() {return runHighScore;}
    public int getAllTimeHighScore() {return allTimeHighScore;}
    public int getPlays() {return plays;}
    public int getRerolls() {return rerolls;}
    public int getRound() {return round;}
    public int getCurrentScore() {return currentScore;}
    public int getScoreToBeat() {return scoreToBeat;}

    public void setRunHighScore(int highScore) {this.runHighScore = highScore;}
    public void setAllTimeHighScore(int allTimeHighScore)
        {this.allTimeHighScore = allTimeHighScore;}
    public void setPlays(int plays) {this.plays = plays;}
    public void setRerolls(int rerolls) {this.rerolls = rerolls;}
    public void setRound(int round) {this.round = round;}
    public void setCurrentScore(int newScore) {this.currentScore = newScore;}
    public void setScoreToBeat(int scoreToBeat) {this.scoreToBeat = scoreToBeat;}

}
