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

import javax.swing.*;

/**
 * Created by Lars on 9/19/2014.
 */
public class Player {

    static final float ACCELERATION = 20f;
    static final float JUMP_VELOCITY = 10;
    static final float GRAVITY = 20.0f;
    static final float MAX_VEL = 6f;
    static final float DAMP = 0.90f;

    private  static  final int FRAME_COLUMNS = 6;
    private static final int FRAME_ROWS = 5;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();

    private Vector2 size = new Vector2(16*Box2DTiledMapParserTest.UnitScale,16 * Box2DTiledMapParserTest.UnitScale);
    private Vector2 spawnpoint;
    private Box2DSprite testsprite = new Box2DSprite(new Texture(Gdx.files.internal("DARK.png")));

    protected Body body;
    public Body getBody() { return body;}


    protected World world;
    Fixture bodyFixture;

    public Player( World world,Vector2 spawn){
        this.world = world;
        this.spawnpoint = spawn;
        InitBox2D();



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
        shape.dispose();

        bodyFixture.setUserData(testsprite);


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

    }
}
