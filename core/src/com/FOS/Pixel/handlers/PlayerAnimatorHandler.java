package com.FOS.Pixel.handlers;


import com.FOS.Pixel.AnimationUtil;
import com.FOS.Pixel.Data.AbilityData;
import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Data.PlayerData.AbilityType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.OrderedMap;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import net.dermetfan.utils.Pair;

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

    private static final int COLUMNS = 11;   // amount of columns (vertical) in the spritesheet
    private static final int ROWS = 1;      // amount of rows (horizontal) in the spritesheet



    private Animation animation;
    private TextureRegion[] frames;
    private AnimatedBox2DSprite animatedBox2DSprite;

    private OrderedMap<PlayerData.SkinType,String[]> skinTypeStringOrderedMap = new OrderedMap<PlayerData.SkinType, String[]>();
    PlayerData Data;

    OrderedMap<AbilityType,AbilityData> abilitydata = new OrderedMap<AbilityType, AbilityData>();

    protected PlayerAnimatorHandler(){

        skinTypeStringOrderedMap.put(PlayerData.SkinType.HUMAN,new String[]{"sprites/spriteSheet_player.png","sprites/spriteSheet_player.png","sprites/spriteSheet_player.png"});
        skinTypeStringOrderedMap.put(PlayerData.SkinType.ORC,new String[]{"sprites/spriteSheet_player.png","sprites/spriteSheet_player.png","sprites/spriteSheet_player.png"});
        skinTypeStringOrderedMap.put(PlayerData.SkinType.ELF,new String[]{"sprites/spriteSheet_player.png","sprites/spriteSheet_player.png","sprites/spriteSheet_player.png"});

        abilitydata.put(AbilityType.JUMP,Data.getAbilityData(AbilityType.JUMP));
        abilitydata.put(AbilityType.SPEED,Data.getAbilityData(AbilityType.SPEED));
        abilitydata.put(AbilityType.STRENGTH,Data.getAbilityData(AbilityType.STRENGTH));
    }


    protected void InitAnimation(){

        this.Data = getPlayerData();
        System.out.println(Data.toString());

        setRunAnim();


    }
    protected void setRunAnim(){
        OrderedMap<AbilityType,Fixture> fixtureOrderedMap = getFixtures();
        Fixture bodyFixture = getBodyFixture();

        for(AbilityType type : fixtureOrderedMap.keys()){
            String texturepath =abilitydata.get(type).getTexturename();

            fixtureOrderedMap.get(type).setUserData(AnimationUtil.createBox2DAnimation("run"+type.toString(),AnimationUtil.createTextureRegion(texturepath, COLUMNS, ROWS, 0, 3), Animation.PlayMode.LOOP));
        }

        AnimatedBox2DSprite anim= AnimationUtil.createBox2DAnimation("runBody",AnimationUtil.createTextureRegion(skinTypeStringOrderedMap.get(Data.getSkinType())[Data.getPhase()], COLUMNS, ROWS, 0, 3), Animation.PlayMode.LOOP);
        bodyFixture.setUserData(anim);
    }

    protected void setJumpAnim(){
        OrderedMap<AbilityType,Fixture> fixtureOrderedMap = getFixtures();
        Fixture bodyFixture = getBodyFixture();

        for(AbilityType type : fixtureOrderedMap.keys()){
            String texturepath =abilitydata.get(type).getTexturename();
            fixtureOrderedMap.get(type).setUserData(AnimationUtil.createBox2DAnimation("jump"+type.toString(),AnimationUtil.createTextureRegion(texturepath, COLUMNS, ROWS, 4, 5), Animation.PlayMode.NORMAL));
        }

        AnimatedBox2DSprite anim= AnimationUtil.createBox2DAnimation("jumpBody",AnimationUtil.createTextureRegion(skinTypeStringOrderedMap.get(Data.getSkinType())[Data.getPhase()], COLUMNS, ROWS, 4, 5), Animation.PlayMode.NORMAL);
        bodyFixture.setUserData(anim);
    }

    protected void setStumbleAnim(){
        OrderedMap<AbilityType,Fixture> fixtureOrderedMap = getFixtures();
        Fixture bodyFixture = getBodyFixture();

        for(AbilityType type : fixtureOrderedMap.keys()){
            String texturepath =abilitydata.get(type).getTexturename();
            fixtureOrderedMap.get(type).setUserData(AnimationUtil.createBox2DAnimation("jump"+type.toString(),AnimationUtil.createTextureRegion(texturepath, COLUMNS, ROWS, 6, 10), Animation.PlayMode.NORMAL));
        }

        AnimatedBox2DSprite anim= AnimationUtil.createBox2DAnimation("stumbleBody",AnimationUtil.createTextureRegion(skinTypeStringOrderedMap.get(Data.getSkinType())[Data.getPhase()], COLUMNS, ROWS, 6, 10), Animation.PlayMode.NORMAL);

        bodyFixture.setUserData(anim);
    }
    public abstract PlayerData getPlayerData();
    protected abstract OrderedMap<AbilityType,Fixture> getFixtures();
    protected abstract Fixture getBodyFixture();

}
