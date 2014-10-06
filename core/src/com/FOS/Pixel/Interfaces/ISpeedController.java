package com.FOS.Pixel.Interfaces;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lars on 10/4/2014.
 */
public interface ISpeedController {

    Vector2 currentvelocity = new Vector2();
    Vector2 futurevelocity = new Vector2();

    /**
     * Increases speed per 1 second
     * @param increaseto Vector2 to which the ISpeedController has to increase
     * @param steps amount of steps used to increment the speed
     */
    void incrSpeed(Vector2 increaseto, int steps);

    /**
     * Decreses speed per 1 second
     * @param decreaseto Vector2 to which the ISpeedController has to decrease
     * @param steps amount of steps used to decrement the speed
     */
    void decrSpeed(Vector2 decreaseto, int steps);

    /**
     * Increases speed per x amount seconds
     * @param increaseto Vector2 to which the ISpeedController has to increase
     * @param steps amount of steps used to increment the speed
     * @param seconds amount of seconds in which the increments have to happen
     */
    void incrSpeed(Vector2 increaseto, int steps, float seconds);

    /**
     * Decreases speed per x amount seconds
     * @param decreaseto Vector2 to which the ISpeedController has to decrease
     * @param steps amount of steps used to decrement the speed
     * @param seconds amount of seconds in which the increments have to happen
     */
    void decrSpeed(Vector2 decreaseto, int steps, float seconds);

}
