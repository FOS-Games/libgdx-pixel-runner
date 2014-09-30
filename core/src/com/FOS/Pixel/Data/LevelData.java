package com.FOS.Pixel.Data;

/**
 * Created by Lars on 9/26/2014.
 */
public class LevelData {

    float minSpeed;
    float maxSpeed;
    float speedIncr;
    float maxDelay;
    float minDelay;
    float bronzeTime;

    public LevelData() {
    }

    float silverTime;
    float goldTime;

    public LevelData(float minSpeed, float maxSpeed, float speedIncr, float maxDelay, float minDelay, float bronzeTime, float silverTime, float goldTime) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.speedIncr = speedIncr;
        this.maxDelay = maxDelay;
        this.minDelay = minDelay;
        this.bronzeTime = bronzeTime;
        this.silverTime = silverTime;
        this.goldTime = goldTime;
    }
}
