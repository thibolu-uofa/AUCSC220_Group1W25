package com.rollo;

public class UpgradeOption {
    public int imageResId;
    public Runnable upgradeAction;

    public UpgradeOption(int imageResId, Runnable upgradeAction) {
        this.imageResId = imageResId;
        this.upgradeAction = upgradeAction;
    }
}
