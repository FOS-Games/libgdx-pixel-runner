package com.FOS.Pixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import java.util.ArrayList;

/**
 * Created by Lars on 10/6/2014.
 */
public class AnimationUtil {

    public static Animation createAnimation(TextureRegion[] textureRegions, Animation.PlayMode playMode){
        Animation animation=new Animation(0.125f, textureRegions) ;
        animation.setPlayMode(playMode);
        return animation;
    }

    public static Animation createAnimation(float duration,TextureRegion[] textureRegions, Animation.PlayMode playMode){

        Animation animation=new Animation(0.125f, textureRegions) ;
        animation.setPlayMode(playMode);
        return animation;
    }

    public static AnimatedSprite createAnimatedSprite(TextureRegion[] textureRegions, Animation.PlayMode playMode){
        AnimatedSprite animatedSprite = new AnimatedSprite(createAnimation(textureRegions, playMode));
        return animatedSprite;
    }

    public static AnimatedSprite createAnimatedSprite(float duration, TextureRegion[] textureRegions, Animation.PlayMode playMode){
        AnimatedSprite animatedSprite = new AnimatedSprite(createAnimation(duration,textureRegions, playMode));
        return animatedSprite;
    }

    public static AnimatedBox2DSprite createBox2DAnimation(TextureRegion[] textureRegions, Animation.PlayMode playMode){
        AnimatedBox2DSprite animatedSprite = new AnimatedBox2DSprite(createAnimatedSprite(textureRegions, playMode));
        return animatedSprite;
    }

    public static AnimatedBox2DSprite createBox2DAnimation(float duration, TextureRegion[] textureRegions, Animation.PlayMode playMode){
        AnimatedBox2DSprite animatedSprite = new AnimatedBox2DSprite(createAnimatedSprite(duration, textureRegions, playMode));
        return animatedSprite;
    }

    public static TextureRegion[] createTextureRegion(Texture SpriteSheet, int Columns, int Rows){
        TextureRegion[][] tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth()/Columns, SpriteSheet.getHeight()/Rows);
        TextureRegion[] frames = new TextureRegion[Columns*Rows];
        int index=0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return  frames;
    }

    public static TextureRegion[] createTextureRegion(Texture SpriteSheet, int Columns, int Rows, int startFrame, int endFrame){


        TextureRegion[][] tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth()/Columns, SpriteSheet.getHeight()/Rows);
        TextureRegion[] frames = new TextureRegion[Columns*Rows];
        int index=0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                frames[index++]=tmp[i][j];
            }
        }

        return  cutFrames(frames,startFrame,endFrame);
    }

    public static TextureRegion[] createTextureRegion(FileHandle fileHandle, int Columns, int Rows){
        Texture SpriteSheet = new Texture(fileHandle);
        TextureRegion[][] tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth()/Columns, SpriteSheet.getHeight()/Rows);
        TextureRegion[] frames = new TextureRegion[Columns*Rows];
        int index=0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return  frames;
    }

    public static TextureRegion[] createTextureRegion(FileHandle fileHandle, int Columns, int Rows, int startFrame, int endFrame){

        Texture SpriteSheet = new Texture(fileHandle);
        TextureRegion[][] tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth()/Columns, SpriteSheet.getHeight()/Rows);
        TextureRegion[] frames = new TextureRegion[Columns*Rows];
        int index=0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                frames[index++]=tmp[i][j];
            }
        }

        return  cutFrames(frames,startFrame,endFrame);
    }
    public static TextureRegion[] createTextureRegion(String path, int Columns, int Rows){
        Texture SpriteSheet = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth()/Columns, SpriteSheet.getHeight()/Rows);
        TextureRegion[] frames = new TextureRegion[Columns*Rows];
        int index=0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return  frames;
    }

    public static TextureRegion[] createTextureRegion(String path, int Columns, int Rows, int startFrame, int endFrame){
        Texture SpriteSheet = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth()/Columns, SpriteSheet.getHeight()/Rows);

        TextureRegion[] frames = new TextureRegion[Columns*Rows];
        int index=0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                frames[index]=tmp[i][j];
                index++;
            }
        }


        return  cutFrames(frames,startFrame,endFrame);
    }

    private static TextureRegion[] cutFrames(TextureRegion[] fullFrames, int start, int end){
        TextureRegion[] frames = new TextureRegion[(end-start)+1];
        int count = 0;
        for(int i = start; i <=end;i++){
            frames[count] = fullFrames[i];
            count ++;
        }
        return frames;
    }

}
