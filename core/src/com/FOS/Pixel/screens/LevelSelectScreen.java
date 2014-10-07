package com.FOS.Pixel.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Stefan on 6-10-2014.
 */
public class LevelSelectScreen extends MenuScreen {

    Skin skin;
    Stage stage;
    SpriteBatch batch;
    Game game;

    public LevelSelectScreen(Game game) {
        this.game = game;
    }

//    @Override
//    public void show() {
//        batch = new SpriteBatch();
//        stage = new Stage(new StretchViewport(640, 480));
//        Gdx.input.setInputProcessor(stage);
//
//        skin = new Skin();
//
//        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//        pixmap.setColor(Color.WHITE);
//        pixmap.fill();
//        skin.add("white", new Texture(pixmap));
//
//        skin.add("default", new BitmapFont());
//
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
//        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
//        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
//        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
//        textButtonStyle.font = skin.getFont("default");
//        skin.add("default", textButtonStyle);
//
//        Table table = new Table();
//        table.setFillParent(true);
//        stage.addActor(table);
//
//        final TextButton bLevel1 = new TextButton("Start level 1", skin);
//        final TextButton bLevel2 = new TextButton("Start level 2", skin);
//
//        table.add(bLevel1).size(200, 50).padBottom(20).row();
//        table.add(bLevel2).size(200, 50).padBottom(20).row();
//
//        bLevel1.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 1));
//            }
//        });
//
//        bLevel2.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 2));
//            }
//        });
//
//        //table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
//    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(640, 480));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        final TextButton bLevel1 = new TextButton("Start level 1", skin);
        final TextButton bLevel2 = new TextButton("Start level 2", skin);
        final TextButton bLevel3 = new TextButton("Start level 3", skin);
        final TextButton bLevel4 = new TextButton("Start level 4", skin);
        final TextButton bLevel5 = new TextButton("Start level 5", skin);


        final Table scrollTable = new Table();
        //table.setFillParent(true);
        scrollTable.add(bLevel1).size(200, 200).padLeft(5).padRight(5);
        scrollTable.add(bLevel2).size(200, 200).padLeft(5).padRight(5);
        scrollTable.add(bLevel3).size(200, 200).padLeft(5).padRight(5);
        scrollTable.add(bLevel4).size(200, 200).padLeft(5).padRight(5);
        scrollTable.add(bLevel5).size(200, 200).padLeft(5).padRight(5);


        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        stage.addActor(table);


        bLevel1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 1));
            }
        });

        bLevel2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 2));
            }
        });

        //table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        batch.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }
}
