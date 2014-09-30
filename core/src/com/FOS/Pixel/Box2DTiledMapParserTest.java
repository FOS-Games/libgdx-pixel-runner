package com.FOS.Pixel;

import com.FOS.Pixel.Data.AbilityType;
import com.FOS.Pixel.Data.LevelData;
import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.screens.PixelScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.*;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import net.dermetfan.gdx.physics.box2d.utils.Box2DMapObjectParser;


/**
 * Created by Stefan on 19-9-2014.
 */
public class Box2DTiledMapParserTest extends PixelScreen {


    //static  final float UnitScale = 0.0625f;

    // 1 tile is roughly 2 meters
    static final float UnitScale = 1/35f;

    private World world;
    private Box2DDebugRenderer box2DRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Box2DMapObjectParser parser;
    public Player player;
    public Box2DTiledMapParserTest(Game game) {
        super(game);
    }
    int levelwidth;
    int levelheight;
    SpriteBatch spriteBatch;

    @Override
    public void show() {

        world = new World(new Vector2(0, -player.GRAVITY), true);

        world = new World(new Vector2(0, -9.81f), true);
        box2DRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        TiledMap map = new TmxMapLoader().load("level_2_grass.tmx");

        parser = new Box2DMapObjectParser(UnitScale);

        parser.load(world, map);
        ObjectMap<String, Body> bodies = parser.getBodies();
        Body playerBody = bodies.get("player");
        if (playerBody != null) {
            System.out.println("Player body found: " + playerBody.getUserData());
            for(Fixture fixture : playerBody.getFixtureList()){
                System.out.println("Fixtures found: ");
            }
        }

        spriteBatch = new SpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());

        createPlayer();

        // set camera zoom
        camera.zoom = 2.5f;
    }

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

        mapRenderer.setView(camera);
        mapRenderer.render();
        box2DRenderer.render(world, camera.combined);

        spriteBatch.setProjectionMatrix(camera.combined);
        updateCamera();

        spriteBatch.begin();
        Box2DSprite.draw(spriteBatch, world);
        spriteBatch.end();

        //player.testsprite.setWidth(-(camera.position.x-player.position.x));
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void updateCamera() {

        // TODO : Edit the way the camera behaves
        camera.position.x = player.position.x;
        camera.position.y = player.position.y;
        camera.update();
    }
}
