package com.FOS.Pixel.screens;

import com.FOS.Pixel.MainPixel;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Stefan on 6-10-2014.
 */
public class MainMenuScreen extends MenuScreen {

    Skin skin;
    Stage stage;
    SpriteBatch batch;
    Game game;

    Texture tBlueButton;
    Texture tBlueButtonHover;
    Texture tBlueButtonPressed;

    Texture background;

    Texture logo;


    BitmapFont font;

    private AssetManager assetManager;
    private Music music;

    public MainMenuScreen(Game game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(800, 480));
        Gdx.input.setInputProcessor(stage);

        assetManager = new AssetManager();
        assetManager.load("Sounds/Pinball Spring (1).mp3", Music.class);
        assetManager.finishLoading();
        startMusic();



        tBlueButton = ((MainPixel)game).assetManager.get("ui/blueButton.png",Texture.class);
        TextureRegion rBlueButton = new TextureRegion(tBlueButton);

        tBlueButtonHover = ((MainPixel)game).assetManager.get("ui/blueButtonHover.png",Texture.class);
        TextureRegion rBlueButtonHover = new TextureRegion(tBlueButtonHover);

        tBlueButtonPressed = ((MainPixel)game).assetManager.get("ui/blueButtonPressed.png",Texture.class);
        TextureRegion rBlueButtonPressed = new TextureRegion(tBlueButtonPressed);

        background = ((MainPixel)game).assetManager.get("ui/menuBackground.png",Texture.class);
        TextureRegion rBackground = new TextureRegion(background);

        logo = ((MainPixel)game).assetManager.get("ui/logoBig.png",Texture.class);
        TextureRegion rLogo = new TextureRegion(logo);

        Image iLogo = new Image(new TextureRegionDrawable(rLogo));
        iLogo.setPosition(112, 350);
        iLogo.setSize(585, 89);

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

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable(new TextureRegionDrawable(rBlueButton));
        textButtonStyle.down = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        textButtonStyle.checked = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        textButtonStyle.over = skin.newDrawable(new TextureRegionDrawable(rBlueButtonHover));
        textButtonStyle.font = skin.getFont("customFont");
        skin.add("default", textButtonStyle);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(rBackground));
        stage.addActor(table);

        final TextButton bStart = new TextButton("Start Game", skin);
        final TextButton bSettings = new TextButton("Settings", skin);
        final TextButton bExit = new TextButton("Exit", skin);

        table.add(bStart).size(220, 50).padBottom(20).padTop(100).row();
        table.add(bSettings).size(220, 50).padBottom(20).row();
        table.add(bExit).size(220, 50).padBottom(20).row();

        stage.addActor(iLogo);


        bStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen(game));
            }
        });

        bSettings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen(game));
            }
        });

        bExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((MainPixel)game).stopGame();
            }
        });

        //table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
    }

    protected void startMusic() {
        if (assetManager.isLoaded("Sounds/Pinball Spring (1).mp3")){
            music = assetManager.get("Sounds/Pinball Spring (1).mp3", Music.class);
            music.setVolume(0.1f);
            music.play();

            music.setLooping(true);
            System.out.println("Music loaded, rock on!");
        }else{
            System.out.println("Music not loaded yet!");
        }
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
        music.stop();
        music.dispose();
        stage.dispose();
        skin.dispose();
        batch.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }

}
