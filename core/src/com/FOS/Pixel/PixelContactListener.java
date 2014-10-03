package com.FOS.Pixel;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Lars on 9/23/2014.
 */
public class PixelContactListener implements ContactListener {

    private int numFootContacts;

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        // Foot contact
        if(fa.getUserData() != null && fa.getUserData().equals("feet")) {
            numFootContacts++;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("feet")) {
            numFootContacts++;
        }


        if(fa.getUserData() != null && fa.getUserData().equals("orb") && fb.getUserData() instanceof Player) {
            ((Player)fb.getUserData()).gameScreen.orbs++;
            fa.getBody().getWorld().destroyBody(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("orb")&& fb.getUserData() instanceof Player) {
            ((Player)fa.getUserData()).gameScreen.orbs++;
            fb.getBody().getWorld().destroyBody(fb.getBody());
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

        if(fa.getUserData() != null && fa.getUserData().equals("feet")) {
            numFootContacts--;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("feet")) {
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

}
