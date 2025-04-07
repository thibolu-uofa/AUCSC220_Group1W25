package com.rollo;


public class Gemstone extends Upgrade {
    private HandType type;
    private int addedPips;
    private int addedMult;

    public void upgradeHandType() {
        type.setPips(type.getPips() + addedPips);
        type.setMult(type.getMult() + addedMult);
        type.setLevel(type.getLevel() + 1);
    }
}