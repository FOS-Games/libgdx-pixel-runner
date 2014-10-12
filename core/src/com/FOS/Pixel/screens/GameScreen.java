package com.FOS.Pixel.screens;

import com.FOS.Pixel.*;
import com.FOS.Pixel.Data.LevelSaveData;
import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Data.SaveData;
import com.FOS.Pixel.PixelContactListener;
import com.FOS.Pixel.Player;
import com.FOS.Pixel.SpeedController;
import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.TimeUtils;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;


public class GameScreen extends PixelGameScreen {

    long time = System.currentTimeMillis();
    public PixelContactListener pixelContactListener = new PixelContactListener();

    long milligold = (long)levelData.getGoldTime()*1000;
    long millisilver = (long)levelData.getSilverTime()*1000;
    long millibronze = (long)levelData.getBronzeTime()*1000;

    boolean gold = true;
    boolean silver = true;
    boolean bronze=true;
    private Array<Body> playingAnimation = new Array<Body>();
    public int orbs = 0;
    SpeedController speedController = new SpeedController();

    private AssetManager assetManager;
    private Music music;
    private String musicpath;
    public Sound coin = MainPixel.assetManager.get("Sounds/coin1.mp3",Sound.class);
    public Sound crash = MainPixel.assetManager.get("Sounds/crash2.mp3",Sound.class);
    public Sound death =MainPixel.assetManager.get("Sounds/death.mp3",Sound.class);
    public Sound jump = MainPixel.assetManager.get("Sounds/jump2.mp3",Sound.class);
    public Sound pain = MainPixel.assetManager.get("Sounds/pain1.mp3",Sound.class);

    public boolean isDeath=false;

    // Background stuff
    Texture bgTex;
    Sprite bgSpr;
    Camera fixedCam;
    public Integer level;
    private Vector2 finish;

    public GameScreen(Game game,int level) {
        super(game,level);
        MainPixel.assetManager.loadLevel(level);
        this.level = level;
        musicpath=levelData.getMusicpath();
        createBackground();
        createPlayer();
        createCamera();
        createCollectibles();
        createBoxes();
        findFinish();
        assetManager = new AssetManager();
        assetManager.load(musicpath, Music.class);
        assetManager.finishLoading();
        startMusic();
    }

    @Override
    public void show() {



        speedController.registerController(player);
        speedController.registerController(camera);

        world.setContactListener(pixelContactListener);
        //orbs = SaveHandler.getSaveData().getTotalOrbs();

        //player.decrSpeed(new Vector2(10,0),10,0.5f);

        Timer test = new Timer();
        test.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                speedController.adjustSpeed(new Vector2(6*player.getPlayerData().getAbilityData(PlayerData.AbilityType.SPEED).getMultiplier()*Gdx.graphics.getDeltaTime(), 0),5);
            }
        },2,2,0);

        test.start();


        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();


    }

    private void findFinish() {
        ObjectMap<String,Body> bodies = parser.getBodies();
        for (ObjectMap.Entry<String,Body> x : bodies){
            if(x.key.equals("finish")){
                finish=x.value.getPosition();
                System.out.println("Finish found");
            }
    }}

    private void createBackground() {
        bgSpr = new Sprite(MainPixel.assetManager.get(JsonHandler.readLevel(level).getBackground(),Texture.class));

        // A fixed camera to draw the background (+ GUI!)
        fixedCam = new OrthographicCamera();

        // Viewport equals size of the background, so it fits the screen.
        fixedCam.viewportHeight = bgSpr.getHeight();
        fixedCam.viewportWidth = bgSpr.getWidth();

        fixedCam.position.set(fixedCam.viewportWidth * .5f, fixedCam.viewportHeight * .5f, 0f);
        fixedCam.update();
    }

    private void createBoxes() {
        ObjectMap<String,Body> bodies = parser.getBodies();
        for (ObjectMap.Entry<String,Body> x : bodies){
            if(x.key.startsWith("crate")){
                x.value.setUserData(new Box2DSprite(MainPixel.assetManager.get("obstacle.png",Texture.class)));
                for(Fixture fix: x.value.getFixtureList()){
                    fix.setSensor(true);
                    fix.setUserData("crate");
                }
            }
        }

    }

    private void createCollectibles() {
        int orbCounter = 0;

        ObjectMap<String,Body> bodies = parser.getBodies();
        for (ObjectMap.Entry<String,Body> x : bodies){
            if(x.key.startsWith("orb")){
                //x.value.setUserData(new Box2DSprite(new Texture(MainPixel.assetManager.get("orb.png"))));
                x.value.setUserData(MainPixel.assetManager.getAnimation("orbs"));
                orbCounter++;
            }
        }

        System.out.println("Orbs: " + Integer.toString(orbCounter));
    }

    private void createPowerUps() {
        ObjectMap<String,Body> bodies = parser.getBodies();
        for (ObjectMap.Entry<String,Body> x : bodies){
            if(x.key.equals("powerup")){
                for(Fixture fix: x.value.getFixtureList()){
                    fix.setSensor(true);
                    fix.setUserData("powerup");
                }
            }
        }
    }

    /**
     * Create camera at appropriate position.
     */
    private void createCamera(){
        Vector2 spawn;
        if(parser.getBodies().containsKey("spawn")) {
            spawn = parser.getBodies().get("spawn").getPosition();
        }else{
            spawn = new Vector2(0,0); }
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new PlayerCamera(this,spawn,PixelVars.UNITS_VISIBLE, PixelVars.UNITS_VISIBLE * (h / w));
    }
    /**

    /**
     * Create player at appropriate position.
     */
    private void createPlayer(){
        Vector2 spawn;
        if(parser.getBodies().containsKey("spawn")) {
            spawn = parser.getBodies().get("spawn").getPosition();
        }else{
            spawn = new Vector2(0,0); }
        this.player=new Player(this, spawn);
    }

    @Override
    public void render(float delta) {

        Update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 8, 3);


        spriteBatch.setProjectionMatrix(fixedCam.combined);
        spriteBatch.begin();
        bgSpr.draw(spriteBatch);
        spriteBatch.end();


        spriteBatch.setProjectionMatrix(camera.combined);
        super.render(delta);
        spriteBatch.begin();
        //spriteBatch.draw(bg, 0, 0);
        Box2DSprite.draw(spriteBatch, world);
        spriteBatch.end();

        updateCamera();
        camera.update();
        player.update(delta);

        camera.render(delta);

    }

    private void Update() {
//        long millis = TimeUtils.timeSinceMillis(time);
//        long second = (millis / 1000) % 60;
//        long minute = (millis / (1000 * 60)) % 60;
//        long hour = (millis / (1000 * 60 * 60)) % 24;
//
//        String stringtime = String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
//        //System.out.println(stringtime);
        if(!world.isLocked()) {
            checkCollectedOrbs();
            breakAnim();
            checkAnimFinished();
        }
        checkMedalTime(TimeUtils.timeSinceMillis(time));
        checkPlayerFinished();

    }

    private void checkPlayerFinished() {
        if(player.getBody().getPosition().x>finish.x){
            System.out.println("!!!!!!!!!!!!!!!!FINISH!!!!!!!!!!!!!");
        }
    }

    private  void checkMedalTime(long timepassed){

        if (timepassed>= milligold&&gold){
            gold=false;
            System.out.println("No gold");
        }else if(timepassed >= millisilver&&silver){

            silver=false;
            System.out.println("No silver");
        }else if(timepassed >= millibronze&&bronze){

            bronze=false;
            System.out.println("No bronze");
        }
    }

    private void checkCollectedOrbs() {

        Array<Body> bodies = pixelContactListener.getOrbs();
        for(int i = 0; i < bodies.size; i++) {
            coin.play();
            world.destroyBody(bodies.get(i));
            orbs++;
            // play sound
        }

        bodies.clear();
    }

    private void breakAnim() {

        Array<Body> bodies = pixelContactListener.getCrates();
        for(Body crate : bodies) {
            crate.setUserData(MainPixel.assetManager.getAnimation("crates"));
            playingAnimation.add(crate);
            crash.play();
            speedController.adjustSpeed(new Vector2(-(2/player.getPlayerData().getAbilityData(PlayerData.AbilityType.STRENGTH).getMultiplier()),0),5,0.1f);
//            final Timer speedup = new Timer();
//            speedup.scheduleTask(new Timer.Task() {
//                @Override
//                public void run() {
//                    speedController.adjustSpeed(new Vector2(2*player.getPlayerData().getAbilityData(PlayerData.AbilityType.SPEED).getMultiplier(), 0),5);
//                    speedup.stop();
//                }
//
//            },5,2,0);
            player.setState(Player.PLAYER_STATE.STUMBLE);
        }

        bodies.clear();
    }
    private void checkAnimFinished(){
        for(Body crate : playingAnimation){
            AnimatedBox2DSprite anim = ((AnimatedBox2DSprite)crate.getUserData());

                if(anim.isAnimationFinished()){
                    world.destroyBody(crate);
                    playingAnimation.removeValue(crate,true);
                    pain.play();
                }


        }
    }


    @Override
    public void resize(int width, int height) {

        //camera.viewportWidth = width / 25;
        //camera.viewportHeight = height / 25;

        // Every screen sees the same amount of units (fair play!)
        camera.viewportWidth = PixelVars.UNITS_VISIBLE;
        camera.viewportHeight = PixelVars.UNITS_VISIBLE * height/width;
        camera.update();

    }

    @Override
    public void dispose() {
        super.dispose();
        coin.stop();
        crash.stop();
        death.stop();
        jump.stop();
        pain.stop();
        coin.dispose();
        crash.dispose();
        death.dispose();
        jump.dispose();
        pain.dispose();

        music.stop();
        music.dispose();
        camera.dispose();
        MainPixel.assetManager.unloadLevel(level);
        //player.dispose();
    }
    protected void startMusic() {
        if (assetManager.isLoaded(musicpath)){
            music = assetManager.get(musicpath, Music.class);
            music.setVolume(0.1f);
            music.play();

            music.setLooping(true);
            System.out.println("Music loaded, rock on!");
        }else{
            System.out.println("Music not loaded yet!");
        }
    }

    /**
     * Updates Camera
     */
    public void updateCamera() {

        camera.position.x = player.position.x;
        camera.position.y = player.position.y;
        camera.update();
    }

    @Override
    protected float overrideEarthGravity() {
        return PixelVars.GRAVITY;
    }


    public void saveOrbs(){
        SaveData data = SaveHandler.getSaveData();
        data.setTotalOrbs(data.getTotalOrbs()+orbs);
        SaveHandler.Save(data);
        orbs=0;
    }
    public void saveTime(){

        SaveData data = SaveHandler.getSaveData();
        LevelSaveData [] levelSaveDatas = data.getLevelSaveDatas();
        levelSaveDatas[level-1] = levelSaveDatas[level-1].setTotaltime(time);

    }
    public void saveLevelProgress(){
        SaveData data = SaveHandler.getSaveData();
        LevelSaveData [] levelSaveDatas = data.getLevelSaveDatas();
        levelSaveDatas[level] = levelSaveDatas[level].setUnlocked(true);
        saveOrbs();
        saveTime();

    }








}
