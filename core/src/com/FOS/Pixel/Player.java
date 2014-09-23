package com.FOS.Pixel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;

import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;

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
    Body body;
    Texture walk_sheet = new Texture(Gdx.files.internal("sprite-animation1.png"));
    float walkFrameWidth = walk_sheet.getWidth()/FRAME_COLUMNS;
    float walkFrameHeight = walk_sheet.getHeight()/FRAME_ROWS;
    Animation walkAnimation;
    SpriteBatch spriteBatch;
    float stateTime;
    TextureRegion currentFrame;
    TextureRegion[] walkFrames;
    int width;
    int height;


    public Player(Body body, int width, int heigth){
        this.body=body;
        this.width = width;
        this.height = heigth;

        this.body.setUserData(this);

        createBodyFixture();
        createFootFixture();
        createAnim();
        System.out.println(body.getPosition());



    }

    private void createBodyFixture() {
        PolygonShape shape =  new PolygonShape();
        //shape.setAsBox(walkFrameWidth * Box2DTiledMapParserTest.UnitScale, walkFrameHeight*Box2DTiledMapParserTest.UnitScale);
        shape.setAsBox(16 * Box2DTiledMapParserTest.UnitScale, 16 * Box2DTiledMapParserTest.UnitScale);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 1;
        fdef.density = 1;

        body.createFixture(fdef);
        shape.dispose();
    }

    private void createAnim(){
        TextureRegion[][] tmp = TextureRegion.split(walk_sheet, walk_sheet.getWidth()/FRAME_COLUMNS, walk_sheet.getHeight()/FRAME_ROWS);              // #10

        walkFrames = new TextureRegion[FRAME_COLUMNS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLUMNS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.05f,walkFrames);     // #11
        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;
    }

    private void createFootFixture() {
        Fixture BodyFixture = body.getFixtureList().first();

        Vector2 pointb;
    }

    public void update(){

        velocity = body.getLinearVelocity();
        position=body.getPosition();

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16
        spriteBatch.begin();

        //float x=body.getPosition().x-width/2;
        //float y=body.getPosition().y-height/2 ;

        //float x = position.x * Box2DTiledMapParserTest.UnitScale + 10;
        //float y = position.y * Box2DTiledMapParserTest.UnitScale + height / 2;

        float x = position.x * Box2DTiledMapParserTest.UnitScale;
        float y = position.y * Box2DTiledMapParserTest.UnitScale;

        spriteBatch.draw(currentFrame, x, y);
        System.out.println("transform is at: " + body.getPosition());
        System.out.println("X: "+x+" Y: "+y);
        spriteBatch.end();
    }
}
