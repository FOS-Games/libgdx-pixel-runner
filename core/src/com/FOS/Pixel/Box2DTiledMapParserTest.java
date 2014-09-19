package com.FOS.Pixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

/**
 * Created by Stefan on 19-9-2014.
 */
public class Box2DTiledMapParserTest implements Screen{

    private World world;
    private Box2DDebugRenderer box2DRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), true);
        box2DRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        TiledMap map = new TmxMapLoader().load("maps/map1.tmx");

        Box2DMapObjectParser parser = new Box2DMapObjectParser();

        parser.load(world, map);

        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 8, 30);

        mapRenderer.setView(camera);
        mapRenderer.render();
        box2DRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
        camera.update();
    }

    @Override
    public void dispose()
    {
        world.dispose();
        mapRenderer.dispose();
    }

    @Override
    public void hide()
    {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
