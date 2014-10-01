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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;


public class GameScreen extends PixelGameScreen {

    int levelwidth;
    int levelheight;

    Timer timer = new Timer();

    public PixelContactListener pixelContactListener = new PixelContactListener();



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
        startMedalTimer();
    }

    private void startMedalTimer() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 8, 3);
        super.render(delta);


        spriteBatch.begin();
        Box2DSprite.draw(spriteBatch, world);
        spriteBatch.end();
        updateCamera();
        player.update(delta);

    }

    @Override
    public void resize(int width, int height) {

        levelwidth = width;
        levelheight = height;
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
        camera.update();

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
