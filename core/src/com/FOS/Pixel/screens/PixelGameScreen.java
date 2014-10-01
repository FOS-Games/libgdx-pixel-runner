package com.FOS.Pixel.screens;

import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import net.dermetfan.gdx.physics.box2d.utils.Box2DMapObjectParser;

/**
 * Created by Lars on 9/19/2014.
 */
public abstract class PixelGameScreen implements Screen {
    public Game game;

    protected World world;
    protected Box2DDebugRenderer box2DRenderer;
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected OrthographicCamera camera;
    protected Box2DMapObjectParser parser;
    protected TiledMap map;
    protected SpriteBatch spriteBatch;
    protected Player player;

    Boolean BOX2DDEBUG = false;


    public Game getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Player getPlayer() {
        return player;
    }

    public PixelGameScreen(Game game){

        this.game=game;
        world = new World(new Vector2(0, -overrideEarthGravity()), true);
        box2DRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        map = new TmxMapLoader().load(getLevelMap());
        parser = new Box2DMapObjectParser(PixelVars.UNITSCALE);

        parser.load(world, map);
        spriteBatch = new SpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());

    }

    @Override
    public void render(float delta) {
        mapRenderer.setView(camera);
        mapRenderer.render();
        box2DRenderer.render(world, camera.combined);

        spriteBatch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        spriteBatch.dispose();
        world.dispose();
        mapRenderer.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    protected float overrideEarthGravity(){
        return 9.81f;
    }
    protected abstract String getLevelMap();
}
