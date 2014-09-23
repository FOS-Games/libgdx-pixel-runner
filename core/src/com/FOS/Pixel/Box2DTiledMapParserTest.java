package com.FOS.Pixel;

import com.FOS.Pixel.screens.PixelScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ObjectMap;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;

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

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), true);
        box2DRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        // set the height of the camera
        camera.position.y = 3;

        TiledMap map = new TmxMapLoader().load("level_1_black.tmx");


        parser = new Box2DMapObjectParser(0.05f);

        parser.load(world, map);
        ObjectMap<String, Body> bodies = parser.getBodies();
        Body playerBody = bodies.get("player");
        if (playerBody != null) {
            System.out.println("Player body found: " + playerBody.getUserData());
            for(Fixture fixture : playerBody.getFixtureList()){
                System.out.println("Fictures found: ");
            }
        }

        mapRenderer = new OrthogonalTiledMapRenderer(map, parser.getUnitScale());
        createPlayer();
    }

    private void createPlayer(){
        BodyDef bdef= new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(parser.getBodies().containsKey("spawn")) {
            System.out.println("Spawn found");
            bdef.position.set(parser.getBodies().get("spawn").getPosition());

        }else{
            bdef.position.set(new Vector2(0,0));
        }
        bdef.fixedRotation = true;

        Body body = world.createBody(bdef);
        player=new Player(body,levelwidth,levelheight);
    }

    @Override
    public void render(float delta) {
        System.out.println("render");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 8, 30);

        mapRenderer.setView(camera);
        mapRenderer.render();
        box2DRenderer.render(world, camera.combined);
        player.update();
        playerMovement();

        updateCamera();
    }

    private void playerMovement() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.body.setLinearVelocity(-4,player.body.getLinearVelocity().y);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.body.setLinearVelocity(player.body.getLinearVelocity().x,4);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.body.setLinearVelocity(4,player.body.getLinearVelocity().y);
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
