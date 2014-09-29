package com.FOS.Pixel;

import com.badlogic.gdx.graphics.Camera;

/**
 * Created by Lars on 9/26/2014.
 */
public class PlayerCamera extends Camera {
    private Player player;

    public PlayerCamera(Player player){
        this.player = player;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(boolean updateFrustum) {

    }
}
