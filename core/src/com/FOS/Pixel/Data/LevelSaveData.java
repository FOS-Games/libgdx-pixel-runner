package com.FOS.Pixel.Data;

/**
 * Created by Lars on 10/2/2014.
 */
public class LevelSaveData {
    float levelDistance;
    long totaltime;
    boolean unlocked;

    public LevelSaveData() {
    }

    public LevelSaveData(float levelDistance, long totaltime, boolean unlocked) {
        this.levelDistance = levelDistance;
        this.totaltime = totaltime;
        this.unlocked = unlocked;
    }
}
