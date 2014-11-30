package com.FOS.Pixel;

import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.handlers.JsonHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.OrderedMap;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Lars on 10/10/2014.
 */
public class LevelAssetManager extends AssetManager {

    public OrderedMap<String,TextureRegion[]> TextureRegions = new OrderedMap<String, TextureRegion[]>();
    public OrderedMap<String,AnimatedBox2DSprite> Box2dAnims = new OrderedMap<String, AnimatedBox2DSprite>();
    private static final int COLUMNS = 11;   // amount of columns (vertical) in the spritesheet
    private static final int ROWS = 1;      // amount of rows (horizontal) in the spritesheet

    public void loadSounds(){
       this.load("Sounds/coin1.mp3",Sound.class);
        this.load("Sounds/crash2.mp3",Sound.class);
        this.load("Sounds/death.mp3",Sound.class);
        this.load("Sounds/jump2.mp3",Sound.class);
        this.load("Sounds/pain1.mp3",Sound.class);
        finishLoading();
    }
    public void unloadSounds(){
        this.unload("Sounds/coin1.mp3");
        this.unload("Sounds/crash2.mp3");
        this.unload("Sounds/death.mp3");
        this.unload("Sounds/jump2.mp3");
        this.unload("Sounds/pain1.mp3");
    }

    public void loadGUIAssets(){
        loadGUIButtons();
        this.load("ui/glassPanel.png", Texture.class);
        this.load("ui/glassPanel320x50.png", Texture.class);
        this.load("ui/glassPanelOrbCost.png", Texture.class);
        this.load("ui/glassPanelPlus.png", Texture.class);
        this.load("ui/glassPanelPlusHover.png", Texture.class);
        this.load("ui/glassPanelPlusLocked.png", Texture.class);
        this.load("ui/logo.png", Texture.class);
        this.load("ui/logoBig.png", Texture.class);
        this.load("ui/menuBackground.png", Texture.class);
        this.load("ui/squareShadow.png", Texture.class);
        this.load("ui/squareWhite.png", Texture.class);
        finishLoading();
    }

    public void loadThumbnails(){
        this.load("maps/thumbnails/01_Grasslands.png", Texture.class);
        this.load("maps/thumbnails/01_GrasslandsHover.png", Texture.class);
        this.load("maps/thumbnails/01_GrasslandsLocked.png", Texture.class);
        this.load("maps/thumbnails/02_Cave.png", Texture.class);
        this.load("maps/thumbnails/02_CaveHover.png", Texture.class);
        this.load("maps/thumbnails/02_CaveLocked.png", Texture.class);
        this.load("maps/thumbnails/03_Sand.png", Texture.class);
        this.load("maps/thumbnails/03_SandHover.png", Texture.class);
        this.load("maps/thumbnails/03_SandLocked.png", Texture.class);
        finishLoading();
    }

    public void unloadGUIAssets(){
        unloadGUIButtons();
        this.unload("ui/glassPanel.png");
        this.unload("ui/glassPanel320x50.png");
        this.unload("ui/glassPanelOrbCost.png");
        this.unload("ui/glassPanelPlus.png");
        this.unload("ui/glassPanelPlusHover.png");
        this.unload("ui/glassPanelPlusLocked.png");
        this.unload("ui/logo.png");
        this.unload("ui/logoBig.png");
        this.unload("ui/menuBackground.png");
        this.unload("ui/squareShadow.png");
        this.unload("ui/squareWhite.png");
    }
    public void loadGUIButtons(){
        this.load("ui/blueButton.png", Texture.class);
        this.load("ui/blueButtonHover.png", Texture.class);
        this.load("ui/blueButtonPressed.png", Texture.class);
        finishLoading();
    }

    public void unloadGUIButtons(){
        this.unload("ui/blueButtonHover.png");
        this.unload("ui/blueButtonPressed.png");
    }


    public void loadAssetsLevel1(){
        this.load("maps/backgrounds/bgSky.png", Texture.class);
        loadCrate();
        loadGUIButtons();

        finishLoading();
    }
    public void loadAssetsLevel2(){
        this.load("maps/backgrounds/bgCave.png", Texture.class);
        loadCrate();
        loadGUIButtons();

        finishLoading();

    }
    public void loadAssetsLevel3(){
        this.load("maps/backgrounds/bgSand.png", Texture.class);
        loadCrate();
        loadGUIButtons();

        finishLoading();
    }

    public void loadLevel(int level){
        switch(level){
            case 1: loadAssetsLevel1(); break;
            case 2: loadAssetsLevel2(); break;
            case 3: loadAssetsLevel3(); break;

        }
    }
    private void loadCrate(){
        this.load("obstacle.png", Texture.class);
        this.load("sprites/spriteSheet_box.png",Texture.class);
        finishLoading();

        createBox2DAnimation("crates",createTextureRegion("sprites/spriteSheet_box.png",4,1), Animation.PlayMode.NORMAL);


    }

    public void unloadAssetsLevel1(){
        this.unload("maps/backgrounds/bgSky.png");
        unloadCrate();
        unloadGUIButtons();

    }
    public void unloadAssetsLevel2(){
        this.unload("maps/backgrounds/bgCave.png");
        unloadCrate();
        unloadGUIButtons();


    }
    public void unloadAssetsLevel3(){
        this.unload("maps/backgrounds/bgSand.png");
        unloadCrate();
        unloadGUIButtons();

    }

    public void unloadLevel(int level){
        switch(level){
            case 1: unloadAssetsLevel1(); break;
            case 2: unloadAssetsLevel2(); break;
            case 3: unloadAssetsLevel3(); break;

        }
    }
    private void unloadCrate(){
        this.unload("obstacle.png");
        this.unload("sprites/spriteSheet_box.png");
        //Box2dAnims.remove("crates");



    }

    private void loadWingAnims(){
        loadAnimTextures(PlayerData.AbilityType.JUMP);
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.JUMP.toString(),i).getTexturename();
            createBox2DAnimation("run"+PlayerData.AbilityType.JUMP.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 0, 3), Animation.PlayMode.LOOP);
        }
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.JUMP.toString(),i).getTexturename();
            createBox2DAnimation("jump"+PlayerData.AbilityType.JUMP.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 4, 5), Animation.PlayMode.NORMAL);
        }
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.JUMP.toString(),i).getTexturename();
            createBox2DAnimation("stumble"+PlayerData.AbilityType.JUMP.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 6, 10), Animation.PlayMode.NORMAL);
        }
    }
    private void loadHammerAnims(){
        loadAnimTextures(PlayerData.AbilityType.STRENGTH);
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.STRENGTH.toString(),i).getTexturename();
            createBox2DAnimation("run"+PlayerData.AbilityType.STRENGTH.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 0, 3), Animation.PlayMode.LOOP);
        }
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.STRENGTH.toString(),i).getTexturename();
            createBox2DAnimation("jump"+PlayerData.AbilityType.STRENGTH.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 4, 5), Animation.PlayMode.NORMAL);
        }
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.STRENGTH.toString(),i).getTexturename();
            createBox2DAnimation("stumble"+PlayerData.AbilityType.STRENGTH.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 6, 10), Animation.PlayMode.NORMAL);
        }
    }
    private void loadShoeAnims(){
        loadAnimTextures(PlayerData.AbilityType.SPEED);
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.SPEED.toString(),i).getTexturename();
            createBox2DAnimation("run"+PlayerData.AbilityType.SPEED.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 0, 3), Animation.PlayMode.LOOP);
        }
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.SPEED.toString(),i).getTexturename();
            createBox2DAnimation("jump"+PlayerData.AbilityType.SPEED.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 4, 5), Animation.PlayMode.NORMAL);
        }
        for(int i =0;i<6;i++){
            String texturepath =JsonHandler.getAbiltydata(PlayerData.AbilityType.SPEED.toString(),i).getTexturename();
            createBox2DAnimation("stumble"+PlayerData.AbilityType.SPEED.toString()+i,createTextureRegion(texturepath, COLUMNS, ROWS, 6, 10), Animation.PlayMode.NORMAL);
        }
    }

    private void loadBodyAnims(){
        String texturepath = "sprites/spriteSheet_player.png";
        this.load(texturepath,Texture.class);
        finishLoading();
        ArrayList<String> animSheets = new ArrayList<String>();

            createBox2DAnimation("run"+"Body",createTextureRegion(texturepath, COLUMNS, ROWS, 0, 3), Animation.PlayMode.LOOP);

            createBox2DAnimation("jump"+"Body",createTextureRegion(texturepath, COLUMNS, ROWS, 4, 5), Animation.PlayMode.NORMAL);

            createBox2DAnimation("stumble"+"Body",createTextureRegion(texturepath, COLUMNS, ROWS, 6, 10), Animation.PlayMode.NORMAL);

    }
    private void loadAnimTextures(PlayerData.AbilityType type){
        ArrayList<String> animSheets = new ArrayList<String>();
        for(int i =0;i<6;i++){
            animSheets.add(JsonHandler.getAbiltydata(type.toString(),i).getTexturename());
        }
        HashSet hs = new HashSet();
        hs.addAll(animSheets);
        animSheets.clear();
        animSheets.addAll(hs);

        for(String x : animSheets){
            this.load(x,Texture.class);
        }
        finishLoading();
    }

    public void loadPlayerAnimation(){
        loadBodyAnims();
        loadWingAnims();
        loadShoeAnims();
        loadHammerAnims();

    }

    public void loadOrbAnim(){
        this.load("sprites/spriteSheet_collectible.png",Texture.class);
        finishLoading();
        createBox2DAnimation("orbs",20f,createTextureRegion("sprites/spriteSheet_collectible.png", 15, 1), Animation.PlayMode.LOOP);
    }
    public TextureRegion[] createTextureRegion(String path, int Columns, int Rows){
        TextureRegion[] frames;
        if(TextureRegions.containsKey(path)){
            frames = TextureRegions.get(path);
        }else {
            Texture SpriteSheet = this.get(path,Texture.class);
            TextureRegion[][] tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth() / Columns, SpriteSheet.getHeight() / Rows);
            frames = new TextureRegion[Columns * Rows];
            int index = 0;
            for (int i = 0; i < Rows; i++) {
                for (int j = 0; j < Columns; j++) {
                    frames[index++] = tmp[i][j];
                }
            }

            TextureRegions.put(path,frames);
        }

        return  frames;
    }


    public TextureRegion[] createTextureRegion(String path, int Columns, int Rows, int startFrame, int endFrame){
        TextureRegion[] frames;
        if(TextureRegions.containsKey(path)){
            frames = TextureRegions.get(path);
        }else {
            Texture SpriteSheet = this.get(path,Texture.class);
            TextureRegion[][] tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth() / Columns, SpriteSheet.getHeight() / Rows);

            frames = new TextureRegion[Columns * Rows];
            int index = 0;
            for (int i = 0; i < Rows; i++) {
                for (int j = 0; j < Columns; j++) {
                    frames[index] = tmp[i][j];
                    index++;
                }
            }
            frames = cutFrames(frames,startFrame,endFrame);
        }


        return frames;
    }

    private TextureRegion[] cutFrames(TextureRegion[] fullFrames, int start, int end){
        TextureRegion[] frames = new TextureRegion[(end-start)+1];
        int count = 0;
        for(int i = start; i <=end;i++){
            frames[count] = fullFrames[i];
            count ++;
        }
        return frames;
    }

    public Animation createAnimation(TextureRegion[] textureRegions, Animation.PlayMode playMode){
        Animation animation=new Animation(0.125f, textureRegions) ;
        animation.setPlayMode(playMode);
        return animation;
    }

    public Animation createAnimation(float duration,TextureRegion[] textureRegions, Animation.PlayMode playMode){

        Animation animation=new Animation(0.125f, textureRegions) ;
        animation.setFrameDuration(duration);
        animation.setPlayMode(playMode);
        return animation;
    }

    public AnimatedSprite createAnimatedSprite(TextureRegion[] textureRegions, Animation.PlayMode playMode){
        AnimatedSprite animatedSprite = new AnimatedSprite(createAnimation(textureRegions, playMode));
        return animatedSprite;
    }

    public AnimatedSprite createAnimatedSprite(float duration, TextureRegion[] textureRegions, Animation.PlayMode playMode){
        AnimatedSprite animatedSprite = new AnimatedSprite(createAnimation(duration,textureRegions, playMode));
        return animatedSprite;
    }

    public AnimatedBox2DSprite createBox2DAnimation(String AnimNaam,TextureRegion[] textureRegions, Animation.PlayMode playMode){
        AnimatedBox2DSprite animatedSprite;
        if(Box2dAnims.containsKey(AnimNaam)) {
            animatedSprite = Box2dAnims.get(AnimNaam);
        }else{
            animatedSprite = new AnimatedBox2DSprite(createAnimatedSprite(textureRegions, playMode));
            Box2dAnims.put(AnimNaam,animatedSprite);

        }


        return animatedSprite;
    }

    public AnimatedBox2DSprite createBox2DAnimation(String AnimNaam,float duration, TextureRegion[] textureRegions, Animation.PlayMode playMode){
        AnimatedBox2DSprite animatedSprite;
        if(Box2dAnims.containsKey(AnimNaam)) {
            animatedSprite = Box2dAnims.get(AnimNaam);
        }else{
            animatedSprite = new AnimatedBox2DSprite(createAnimatedSprite(duration, textureRegions, playMode));
            Box2dAnims.put(AnimNaam,animatedSprite);

        }


        return animatedSprite;
    }

    public AnimatedBox2DSprite getAnimation(String name){
//        if(name.equals("crates")&&!Box2dAnims.containsKey(name)){
//
//        }
        AnimatedBox2DSprite anim = Box2dAnims.get(name);
        anim.stop();
        anim.play();

        return anim;
    }

    /**
     * Disposes all assets in the manager and stops all asynchronous loading.
     */
    @Override
    public synchronized void dispose() {

        Box2dAnims.clear();
        TextureRegions.clear();
        super.dispose();
    }
}
