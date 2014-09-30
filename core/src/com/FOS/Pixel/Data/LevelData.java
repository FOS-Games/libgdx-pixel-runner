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
    public LevelData(float minSpeed, float maxSpeed, float speedIncr, float bronzeTime, float silverTime, float goldTime, String tmxpath, String musicpath) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.speedIncr = speedIncr;
        this.bronzeTime = bronzeTime;
        this.silverTime = silverTime;
        this.goldTime = goldTime;
        this.tmxpath = tmxpath;
        this.musicpath = musicpath;
    }
}
