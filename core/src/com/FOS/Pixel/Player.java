package com.FOS.Pixel;

import com.FOS.Pixel.Data.AbilityData;
import com.FOS.Pixel.Data.AbilityType;
import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.handlers.PlayerAnimatorHandler;
import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Created by Lars on 9/19/2014.
 */
public class Player extends PlayerAnimatorHandler {

    static final float ACCELERATION = 1f;       // acceleration in m/s of the player
    static final float JUMP_VELOCITY = 10f;    // jump velocity in m/s of the player
    static final float GRAVITY = 30f;         // gravity in m/s of the world (9.81 is earth like)
    static final float MAX_VEL = 10f;           // maximum velocity in m/s of the player
    static final int TEXTURE_W = 64;            // the width of the player sprite
    static final int TEXTURE_H = 64;            // the height of the player sprite

    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();

    Vector2 size = new Vector2(TEXTURE_W * PixelVars.UNITSCALE, TEXTURE_H * PixelVars.UNITSCALE);
    private Vector2 spawnpoint;

    private Body body;
    public Body getBody() { return body;}

    private float levelIncr;
    private float levelDecr;
    private float levelDefault = 8f;

    protected World world;
    Fixture bodyFixture;
    Fixture collisionFixture;
    Fixture feetFixture;
    GameScreen gameScreen;

    public Player(GameScreen gameScreen, Vector2 spawn) {
        super();
        this.world = gameScreen.getWorld();
        this.spawnpoint = spawn;
        this.gameScreen = gameScreen;

        InitBox2D();

        body.setUserData(this);
    }

    @Override
    protected AbilityData getAbilities() {
        //TODO: Return AbilityData
        return null;
    }

    @Override
    protected OrderedMap<AbilityType, Fixture> getFixtures() {
        //TODO:Return OrderdMap with Fixtures per AbilityType
        return null;
    }

    private void InitBox2D() {
        initPlayer();
        initCollision();
        initSensors();
    }

    private void initPlayer() {
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
        //bodyFixture.setUserData(super.createAnimation(0, 0));
    }

    private void initCollision() {
        FixtureDef collDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 4, size.y);

        collDef.shape = shape;
        collDef.density = 1;
        collDef.friction = 0;

        body.createFixture(collDef).setUserData("collision");
        shape.dispose();
    }

    private void initSensors() {
        FixtureDef feetDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 4, size.y / 4, new Vector2(0,-size.y), 0);

        feetDef.shape = shape;
        feetDef.isSensor = true;

        body.createFixture(feetDef).setUserData("foot");
        shape.dispose();
    }

    public void render(float dt){

    }

    public void update(float dt){
        position = body.getPosition();
        playerMovement();
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
        if(gameScreen.pixelContactListener.playerCanJump()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                body.applyLinearImpulse(new Vector2(0, JUMP_VELOCITY / PixelVars.UNITSCALE), this.body.getPosition(), true);
            }
        }
    }
}
