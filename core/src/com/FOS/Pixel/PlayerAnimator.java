package com.FOS.Pixel;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

/**
 * Created by Stefan on 29-9-2014.
 */
public abstract class PlayerAnimator {

    private static final int COLUMNS = 4;
    private static final int ROWS = 1;

    private Texture spriteSheet;
    private Animation animation;
    private TextureRegion[] frames;
    private AnimatedBox2DSprite animatedBox2DSprite;

    protected PlayerAnimator(){
        createAnimation();
    }

    protected AnimatedBox2DSprite createAnimation() {
        spriteSheet = new Texture("WIPsheet.png");
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
        return animatedBox2DSprite;
    }

    protected AnimatedBox2DSprite createFeetAnimation() {
        spriteSheet = new Texture("WIPsheet_shoes.png");
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
        return animatedBox2DSprite;
    }


    private void setAbilityLevels(int spe, int agi, int str){

    }
}
