package com.FOS.Pixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

/**
 * Created by Lars on 10/6/2014.
 */
public class AnimationUtil {

    public static Animation createAnimation(){
        //TODO: animation creation
        throw new UnsupportedOperationException();
    }
    public static AnimatedSprite createAnimatedSprite(){
        //TODO: create animatedsprite
        throw new UnsupportedOperationException();
    }
    public static AnimatedBox2DSprite createBox2DAnimation(){
        //TODO: create box2dAnimation
        throw new UnsupportedOperationException();
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
        ArrayList framesList = new ArrayList();
        TextureRegion[] frames = new TextureRegion[1];
        int index=0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                framesList.add(tmp[i][j]);
            }
        }

        if(endFrame == (Columns*Rows)-1){}
        else if(endFrame == (Columns*Rows)-2){
            framesList.remove((Columns*Rows-1));
        }else if (endFrame <(Columns*Rows)-2){
            for(int i = endFrame; i<(Columns*Rows)-1; i++ ){

                framesList.remove(i);
            }
        }
        if(startFrame == 1){
            framesList.remove(0);
        }
        else if (startFrame>1){
            for(int i = 0; i<startFrame;i++){
                framesList.remove(i);
            }
        }

        frames = (TextureRegion[])framesList.toArray(new TextureRegion[0]);

//        System.out.println("Length = "+frames.length);
//        int count = 0;
//        for(TextureRegion x : frames){
//            System.out.println(x.toString()+" aanwezig op "+ count);
//        }

        return  frames;
    }
}
