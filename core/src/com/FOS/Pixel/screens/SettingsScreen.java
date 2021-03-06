package com.FOS.Pixel.screens;

import com.FOS.Pixel.MainPixel;
import com.FOS.Pixel.handlers.SaveHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Stefan on 8-10-2014.
 */
public class SettingsScreen extends MenuScreen{

    Stage stage;
    SpriteBatch batch;
    Game game;

    Texture tBlueButton;
    Texture tBlueButtonHover;
    Texture tBlueButtonPressed;

    Texture background;


    BitmapFont font;

    Skin skin;

    public SettingsScreen(Game game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(800, 480));
        Gdx.input.setInputProcessor(stage);

        tBlueButton = ((MainPixel)game).assetManager.get("ui/blueButton.png",Texture.class);
        TextureRegion rBlueButton = new TextureRegion(tBlueButton);

        tBlueButtonHover = ((MainPixel)game).assetManager.get("ui/blueButtonHover.png",Texture.class);
        TextureRegion rBlueButtonHover = new TextureRegion(tBlueButtonHover);

        tBlueButtonPressed = ((MainPixel)game).assetManager.get("ui/blueButtonPressed.png",Texture.class);
        TextureRegion rBlueButtonPressed = new TextureRegion(tBlueButtonPressed);

        background = ((MainPixel)game).assetManager.get("ui/menuBackground.png",Texture.class);
        TextureRegion rBackground = new TextureRegion(background);

        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());
        // Create ability font
        FreeTypeFontGenerator generatorAbility = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterAbility = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterAbility.size = 24;
        font = generatorAbility.generateFont(parameterAbility);
        //abilityFont.dispose();
        skin.add("customFont", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable(new TextureRegionDrawable(rBlueButton));
        textButtonStyle.down = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        //textButtonStyle.checked = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        textButtonStyle.over = skin.newDrawable(new TextureRegionDrawable(rBlueButtonHover));
        textButtonStyle.font = skin.getFont("customFont");
        skin.add("default", textButtonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("customFont");
        skin.add("default", labelStyle);

        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = skin.getFont("customFont");
        windowStyle.stageBackground = skin.newDrawable("white", 0, 0, 0, 0.7f);
        skin.add("dialog", windowStyle);


        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(rBackground));
        stage.addActor(table);

        final TextButton bReset = new TextButton("Reset", skin);
        final TextButton bBack= new TextButton("Back", skin);

        table.add(bReset).size(200, 50).padBottom(20).row();
        table.add(bBack).size(200, 50).padBottom(20).row();

        bReset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new Dialog("", skin, "dialog"){
                    protected void result (Object object) {
                       if (object.equals(true)) {
                           SaveHandler.ResetSave();
                           System.out.println("RESET SAVE");
                       }
                    }
                }.text("Are you sure you\nwant to reset your\ncharacter progress?").button("Yes", true).button("No", false).show(stage);
            }
        });

        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
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
