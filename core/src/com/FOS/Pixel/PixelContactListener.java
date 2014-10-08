package com.FOS.Pixel;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Lars on 9/23/2014.
 */
public class PixelContactListener implements ContactListener {

    private int numFootContacts;
    private Array<Body> orbsToRemove;

    private Array<Body> cratesToRemove;

    public PixelContactListener() {
        super();
        orbsToRemove = new Array<Body>();

        cratesToRemove = new Array<Body>();
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

        //Check if player is hugging a wall
        if(fa.getUserData() != null && fa.getUserData().equals("wallSensorCollider") && fb.getUserData() != null && fb.getUserData().equals("ground")) {
            numFootContacts--;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("wallSensorCollider") && fa.getUserData() != null && fa.getUserData().equals("ground")) {
            numFootContacts--;
        }

        // Collect the orbs!
        if(fa.getUserData() != null && fa.getUserData().equals("playerCollider") && fb.getUserData() != null && fb.getUserData().equals("orb")) {
            orbsToRemove.add(fb.getBody());

        }
        if(fb.getUserData() != null && fb.getUserData().equals("playerCollider") && fa.getUserData() != null && fa.getUserData().equals("orb")) {
            orbsToRemove.add(fa.getBody());
        }


        if(fa.getUserData() != null && fa.getUserData().equals("playerCollider") && fb.getUserData() != null && fb.getUserData().equals("crate")) {
            cratesToRemove.add(fb.getBody());

        }
        if(fb.getUserData() != null && fb.getUserData().equals("playerCollider") && fa.getUserData() != null && fa.getUserData().equals("crate")) {
            cratesToRemove.add(fa.getBody());
        }

        //TODO Crate contact

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        // Not touching the ground with our feet
        if(fa.getUserData() != null && fa.getUserData().equals("feet") && fb.getUserData() != null && fb.getUserData().equals("ground")) {
            numFootContacts--;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("feet") && fa.getUserData() != null && fa.getUserData().equals("ground")) {
            numFootContacts--;
        }

        // Not touching the wall... ground with out body
        if(fa.getUserData() != null && fa.getUserData().equals("wallSensorCollider") && fb.getUserData() != null && fb.getUserData().equals("ground")) {
            numFootContacts++;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("wallSensorCollider") && fa.getUserData() != null && fa.getUserData().equals("ground")) {
            numFootContacts++;
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

    public Array<Body> getOrbs() { return orbsToRemove; }

    public Array<Body> getCrates() {
        return cratesToRemove;
    }
}
