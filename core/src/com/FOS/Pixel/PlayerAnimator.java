package com.FOS.Pixel;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 * Created by Stefan on 29-9-2014.
 */
public abstract class PlayerAnimator {

    private static final int COLUMNS = 4;   // amount of columns (vertical) in the spritesheet
    private static final int ROWS = 1;      // amount of rows (horizontal) in the spritesheet

    private Texture spriteSheet;
    private Animation animation;
    private TextureRegion[] frames;
    private AnimatedBox2DSprite animatedBox2DSprite;

    protected PlayerAnimator(){

    }

    // int body
    //  0 - full body
    //  1 - shoes
    //  2 - wings
    //  3 - weapon

    // int level
    // level of the ability
    protected AnimatedBox2DSprite createAnimation(int body, int level) {

        if(body == 0) {
            // full body
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 1 && level == 0){
            // shoes level 0
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 1 && level == 1){
            // shoes level 1
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 1 && level == 2){
            // shoes level 2
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 1 && level == 3){
            // shoes level 3
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 1 && level == 4){
            // shoes level 4
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 1 && level == 5){
            // shoes level 5
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 2 && level == 0){
            // wings level 0
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 2 && level == 1){
            // wings level 1
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 2 && level == 2){
            // wings level 2
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 2 && level == 3){
            // wings level 3
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 2 && level == 4){
            // wings level 4
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 2 && level == 5) {
            // wings level 5
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 3 && level == 0){
            // weapon level 0
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 3 && level == 1){
            // weapon level 1
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 3 && level == 2){
            // weapon level 2
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 3 && level == 3){
            // weapon level 3
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 3 && level == 4){
            // weapon level 4
            spriteSheet = new Texture("WIPsheet.png");
        }
        else if(body == 3 && level == 5){
            // weapon level 5
            spriteSheet = new Texture("WIPsheet.png");
        }

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/COLUMNS, spriteSheet.getHeight()/ROWS);
        frames = new TextureRegion[COLUMNS * ROWS];

        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        animation = new Animation(0.125f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        animatedBox2DSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
        animatedBox2DSprite.setSize(64, 64);
        return animatedBox2DSprite;
    }
}
