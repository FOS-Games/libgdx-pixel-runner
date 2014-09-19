package com.FOS.Pixel;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lars on 9/19/2014.
 */
public class Player {

    static final float ACCELERATION = 20f;
    static final float JUMP_VELOCITY = 10;
    static final float GRAVITY = 20.0f;
    static final float MAX_VEL = 6f;
    static final float DAMP = 0.90f;

    Vector2 position = new Vector2();
    Vector2 acceleration = new Vector2();
    Vector2 velocity = new Vector2();




    public Player(){

    }
}
