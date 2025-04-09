package com.rollo;

import java.util.List;

public class UserData {
    List<HandType> handTypes;
    GameState gameState;
    Dice[] dice;


    public List<HandType> getHandTypes() {
        return handTypes;
    }

    public GameState getGameState() { return gameState; }
    public Dice[] getDice() {
        return dice;
    }
}
class GameState {
    protected int round;
    protected int highScore;
    protected int money;
    protected int rerolls;
    protected int plays;

    public GameState(){
        this.round = 0;
        this.highScore = 0;
        this.money = 0;
        this.rerolls = 0;
        this.plays = 3;
    }

    public int getHighScore() {return highScore;}
    public int getMoney() {return money;}
    public int getPlays() {return plays;}
    public int getRerolls() {return rerolls;}
    public int getRound() {return round;}

    public void setHighScore(int highScore) {this.highScore = highScore;}
    public void setMoney(int money) {this.money = money;}
    public void setPlays(int plays) {this.plays = plays;}

    public void setRerolls(int rerolls) {this.rerolls = rerolls;}
    public void setRound(int round) {this.round = round;}
}
