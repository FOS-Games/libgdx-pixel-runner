package com.FOS.Pixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import net.dermetfan.utils.libgdx.box2d.Box2DUtils;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Lars on 9/24/2014.
 */
public class TestScreen implements Screen{


        private SpriteBatch batch;
        private Box2DDebugRenderer debugRenderer;
        private OrthographicCamera camera;
        private World world;

        @Override
        public void render(float delta) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            world.step(1 / 60f, 8, 3);

            batch.begin();
            Box2DSprite.draw(batch, world);
            batch.end();

            debugRenderer.render(world, camera.combined);
        }

        @Override
        public void resize(int width, int height) {
            camera.viewportWidth = width / 25;
            camera.viewportHeight = height / 25;
            camera.update();

            batch.setProjectionMatrix(camera.combined);
        }

        @Override
        public void show() {
            batch = new SpriteBatch();
            debugRenderer = new Box2DDebugRenderer();
            camera = new OrthographicCamera();

            world = new World(new Vector2(0, -9.81f), true);

            // reusable construction objects
            BodyDef bodyDef = new BodyDef();
            FixtureDef fixtureDef = new FixtureDef();

            // the ground
            bodyDef.type = BodyType.StaticBody;

            EdgeShape groundShape = new EdgeShape();
            groundShape.set(-50, 0, 50, 0);

            fixtureDef.shape = groundShape;
            fixtureDef.restitution = 0;
            fixtureDef.friction = .5f;

            world.createBody(bodyDef).createFixture(fixtureDef);

            groundShape.dispose();

            // a ball
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.y = 10;
            bodyDef.angularVelocity = -10;

            CircleShape ballShape = new CircleShape();
            ballShape.setRadius(.5f);

            fixtureDef.shape = ballShape;
            fixtureDef.density = 2.5f;
            fixtureDef.restitution = .8f;
            fixtureDef.friction = .25f;

            world.createBody(bodyDef).createFixture(fixtureDef).setUserData(new Box2DSprite(new Box2DSprite(new Texture(Gdx.files.internal("DARK.png")))));

//		ballShape.dispose();

            // a box
            bodyDef.position.set(4f, 8);
            bodyDef.angularVelocity = 5;

            PolygonShape boxShape = new PolygonShape();
            boxShape.setAsBox(2.5f, 3);

            fixtureDef.shape = boxShape;
            fixtureDef.density = 5;
            fixtureDef.friction = .4f;
            fixtureDef.restitution = .2f;

            Body box = world.createBody(bodyDef);
            box.createFixture(fixtureDef);
            box.setUserData(new Box2DSprite(new Texture(Gdx.files.internal("DARK.png"))));

            ballShape.setPosition(new Vector2(-Box2DUtils.width(boxShape), 0));;
            fixtureDef.shape = ballShape;

            box.createFixture(fixtureDef);

            boxShape.dispose();
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

        @Override
        public void dispose() {
            batch.dispose();
            debugRenderer.dispose();
            world.dispose();
        }

    }

