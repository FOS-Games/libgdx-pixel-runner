package com.FOS.Pixel.Data;

/**
 * Created by Lars on 9/26/2014.
 */
public class LevelData {

    float minSpeed;
    float maxSpeed;
    float speedIncr;
    float bronzeTime;
    float silverTime;
    float goldTime;

    String tmxpath;
    String musicpath;
    String background;


    /**
     * Empty Constructor for LevelData(used by JsonHandler)
     */
    public LevelData() {
    }

    /**
     * Constructor for LevelData
     * @param minSpeed Minimal Level speed
     * @param maxSpeed Maximal level speed
     * @param speedIncr Step size for increments of speed
     * @param bronzeTime Time for Bronze medal
     * @param silverTime Time for Silver medal
     * @param goldTime Time for Gold medal
     * @param tmxpath Path to the tmx map
     * @param musicpath Path to the level music
     */
    public LevelData(float minSpeed, float maxSpeed, float speedIncr, float bronzeTime, float silverTime, float goldTime, String tmxpath, String musicpath, String background) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.speedIncr = speedIncr;
        this.bronzeTime = bronzeTime;
        this.silverTime = silverTime;
        this.goldTime = goldTime;
        this.tmxpath = tmxpath;
        this.musicpath = musicpath;
        this.background = background;

    }

    public float getMinSpeed() {
        return minSpeed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getSpeedIncr() {
        return speedIncr;
    }

    public float getBronzeTime() {
        return bronzeTime;
    }

    public float getSilverTime() {
        return silverTime;
    }

    public float getGoldTime() {
        return goldTime;
    }

    public String getTmxpath() {
        return tmxpath;
    }

    public String getMusicpath() {
        return musicpath;
    }

    public String getBackground() { return background; }
}
