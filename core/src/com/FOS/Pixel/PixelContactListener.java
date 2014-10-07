package com.FOS.Pixel;

import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Lars on 9/23/2014.
 */
public class PixelContactListener implements ContactListener {

    private int numFootContacts;
    private Array<Body> bodiesToRemove;

    public PixelContactListener() {
        super();
        bodiesToRemove = new Array<Body>();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        // Foot contact
        if(fa.getUserData() != null && fa.getUserData().equals("feet") && fb.getUserData() != null && fb.getUserData().equals("ground")) {
            numFootContacts++;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("feet") && fa.getUserData() != null && fa.getUserData().equals("ground")) {
            numFootContacts++;
        }

//
//        if(fa.getUserData() != null && fa.getUserData().equals("orb") && fb.getUserData() instanceof Player) {
//            ((Player)fb.getUserData()).gameScreen.orbs++;
//            fa.getBody().getWorld().destroyBody(fa.getBody());
//        }
//
//        if(fb.getUserData() != null && fb.getUserData().equals("orb")&& fa.getUserData() instanceof Player) {
//            ((Player)fa.getUserData()).gameScreen.orbs++;
//            fb.getBody().getWorld().destroyBody(fb.getBody());
//        }

        if(fa.getUserData() != null && fa.getUserData().equals("playerCollider") && fb.getUserData() != null && fb.getUserData().equals("orb")) {
            bodiesToRemove.add(fb.getBody());

        }

        if(fb.getUserData() != null && fb.getUserData().equals("playerCollider") && fa.getUserData() != null && fa.getUserData().equals("orb")) {
            bodiesToRemove.add(fa.getBody());
        }


        if(fa.getUserData() != null && fa.getUserData().equals("powerup") && fb.getUserData() instanceof Player) {
            //TODO: something something when player hits powerup
            fa.getBody().getWorld().destroyBody(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("powerup")&& fb.getUserData() instanceof Player) {
            //TODO: something something when player hits powerup
            fb.getBody().getWorld().destroyBody(fb.getBody());
        }

        //TODO Crate contact

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        if(fa.getUserData() != null && fa.getUserData().equals("feet") && fb.getUserData() != null && fb.getUserData().equals("ground")) {
            numFootContacts--;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("feet") && fa.getUserData() != null && fa.getUserData().equals("ground")) {
            numFootContacts--;
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * Returns true if the player should be able to jump.
     * TODO double jump
     */
    public boolean playerCanJump() {
        return numFootContacts > 0;
    }

    public Array<Body> getBodies() { return bodiesToRemove; }


}
