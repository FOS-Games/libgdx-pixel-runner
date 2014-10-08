package com.FOS.Pixel;

/**
 * Created by Stefan on 7-10-2014.
 */

import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Interfaces.ISpeedController;
import com.FOS.Pixel.handlers.PlayerAnimatorHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class PlayerProp extends PlayerAnimatorHandler {


    public enum PLAYER_STATE {
        RUN,
    }

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
    Fixture feetFixture;
    Fixture wingFixture;
    Fixture weaponFixture;

    GameScreen gameScreen;
    PlayerData playerData;

    PLAYER_STATE state;
    PLAYER_STATE anim;

    // GameScreen gameScreen
    public PlayerProp(GameScreen gameScreen) {
        super();
        this.world = gameScreen.getWorld();
        this.gameScreen = gameScreen;

        playerData = SaveHandler.getSaveData().getPlayerData();

        InitBox2D();
        body.setUserData(this);

        super.InitAnimation();
        state = PLAYER_STATE.RUN;
        anim = PLAYER_STATE.RUN;
        super.InitAnimation();
    }

    @Override
    public PlayerData getPlayerData() {
        return SaveHandler.getSaveData().getPlayerData();
    }

    @Override
    protected OrderedMap<PlayerData.AbilityType, Fixture> getFixtures() {
        OrderedMap<PlayerData.AbilityType, Fixture> map = new OrderedMap<PlayerData.AbilityType, Fixture>();
        map.put(PlayerData.AbilityType.SPEED, feetFixture);
        map.put(PlayerData.AbilityType.JUMP, wingFixture);
        map.put(PlayerData.AbilityType.STRENGTH, weaponFixture);
        return map;
    }

    @Override
    protected Fixture getBodyFixture() {
        return bodyFixture;
    }

    private void InitBox2D() {
        initPlayer();
        initFeet();
        initWeapon();
        initWings();
    }

    private void initPlayer() {
        // Define the players body
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        //bdef.position.set(spawnpoint);
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
        checkAnim();
    }

    private void checkAnim() {
        if(anim == PLAYER_STATE.RUN && anim != state){
            ((AnimatedBox2DSprite)getBodyFixture().getUserData()).getAnimation().setFrameDuration(1/30f);
        }

    }


    public Animation getAnimation() { return ((AnimatedBox2DSprite)getBodyFixture().getUserData()).getAnimation(); }

    public AnimatedBox2DSprite getAnimatedSprite() { return ((AnimatedBox2DSprite)getBodyFixture().getUserData()); }




}
