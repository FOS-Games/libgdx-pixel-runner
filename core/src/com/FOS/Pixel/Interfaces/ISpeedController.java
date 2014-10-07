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
     * @param adjustWith Vector2 to which the ISpeedController has to increase
     * @param steps amount of steps used to increment the speed
     */
    void adjustSpeed(Vector2 adjustWith, int steps);


    /**
     * Increases speed per x amount seconds
     * @param adjustWith Vector2 to which the ISpeedController has to increase
     * @param steps amount of steps used to increment the speed
     * @param seconds amount of seconds in which the increments have to happen
     */
    void adjustSpeed(Vector2 adjustWith, int steps, float seconds);


}
