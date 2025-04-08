package com.rollo;

public class HandType {
    private final String name;
    private int pips;
    private int mult;
    private int level;

    public HandType(String name, int pips, int mult, int level) {
        this.name = name;
        this.pips = pips;
        this.mult = mult;
        this.level = level;
    }

    public String getName() { return name; }
    public int getPips() { return pips; }
    public int getMult() { return mult; }
    public int getLevel() { return level; }

    public void setPips(int pips) { this.pips = pips; }
    public void setMult(int mult) { this.mult = mult; }
    public void setLevel(int level) { this.level = level; }
}