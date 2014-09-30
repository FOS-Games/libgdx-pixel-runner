package com.FOS.Pixel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;

import javax.swing.*;

/**
 * Created by Lars on 9/19/2014.
 */
public class Player extends PlayerAnimator {

    static final float ACCELERATION = 1f;       // acceleration in m/s of the player
    static final float JUMP_VELOCITY = 7.0f;    // jump velocity in m/s of the player
    static final float GRAVITY = 30f;         // gravity in m/s of the world (9.81 is earth like)
    static final float MAX_VEL = 10f;           // maximum velocity in m/s of the player
    static final int TEXTURE_W = 64;            // the width of the player sprite
    static final int TEXTURE_H = 64;            // the height of the player sprite

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();

    Vector2 size = new Vector2(TEXTURE_W * Box2DTiledMapParserTest.UnitScale, TEXTURE_H * Box2DTiledMapParserTest.UnitScale);
    private Vector2 spawnpoint;

    protected Body body;
    public Body getBody() { return body;}

    private float levelIncr;
    private float levelDecr;
    private float levelDefault = 8f;

    protected World world;
    Fixture bodyFixture;
    Fixture collisionFixture;
    Fixture feetFixture;

    public Player(World world, Vector2 spawn) {
        super();
        this.world = world;
        this.spawnpoint = spawn;
        InitBox2D();
        //this.body.setLinearVelocity(5,0);
    }

    private void InitBox2D() {

        // Define the players body
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.position.set(spawnpoint);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        // Create the full body fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);
        bodyFixture = body.createFixture(shape, 1);
        shape.dispose();

        // Make the full body fixture non-collidable
        bodyFixture.setSensor(true);



        // Add the sprite animation to the player
        bodyFixture.setUserData(super.createAnimation(0, 0));

        // Create the collision box and add it to the player
        PolygonShape cFix = new PolygonShape();
        cFix.setAsBox(1, size.y);
        collisionFixture = body.createFixture(cFix, 1);
        cFix.dispose();

        // Density / Mass of the body
        collisionFixture.setDensity(1000);


        //initSensors();
    }

    private void initSensors() {
        FixtureDef feetdef = new FixtureDef();
        FixtureDef frontdef = new FixtureDef();

        feetdef.friction = 1;
        feetdef.density = 1;
        feetdef.isSensor = true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,0,new Vector2(0,size.y/2),0);



    }

    public void render(float dt){

    }

    public void update(float dt){
        //this.body.setLinearVelocity(5,0);
        position = body.getPosition();
        playerMovement();
        //this.body.setLinearVelocity(levelDefault,this.body.getLinearVelocity().y);
        playerUpdateSpeed();
    }

    public void playerUpdateSpeed(){
        velocity = this.body.getLinearVelocity();
        if(velocity.x > MAX_VEL){
            velocity.x =MAX_VEL;
        }
        else if(velocity.x<levelDefault){
            velocity.x = levelDefault;
        }
        this.body.setLinearVelocity(velocity.x,velocity.y);
    }

    public boolean increaseSpeed(int incr){
        velocity = this.body.getLinearVelocity();
        if(velocity.x +incr > MAX_VEL){
            return false;
        }
        else {
            this.body.setLinearVelocity(velocity.x+incr, velocity.y);
            return true;
        }
    }
    public  boolean increaseSpeed(){
        velocity = this.body.getLinearVelocity();
        if(velocity.x +levelIncr > MAX_VEL){
            return false;
        }
        else {
            this.body.setLinearVelocity(velocity.x+levelIncr, velocity.y);
            return true;
        }
    }

    public boolean decreaseSpeed(int decr){
        velocity=this.body.getLinearVelocity();
        if(velocity.x-decr<0){
            return false;
        }
        else{
            this.body.setLinearVelocity(velocity.x-decr, velocity.y);
            return true;
        }
    }
    public boolean decreaseSpeed(){
        velocity=this.body.getLinearVelocity();
        if(velocity.x-levelDecr<0){
            return false;
        }
        else{
            this.body.setLinearVelocity(velocity.x-levelDecr, velocity.y);
            return true;
        }
    }
    private void playerMovement() {
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& body.getLinearVelocity().x >=-MAX_VEL){
//            body.setLinearVelocity(body.getLinearVelocity().x-ACCELERATION,body.getLinearVelocity().y);
//
//        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            //body.setLinearVelocity(body.getLinearVelocity().x,JUMP_VELOCITY);
            //body.applyLinearImpulse(0, JUMP_VELOCITY, body.getPosition().x, body.getPosition().y, true);

            //body.applyLinearImpulse(0, body.getMass() * 10, body.getWorldCenter().x, body.getWorldCenter().y, true );
            body.applyLinearImpulse(new Vector2(0, JUMP_VELOCITY / Box2DTiledMapParserTest.UnitScale), this.body.getPosition(), true);
        }
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& body.getLinearVelocity().x <=MAX_VEL){
//            body.setLinearVelocity(body.getLinearVelocity().x+ACCELERATION,body.getLinearVelocity().y);
//        }

    }
}
