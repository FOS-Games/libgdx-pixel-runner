package com.FOS.Pixel.screens;

import com.FOS.Pixel.Data.LevelData;
import com.FOS.Pixel.Data.PixelVars;
import com.FOS.Pixel.PixelContactListener;
import com.FOS.Pixel.Player;
import com.FOS.Pixel.handlers.JsonHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    LevelData levelData;
    private AssetManager assetManager;

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

    public PixelGameScreen(Game game, int level) {

        this.game = game;
        levelData = JsonHandler.readLevel(level);

        // Set up the box2d world and contact listener
        world = new World(new Vector2(0, -overrideEarthGravity()), true);
        box2DRenderer = new Box2DDebugRenderer();

        // NEW
        // Get width and height of application display
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // 30 units on X visible
        // 10 units on Y visible
        camera = new OrthographicCamera(40, 40 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        // END NEW

        // BACKUP
        // camera = new OrthographicCamera();

        map = new TmxMapLoader().load(levelData.getTmxpath());
        parser = new Box2DMapObjectParser(PixelVars.UNITSCALE);

        parser.load(world, map);
        spriteBatch = new SpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());

        assetManager = new AssetManager();
        assetManager.load("flatwound_-_The_Long_Goodbye.mp3", Music.class);
        assetManager.finishLoading();

    }

    @Override
    public void render(float delta) {
        mapRenderer.setView(camera);
        mapRenderer.render();
        box2DRenderer.render(world, camera.combined);


        // render the camera
        camera.update();
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

    protected float overrideEarthGravity() {
        return 9.81f;
    }

    protected void startMusic() {
        if (assetManager.isLoaded("flatwound_-_The_Long_Goodbye.mp3")){
            Music music = assetManager.get("flatwound_-_The_Long_Goodbye.mp3", Music.class);
            music.play();
            music.setLooping(true);
            System.out.println("Music loaded, rock on!");
        }else{
            System.out.println("Music not loaded yet!");
        }
    }
}
