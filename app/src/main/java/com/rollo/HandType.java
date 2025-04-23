/**
 *
 * HandType.java
 *
 * The HandType class is a class of nine combinations that the user can play.
 *
 * @authors - Brett Siemens
 *
 * functions:
 * getName()
 *      basic getter
 * getPips()
 *      basic getter
 * getMult()
 *      basic getter
 * getLevel()
 *      basic getter
 * setName()
 *      basic setter
 * setPips()
 *      basic setter
 * setMult()
 *      basic setter
 * setLevel()
 *      basic setter
 **/

package com.rollo;

public class HandType {
    private String name;
    private int pips;
    private int mult;
    private int level;

    public HandType(){}

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

    public void setName(String name) { this.name = name; }
    public void setPips(int pips) { this.pips = pips; }
    public void setMult(int mult) { this.mult = mult; }
    public void setLevel(int level) { this.level = level; }
}