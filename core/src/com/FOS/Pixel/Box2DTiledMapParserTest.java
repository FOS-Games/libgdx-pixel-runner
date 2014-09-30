package com.FOS.Pixel;

import com.FOS.Pixel.Data.AbilityType;
import com.FOS.Pixel.Data.LevelData;
import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.screens.PixelScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.*;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import net.dermetfan.utils.libgdx.graphics.Box2DSprite;


/**
 * Created by Stefan on 19-9-2014.
 */
public class Box2DTiledMapParserTest extends PixelScreen {


    static  final float UnitScale = 0.0625f;


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
            
        Json json= new Json();
//        LevelData test = new LevelData(1,1,1,1,1,1,1,1);
//        String testdata = json.prettyPrint(json.toJson(test, LevelData.class));
//
//
        OrderedMap<String,LevelData> jsonmap = new OrderedMap<String, LevelData>();
//        jsonmap.put("level1",test);
//        jsonmap.put("level2",test);
//        jsonmap.put("level3",test);
//        jsonmap.put("level4",test);
//        jsonmap.put("level5",test);
//        jsonmap.put("level6",test);
//
//        String printdata = json.prettyPrint(jsonmap);
//        FileHandle fileHandle = Gdx.files.local("leveldata.json");
//        fileHandle.writeString(printdata, true);

//        JsonReader reader = new JsonReader();
//        JsonValue values = reader.parse(Gdx.files.local("leveldata.json"));
//        jsonmap = json.fromJson(OrderedMap.class, Gdx.files.local("leveldata.json"));
//        LevelData test = jsonmap.get("level1");
//        String testdata = json.prettyPrint(json.toJson(test, LevelData.class));
//        System.out.println(testdata);

        //JsonHandler.test();
        System.out.println(json.prettyPrint(JsonHandler.getAbiltydata(AbilityType.JUMP,3)));


        world = new World(new Vector2(0, -9.81f), true);
        box2DRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        // set the height of the camera
        camera.position.y = 3;

        TiledMap map = new TmxMapLoader().load("level_1_black.tmx");



        parser = new Box2DMapObjectParser(UnitScale);

        parser.load(world, map);
        ObjectMap<String, Body> bodies = parser.getBodies();
        Body playerBody = bodies.get("player");
        if (playerBody != null) {
            System.out.println("Player body found: " + playerBody.getUserData());
            for(Fixture fixture : playerBody.getFixtureList()){
                System.out.println("Fictures found: ");
            }
        }

        spriteBatch = new SpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());




        createPlayer();

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
        player.testsprite.setWidth(-(camera.position.x-player.position.x));
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

        camera.position.x = player.position.x;
        camera.update();
    }
}
