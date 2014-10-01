package com.FOS.Pixel.screens;

import com.FOS.Pixel.Player;
import com.FOS.Pixel.screens.PixelGameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;


/**
 * Created by Stefan on 19-9-2014.
 */
public class GameScreen extends PixelGameScreen {

    int levelwidth;
    int levelheight;


    public GameScreen(Game game) {
        super(game);
    }
    @Override
    public void show() {
        createPlayer();

        // set camera zoom
        camera.zoom = 2.5f;
    }
    @Override
    protected String getLevelMap() {
        return "level_2_grass.tmx";
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
        player=new Player(this.world, spawn);
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
}
