package com.FOS.Pixel.screens;

import com.FOS.Pixel.AnimationUtil;
import com.FOS.Pixel.PlayerProp;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 * Created by Stefan on 7-10-2014.
 */
public class AbilityProgressScreen extends MenuScreen {

    Skin skin;
    Stage stage;
    SpriteBatch batch;
    Game game;

    // Menu background
    Texture background;

    // Default blue button
    Texture tBlueButton;
    Texture tBlueButtonHover;
    Texture tBlueButtonPressed;

    GameScreen gameScreen;


    public AbilityProgressScreen(Game game, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.game = game;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(800, 480));
        Gdx.input.setInputProcessor(stage);

        // Background
        background = new Texture(Gdx.files.internal("ui/menuBackground.png"));
        TextureRegion rBackground = new TextureRegion(background);

        // Default blue buttons
        tBlueButton = new Texture(Gdx.files.internal("ui/blueButton.png"));
        TextureRegion rBlueButton = new TextureRegion(tBlueButton);

        tBlueButtonHover = new Texture(Gdx.files.internal("ui/blueButtonHover.png"));
        TextureRegion rBlueButtonHover = new TextureRegion(tBlueButtonHover);

        tBlueButtonPressed = new Texture(Gdx.files.internal("ui/blueButtonPressed.png"));
        TextureRegion rBlueButtonPressed = new TextureRegion(tBlueButtonPressed);


        // Get playerSprite
        PlayerProp playerProp = new PlayerProp(gameScreen);
        Animation animation = playerProp.getAnimation();

        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());

        // DEFAULT TextButtonStyle
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        // BLUE TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleBlue = new TextButton.TextButtonStyle();
        textButtonStyleBlue.up = skin.newDrawable(new TextureRegionDrawable(rBlueButton));
        textButtonStyleBlue.down = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        textButtonStyleBlue.checked = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        textButtonStyleBlue.over = skin.newDrawable(new TextureRegionDrawable(rBlueButtonHover));
        textButtonStyleBlue.font = skin.getFont("default");
        skin.add("blueStyle", textButtonStyleBlue);


        final TextButton bBack = new TextButton("Back", skin, "blueStyle");

        // Player table and container
        final Table playerTable = new Table();
        playerTable.setFillParent(true);
        playerTable.add(new Image(new SpriteDrawable(new AnimatedSprite(animation))));
        final Container playerContainer = new Container(playerTable);

        // Ability table and container
        final Table abilityTable = new Table();
        //abilityTable.setFillParent(true);
        abilityTable.add(new Image(playerProp.getAnimatedSprite()));
        final Container abilityContainer = new Container(abilityTable);



        // Table
        final Table table = new Table();
        table.debug();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(rBackground));
        //table.add(new Image(playerProp.getAnimation()));
        table.add(playerContainer).size(200, 200).top().left().padTop(20).padLeft(20);
        table.add(abilityContainer).size(200, 200).top().right().padTop(20).padRight(20);
        table.row();
        table.add(bBack).size(200, 50).bottom().left().padLeft(20).padBottom(20).colspan(2);

        // Add table to the stage
        stage.addActor(table);

        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen(game, gameScreen));
            }
        });
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
