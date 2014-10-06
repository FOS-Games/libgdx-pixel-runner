package com.FOS.Pixel;

import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Data.PlayerData.AbilityType;
import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.handlers.PlayerAnimatorHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.TimeUtils;


public class Player extends PlayerAnimatorHandler{


    static final float JUMP_VELOCITY = 6f;    // jump velocity in m/s of the player
    static final float MAX_VEL = 10f;           // maximum velocity in m/s of the player
    static final int TEXTURE_W = 128;            // the width of the player sprite
    static final int TEXTURE_H = 128;            // the height of the player sprite

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
//    FixtureDef bodyFixture = new FixtureDef();
//    FixtureDef collisionFixture = new FixtureDef();
//
//    FixtureDef feetFixture = new FixtureDef();
//    FixtureDef wingFixture = new FixtureDef();
//    FixtureDef weaponFixture = new FixtureDef();

    Fixture bodyFixture;
    Fixture collisionFixture;
    Fixture feetFixture;
    Fixture wingFixture;
    Fixture weaponFixture;
    Fixture sensorFixture;

    GameScreen gameScreen;

    PlayerData playerData;

    public Player(GameScreen gameScreen, Vector2 spawn) {
        super();
        this.world = gameScreen.getWorld();
        this.spawnpoint = spawn;
        this.gameScreen = gameScreen;

        playerData = SaveHandler.getSaveData().getPlayerData();

        InitBox2D();
        body.setUserData(this);

        super.InitAnimation();
    }

    @Override
    protected PlayerData getPlayerData() {
        return SaveHandler.getSaveData().getPlayerData();
    }

    @Override
    protected OrderedMap<AbilityType, Fixture> getFixtures() {
        OrderedMap<AbilityType, Fixture> map = new OrderedMap<AbilityType, Fixture>();
        map.put(AbilityType.SPEED, feetFixture);
        map.put(AbilityType.JUMP, wingFixture);
        map.put(AbilityType.STRENGTH, weaponFixture);
        return map;
    }

    @Override
    protected Fixture getBodyFixture() {
        return bodyFixture;
    }

    private void InitBox2D() {
        initPlayer();
        initCollision();
        initFeet();
        initWeapon();
        initWings();
        initSensor();
    }

    private void initPlayer() {
        // Define the players body
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.position.set(spawnpoint);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef bodyDef = new FixtureDef();

        // Create the full body fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);

        bodyDef.shape = shape;
        bodyDef.isSensor = true;
        bodyDef.density = 0;

        bodyFixture = body.createFixture(bodyDef);
        shape.dispose();

    }

    private void initSensor() {
        FixtureDef sensorDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 4, size.y / 4, new Vector2(0,-size.y), 0);

        sensorDef.shape = shape;
        sensorDef.density = 0;
        sensorDef.isSensor = true;

        sensorFixture = body.createFixture(sensorDef);
        sensorFixture.setUserData("feet");
        shape.dispose();
    }

    private void initCollision() {
        FixtureDef collisionDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 4, size.y / 2, new Vector2(0, - size.y / 2), 0);

        collisionDef.shape = shape;
        collisionDef.density = 1;
        collisionDef.friction = 0;

        collisionFixture = body.createFixture(collisionDef);
        shape.dispose();
    }

    private void initFeet() {
        FixtureDef feetDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 4, size.y / 4, new Vector2(0,-size.y), 0);

        feetDef.shape = shape;
        feetDef.density = 0;
        feetDef.isSensor = true;

        feetFixture = body.createFixture(feetDef);
        shape.dispose();
    }

    private void initWings() {
        FixtureDef wingDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);

        wingDef.shape = shape;
        wingDef.density = 0;
        wingDef.isSensor = true;

        wingFixture = body.createFixture(wingDef);
        shape.dispose();
    }

    private void initWeapon() {
        FixtureDef weaponDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);

        weaponDef.shape = shape;
        weaponDef.density = 0;
        weaponDef.isSensor = true;

        weaponFixture = body.createFixture(weaponDef);
        shape.dispose();
    }

    public void render(float dt){

    }

    public void update(float dt){
        position = body.getPosition();
        playerJump();
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
//    private void playerJump() {
//        if(gameScreen.pixelContactListener.playerCanJump()) {
//            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
//                body.applyLinearImpulse(new Vector2(0, JUMP_VELOCITY / PixelVars.UNITSCALE), this.body.getPosition(), true);
//        }
//    }

    boolean jumped = false;
    boolean holdable = false;
    long time = 0;

    private void playerJump() {

        // First jump off the ground
        if( !isFalling() && !jumped && gameScreen.pixelContactListener.playerCanJump() && inputJustPressed()) {
            jumped = true;
            holdable = true;
            time = System.currentTimeMillis();
            body.applyLinearImpulse(new Vector2(0, JUMP_VELOCITY / PixelVars.UNITSCALE), this.body.getPosition(), true);
        }

        // Check if key is released in between
        if(!inputPressed()) {
            holdable = false;
        }

        // In the jumping activity
        if(holdable && inputPressed() && TimeUtils.timeSinceMillis(time) <= 250) {
            jumped = false;
            body.applyLinearImpulse(new Vector2(0, (JUMP_VELOCITY / 4) / PixelVars.UNITSCALE), this.body.getPosition(), true);
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y * 0.85f);
        }
    }

    private boolean inputJustPressed() {
        return Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.justTouched();
    }

    private boolean inputPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isTouched();
    }

    private boolean isFalling() { return body.getLinearVelocity().y < 0; }


}
