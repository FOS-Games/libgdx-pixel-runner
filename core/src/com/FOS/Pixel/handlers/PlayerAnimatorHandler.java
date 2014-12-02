package com.FOS.Pixel.handlers;


import com.FOS.Pixel.Data.AbilityData;
import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Data.PlayerData.AbilityType;
import com.FOS.Pixel.MainPixel;
import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.Game;
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
    Game game;

    OrderedMap<AbilityType,AbilityData> abilitydata = new OrderedMap<AbilityType, AbilityData>();

    protected PlayerAnimatorHandler(Game game){
        this.game=game;
        skinTypeStringOrderedMap.put(PlayerData.SkinType.HUMAN,new String[]{"sprites/spriteSheet_player.png","sprites/spriteSheet_player.png","sprites/spriteSheet_player.png"});
        skinTypeStringOrderedMap.put(PlayerData.SkinType.ORC,new String[]{"sprites/spriteSheet_player.png","sprites/spriteSheet_player.png","sprites/spriteSheet_player.png"});
        skinTypeStringOrderedMap.put(PlayerData.SkinType.ELF,new String[]{"sprites/spriteSheet_player.png","sprites/spriteSheet_player.png","sprites/spriteSheet_player.png"});

    }


    protected void InitAnimation(){

        this.Data = getPlayerData();
        System.out.println(Data.toString());

        abilitydata.put(AbilityType.JUMP,Data.getAbilityData(AbilityType.JUMP));
        abilitydata.put(AbilityType.SPEED,Data.getAbilityData(AbilityType.SPEED));
        abilitydata.put(AbilityType.STRENGTH,Data.getAbilityData(AbilityType.STRENGTH));
        setRunAnim();


    }
    protected void setRunAnim(){
        OrderedMap<AbilityType,Fixture> fixtureOrderedMap = getFixtures();
        Fixture bodyFixture = getBodyFixture();

        for(AbilityType type : fixtureOrderedMap.keys()){

            String texturepath =abilitydata.get(type).getTexturename();

            fixtureOrderedMap.get(type).setUserData(((MainPixel)game).assetManager.getAnimation("run"+type.toString()+getTypeLevel(type)));
        }

        AnimatedBox2DSprite anim= ((MainPixel)game).assetManager.getAnimation("runBody");
        bodyFixture.setUserData(anim);
    }

    protected void setJumpAnim(){
        OrderedMap<AbilityType,Fixture> fixtureOrderedMap = getFixtures();
        Fixture bodyFixture = getBodyFixture();

        for(AbilityType type : fixtureOrderedMap.keys()){
            String texturepath =abilitydata.get(type).getTexturename();
            fixtureOrderedMap.get(type).setUserData(((MainPixel)game).assetManager.getAnimation("jump"+type.toString()+getTypeLevel(type)));
        }


        AnimatedBox2DSprite anim= ((MainPixel)game).assetManager.getAnimation("jumpBody");
        bodyFixture.setUserData(anim);
    }
    private int getTypeLevel(AbilityType type){
        switch (type){
            case JUMP: return Data.getAgilityLevel();
            case SPEED: return Data.getSpeedLevel();
            case STRENGTH: return Data.getStrengthLevel();
            default: return 0;
        }
    }

    protected void setStumbleAnim(){
        OrderedMap<AbilityType,Fixture> fixtureOrderedMap = getFixtures();
        Fixture bodyFixture = getBodyFixture();

        for(AbilityType type : fixtureOrderedMap.keys()){
            String texturepath =abilitydata.get(type).getTexturename();
            AnimatedBox2DSprite animatedBox2DSprite1 = ((MainPixel)game).assetManager.getAnimation("stumble"+type.toString()+getTypeLevel(type));
            fixtureOrderedMap.get(type).setUserData(animatedBox2DSprite1);
            animatedBox2DSprite1.play();
        }

        AnimatedBox2DSprite anim= ((MainPixel)game).assetManager.getAnimation("stumbleBody");

        bodyFixture.setUserData(anim);
    }
    public abstract PlayerData getPlayerData();
    protected abstract OrderedMap<AbilityType,Fixture> getFixtures();
    protected abstract Fixture getBodyFixture();

}
