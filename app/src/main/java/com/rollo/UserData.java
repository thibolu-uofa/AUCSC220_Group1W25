package com.rollo;

import java.util.List;

public class UserData {
    List<HandType> handTypes;
    GameState gameState;
    Dice[] dice;


    public List<HandType> getHandTypes() {
        return handTypes;
    }

    public void setHandTypes(List<HandType> handTypes) {
        this.handTypes = handTypes;
    }

    public GameState getGameState() { return gameState; }
    public Dice[] getDice() {
        return dice;
    }
}
class GameState {
    private int round;
    private int highScore;
    private int money;
    private int rerolls;
    private int plays;
    private int currentScore;
    private int scoreToBeat;
    private int maxRound;

    public GameState(){
        this.round = 0;
        this.highScore = 0;
        this.money = 0;
        this.plays = 4;
        this.rerolls = 5;
        this.maxRound = 20;
    }

    public int getHighScore() {return highScore;}
    public int getMoney() {return money;}
    public int getPlays() {return plays;}
    public int getRerolls() {return rerolls;}
    public int getRound() {return round;}
    public int getCurrentScore() {return currentScore;}
    public int getScoreToBeat() {return scoreToBeat;}

    public int getMaxRound() {return maxRound;}

    public void setHighScore(int highScore) {this.highScore = highScore;}
    public void setMoney(int money) {this.money = money;}
    public void setPlays(int plays) {this.plays = plays;}
    public void setRerolls(int rerolls) {this.rerolls = rerolls;}
    public void setRound(int round) {this.round = round;}
    public void setCurrentScore(int newScore) {this.currentScore = newScore;}
    public void setScoreToBeat(int scoreToBeat) {this.scoreToBeat = scoreToBeat;}

    public void setMaxRound(int maxRound) {this.maxRound = maxRound;}
}
