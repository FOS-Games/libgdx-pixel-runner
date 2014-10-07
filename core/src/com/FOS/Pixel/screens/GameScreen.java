package com.FOS.Pixel.screens;

import com.FOS.Pixel.*;
import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

    public int orbs = 0;
    SpeedController speedController = new SpeedController();


    // Background stuff
    Texture bgTex;
    Sprite bgSpr;
    Camera fixedCam;
    Integer level;

    public GameScreen(Game game,int level) {
        super(game,level);
        this.level = level;
    }

    @Override
    public void show() {
        createPlayer();
        createCamera();
        createBackground();


        createCollectibles();
        createBoxes();
        speedController.registerController(player);
        speedController.registerController(camera);

        world.setContactListener(pixelContactListener);
        orbs = SaveHandler.getSaveData().getTotalOrbs();
        super.startMusic();

        Timer test = new Timer();
        test.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                speedController.adjustSpeed(new Vector2(10, 0),5);
            }
        },2,2,0);

        test.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                speedController.adjustSpeed(new Vector2(-5, 0),5);
            }
        },15,2,0);
        test.start();



        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

    }

    // TODO : Retrieve out of levelData
    private void createBackground() {
        bgTex = new Texture(Gdx.files.internal(JsonHandler.readLevel(level).getBackground()));
        bgSpr = new Sprite(bgTex);

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
            if(x.key.equals("obstacle")){
                x.value.setUserData(new Box2DSprite(new Texture(Gdx.files.internal("obstacle.png"))));
                for(Fixture fix: x.value.getFixtureList()){
                    fix.setSensor(true);
                    fix.setUserData("obstacle");
                }
            }
        }

    }

    // TODO : Add Orb animation
    private void createCollectibles() {
        ObjectMap<String,Body> bodies = parser.getBodies();
        for (ObjectMap.Entry<String,Body> x : bodies){
            if(x.key.startsWith("orb")){
                //x.value.setUserData(new Box2DSprite(new Texture(Gdx.files.internal("orb.png"))));
                x.value.setUserData(AnimationUtil.createBox2DAnimation(AnimationUtil.createTextureRegion("sprites/spriteSheet_collectible.png", 15, 1), Animation.PlayMode.LOOP));

            }
        }
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

    }

    private void Update() {
//        long millis = TimeUtils.timeSinceMillis(time);
//        long second = (millis / 1000) % 60;
//        long minute = (millis / (1000 * 60)) % 60;
//        long hour = (millis / (1000 * 60 * 60)) % 24;
//
//        String stringtime = String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
//        //System.out.println(stringtime);

        checkCollectedOrbs();
        checkMedalTime(TimeUtils.timeSinceMillis(time));

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

        Array<Body> bodies = pixelContactListener.getBodies();
        for(int i = 0; i < bodies.size; i++) {
            world.destroyBody(bodies.get(i));
            orbs++;
            // play sound
        }

        bodies.clear();
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
    }

    /**
     * Updates Camera
     */
    public void updateCamera() {

        // TODO : Edit the way the camera behaves
//        camera.position.x = player.position.x;
        camera.position.y = player.position.y;
        camera.update();
    }

    @Override
    protected float overrideEarthGravity() {
        return PixelVars.GRAVITY;
    }
}
