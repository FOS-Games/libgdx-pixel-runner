package com.FOS.Pixel.screens;

import com.FOS.Pixel.Data.LevelData;
import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.PixelContactListener;
import com.FOS.Pixel.Player;
import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.screens.PixelGameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;


public class GameScreen extends PixelGameScreen {

    int levelwidth;
    int levelheight;
    long time = System.currentTimeMillis();
    public PixelContactListener pixelContactListener = new PixelContactListener();

    long milligold = (long)levelData.getGoldTime()*1000;
    long millisilver = (long)levelData.getSilverTime()*1000;
    long millibronze = (long)levelData.getBronzeTime()*1000;

    boolean gold = true;
    boolean silver = true;
    boolean bronze=true;




    public GameScreen(Game game,int level) {
        super(game,level);
    }
    @Override
    public void show() {
        createPlayer();

        world.setContactListener(pixelContactListener);

        // set camera zoom
        camera.zoom = 2.5f;

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
        player=new Player(this, spawn);
    }

    @Override
    public void render(float delta) {

        Update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 8, 3);
        super.render(delta);





        spriteBatch.begin();
        Box2DSprite.draw(spriteBatch, world);
        spriteBatch.end();
        updateCamera();
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


    @Override
    public void resize(int width, int height) {

        levelwidth = width;
        levelheight = height;
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
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
        camera.position.x = player.position.x;
        camera.position.y = player.position.y;
        camera.update();
    }

    @Override
    protected float overrideEarthGravity() {
        return 50f;
    }
}
