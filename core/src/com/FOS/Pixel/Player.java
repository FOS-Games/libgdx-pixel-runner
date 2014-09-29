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
import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;

import javax.swing.*;

/**
 * Created by Lars on 9/19/2014.
 */
public class Player extends PlayerAnimator {

    static final float ACCELERATION = 1f;
    static final float JUMP_VELOCITY = 2;
    static final float GRAVITY = 20.0f;
    static final float MAX_VEL = 6f;
    static final float DAMP = 0.90f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();

    Vector2 size = new Vector2(16*Box2DTiledMapParserTest.UnitScale,16 * Box2DTiledMapParserTest.UnitScale);
    private Vector2 spawnpoint;
    Box2DSprite testsprite = new Box2DSprite(new Texture(Gdx.files.internal("DARK.png")));

    protected Body body;
    public Body getBody() { return body;}

    private float levelIncr;
    private float levelDecr;
    private float levelDefault = 5;

    protected World world;
    Fixture bodyFixture;
    Fixture feetFixture;

    public Player(World world, Vector2 spawn) {
        super();
        this.world = world;
        this.spawnpoint = spawn;
        InitBox2D();
        //this.body.setLinearVelocity(5,0);
    }

    private void InitBox2D() {
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.position.set(spawnpoint);
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);
        bodyFixture = body.createFixture(shape,1);
        feetFixture = body.createFixture(shape,1);
        shape.dispose();

        // Player animatie toevoegen
        bodyFixture.setUserData(super.createAnimation(0, 0));

        // Voeten animatie toevoegen
        feetFixture.setUserData(super.createAnimation(1, 0));

        // Wings animatie toevoegen
        feetFixture.setUserData(super.createAnimation(2, 0));

        // Weapon animatie toevoegen
        feetFixture.setUserData(super.createAnimation(2, 0));


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
            body.setLinearVelocity(body.getLinearVelocity().x,JUMP_VELOCITY);
        }
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& body.getLinearVelocity().x <=MAX_VEL){
//            body.setLinearVelocity(body.getLinearVelocity().x+ACCELERATION,body.getLinearVelocity().y);
//        }

    }
}
