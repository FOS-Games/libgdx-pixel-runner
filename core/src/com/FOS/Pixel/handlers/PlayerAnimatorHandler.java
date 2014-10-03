package com.FOS.Pixel.handlers;


import com.FOS.Pixel.Data.AbilityData;
import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Data.PlayerData.AbilityType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.OrderedMap;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 * Created by Stefan on 29-9-2014.
 */
public abstract class PlayerAnimatorHandler {

    public enum BodyPart{
        BODY,
        FEET,
        WINGS,
        WEAPON
    }

    private static final int COLUMNS = 6;   // amount of columns (vertical) in the spritesheet
    private static final int ROWS = 1;      // amount of rows (horizontal) in the spritesheet

    private Animation animation;
    private TextureRegion[] frames;
    private AnimatedBox2DSprite animatedBox2DSprite;

    private OrderedMap<PlayerData.SkinType,String> skinTypeStringOrderedMap = new OrderedMap<PlayerData.SkinType, String>();

    protected PlayerAnimatorHandler(){

        skinTypeStringOrderedMap.put(PlayerData.SkinType.HUMAN,"sprites/spriteSheet_player.png");
        skinTypeStringOrderedMap.put(PlayerData.SkinType.ORC,"sprites/spriteSheet_player.png");
        skinTypeStringOrderedMap.put(PlayerData.SkinType.ELF,"sprites/spriteSheet_player.png");

    }

    // int body
    //  0 - full body
    //  1 - shoes
    //  2 - wings
    //  3 - weapon

    // int level
    // level of the ability
    protected AnimatedBox2DSprite createAnimation(Texture spriteSheet) {

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/COLUMNS, spriteSheet.getHeight()/ROWS);
        frames = new TextureRegion[COLUMNS * ROWS];

        int index = 0;

        // Running animation only has 4 frames, spritesheet has 6 frames.
        // Therefore i < COLUMNS becomes: i < 4.
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < 4; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // Create the animation
        animation = new Animation(0.125f, frames);

        // Loop the animation till infinity (and beyond!)
        animation.setPlayMode(Animation.PlayMode.LOOP);

        // Create a Box2DSprite out of the animation
        animatedBox2DSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));

        // Properties of the Box2DSprite
        //animatedBox2DSprite.setScale(0.1f);
        //animatedBox2DSprite.setAdjustSize(false);
        //animatedBox2DSprite.scale(Box2DTiledMapParserTest.UnitScale);
        //animatedBox2DSprite.setUseFrameRegionSize(true);


        return animatedBox2DSprite;
    }

    protected void InitAnimation(){

        PlayerData Data = getPlayerData();
        System.out.println(Data.toString());
        OrderedMap<AbilityType,Fixture> fixtureOrderedMap = getFixtures();
        Fixture bodyFixture = getBodyFixture();

        for(AbilityType type : fixtureOrderedMap.keys()){
            AbilityData abilityData = Data.getAbilityData(type);
            String texturepath =abilityData.getTexturename();
            System.out.println(texturepath);
            Texture texture = new Texture(Gdx.files.internal(texturepath));
            fixtureOrderedMap.get(type).setUserData(createAnimation(texture));
        }
        bodyFixture.setUserData(createAnimation(new Texture(Gdx.files.internal(skinTypeStringOrderedMap.get(Data.getSkinType())))));


    }
    protected abstract PlayerData getPlayerData();
    protected abstract OrderedMap<AbilityType,Fixture> getFixtures();
    protected abstract Fixture getBodyFixture();
}
