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
    Body body;
    Texture walk_sheet = new Texture(Gdx.files.internal("sprite-animation1.png"));
    float walkFrameWidth = walk_sheet.getWidth()/FRAME_COLUMNS;
    float walkFrameHeight = walk_sheet.getHeight()/FRAME_ROWS;
    AnimatedBox2DSprite walkAnimation;
    SpriteBatch spriteBatch;
    float stateTime;
    TextureRegion currentFrame;
    TextureRegion[] walkFrames;
    World world;
    Fixture bodyFixture;

    public Player(Body body, World world){
        this.body=body;

        createBodyFixture();
        createAnim();
        System.out.println(body.getPosition());



    }

    private void createBodyFixture() {
        PolygonShape shape =  new PolygonShape();
        shape.setAsBox(walkFrameWidth * Box2DTiledMapParserTest.UnitScale, walkFrameHeight*Box2DTiledMapParserTest.UnitScale);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 1;
        fdef.density = 1;

        bodyFixture = body.createFixture(shape,1);
        shape.dispose();
    }

    //create player animation
    private void createAnim(){
        TextureRegion[][] tmp = TextureRegion.split(walk_sheet, walk_sheet.getWidth() / FRAME_COLUMNS, walk_sheet.getHeight() / FRAME_ROWS);

        //puts frames in TextureRegion array
        walkFrames = new TextureRegion[FRAME_COLUMNS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLUMNS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        //creating animatedbox2dsprite
        Animation animation = new Animation(0.05f,walkFrames);
        AnimatedSprite animatedSprite = new AnimatedSprite(animation);
        walkAnimation =new AnimatedBox2DSprite( animatedSprite);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    public void update(float dt){

        velocity = body.getLinearVelocity();
        position=body.getPosition();

//        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//        stateTime += Gdx.graphics.getDeltaTime();           // #15
//        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16

//        float x=body.getPosition().x/Box2DTiledMapParserTest.UnitScale;
//        float y=body.getPosition().y/Box2DTiledMapParserTest.UnitScale;
//        Box2DSprite.draw(currentFrame,body);
//        System.out.println("transform is at: " + body.getTransform().getPosition());
//        System.out.println("X: "+x+" Y: "+y);
         Box2DSprite sprite = new Box2DSprite(walkAnimation.getAnimation().getKeyFrame(stateTime,true));
        spriteBatch.begin();
            Box2DSprite.draw(spriteBatch,world);
            sprite.draw(spriteBatch,body);
        spriteBatch.end();
    }
}
