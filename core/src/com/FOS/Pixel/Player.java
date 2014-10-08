package com.FOS.Pixel;

import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Data.PlayerData.AbilityType;
import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.Interfaces.ISpeedController;
import com.FOS.Pixel.handlers.PlayerAnimatorHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;


public class Player extends PlayerAnimatorHandler implements ISpeedController{

    public enum PLAYER_STATE {
        JUMP,
        RUN,
        STUMBLE
    }

    static final float JUMP_VELOCITY = 4f;    // jump velocity in m/s of the player
    static final int TEXTURE_W = 128;            // the width of the player sprite
    static final int TEXTURE_H = 128;            // the height of the player sprite

    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public float minVelocity;

    Vector2 size = new Vector2(TEXTURE_W * PixelVars.UNITSCALE, TEXTURE_H * PixelVars.UNITSCALE);
    private Vector2 spawnpoint;

    private Body body;
    public Body getBody() { return body;}

    protected World world;

    Fixture bodyFixture;
    Fixture collisionFixture;
    Fixture feetFixture;
    Fixture wingFixture;
    Fixture weaponFixture;
    Fixture sensorFixture;
    Fixture wallsensorFixture;

    GameScreen gameScreen;
    PlayerData playerData;

    PLAYER_STATE state;
    PLAYER_STATE anim;

    public Player(GameScreen gameScreen, Vector2 spawn) {
        super();
        this.world = gameScreen.getWorld();
        this.spawnpoint = spawn;
        this.gameScreen = gameScreen;
        this.velocity = new Vector2(gameScreen.levelData.getMinSpeed(),0);
        this.minVelocity =gameScreen.levelData.getMinSpeed();
        playerData = SaveHandler.getSaveData().getPlayerData();

        InitBox2D();
        body.setUserData(this);

        super.InitAnimation();
        state= PLAYER_STATE.RUN;
        anim=PLAYER_STATE.RUN;

//        Timer test = new Timer();
//        test.scheduleTask(new Timer.Task() {
//            @Override
//            public void run() {
//                System.out.println("StumbleTest");
//                state = PLAYER_STATE.STUMBLE;
//            }
//        },2,2,1);
//        test.start();
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
        initWallSensor();
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
        collisionFixture.setUserData("playerCollider");
        shape.dispose();
    }

    private void initWallSensor() {
        FixtureDef wallsensorDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 4, size.y / 3, new Vector2(0, -size.y / 2), 0);

        wallsensorDef.shape = shape;
        wallsensorDef.density = 0;
        wallsensorDef.friction = 0;
        wallsensorDef.isSensor = true;

        wallsensorFixture = body.createFixture(wallsensorDef);
        wallsensorFixture.setUserData("wallSensorCollider");
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
        checkAnim();
    }

    private void checkAnim() {
        if(anim!=state){
            switch (state){
                case RUN:
                    setRunAnim();
                    anim=state;
                    break;
                case JUMP:
                    setJumpAnim();
                    anim=state;
                    break;
                case STUMBLE:
                    setStumbleAnim();
                    anim=state;
                    break;
            }
        }
        if(anim == PLAYER_STATE.RUN){
            ((AnimatedBox2DSprite)getBodyFixture().getUserData()).getAnimation().setFrameDuration(1/this.body.getLinearVelocity().x);
        }
        if(!gameScreen.pixelContactListener.playerCanJump()){
            state=PLAYER_STATE.JUMP;
        }
        else if (gameScreen.pixelContactListener.playerCanJump()&&anim!=PLAYER_STATE.RUN&&anim!=PLAYER_STATE.STUMBLE){
            state=PLAYER_STATE.RUN;
        }
        else if (anim==PLAYER_STATE.STUMBLE && ((AnimatedBox2DSprite)getBodyFixture().getUserData()).isAnimationFinished()){
            state=PLAYER_STATE.RUN;
        }
    }

    public void playerUpdateSpeed(){
        velocity = this.body.getLinearVelocity();
        this.body.setLinearVelocity(minVelocity,velocity.y);
//        System.out.println(this.body.getLinearVelocity());
    }

    boolean jumped = false;
    boolean holdable = false;
    long time = 0;

    private void playerJump() {

        // First jump off the ground
        // !isFalling()
        if(gameScreen.pixelContactListener.playerCanJump() && inputJustPressed()) {
            holdable = true;
            time = System.currentTimeMillis();
            body.applyLinearImpulse(new Vector2(0, (JUMP_VELOCITY / PixelVars.UNITSCALE)*playerData.getAbilityData(AbilityType.JUMP).getMultiplier()), this.body.getPosition(), true);
        }

        // Check if key is released in between
        if(!inputPressed()) {
            holdable = false;
        }

        // In the jumping activity
        if(holdable && inputPressed() && TimeUtils.timeSinceMillis(time) <= 250) {
            body.applyLinearImpulse(new Vector2(0, ((JUMP_VELOCITY / 4) / PixelVars.UNITSCALE)*playerData.getAbilityData(AbilityType.JUMP).getMultiplier()), this.body.getPosition(), true);
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


    @Override
    public void adjustSpeed(Vector2 adjustWith, int steps) {
        float xincr= (adjustWith.x/(float)steps)/playerData.getAbilityData(AbilityType.STRENGTH).getMultiplier();
        float yincr= adjustWith.y/(float)steps;
        final Vector2 incrsteps = new Vector2(xincr,yincr);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                body.setLinearVelocity(body.getLinearVelocity().add(incrsteps));

                minVelocity += incrsteps.x;
            }
        },1,1,steps-1);

    }



    @Override
    public void adjustSpeed(Vector2 adjustWith, int steps, float seconds) {
        float xincr= (adjustWith.x/(float)steps)/playerData.getAbilityData(AbilityType.STRENGTH).getMultiplier();
        float yincr= adjustWith.y/(float)steps;
        final Vector2 incrsteps = new Vector2(xincr,yincr);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                body.setLinearVelocity(body.getLinearVelocity().add(incrsteps));
                minVelocity += incrsteps.x;
            }
        },seconds,seconds,steps-1);
    }




}
