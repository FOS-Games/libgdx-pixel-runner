package com.FOS.Pixel.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Stefan on 6-10-2014.
 */
public class LevelSelectScreen extends MenuScreen {

    Skin skin;
    Stage stage;
    SpriteBatch batch;
    Game game;

    // Textures 01_Grasslands
    Texture tGrasslands;
    Texture tGrasslandsHover;
    Texture tGrasslandsLocked;

    // Textures 02_Cave
    Texture tCave;
    Texture tCaveHover;
    Texture tCaveLocked;

    // Textures 03_Sand
    Texture tSand;
    Texture tSandHover;
    Texture tSandLocked;

    // Menu background
    Texture background;

    // Default blue button
    Texture tBlueButton;
    Texture tBlueButtonHover;
    Texture tBlueButtonPressed;

    GameScreen gameScreen;

    BitmapFont font;


    public LevelSelectScreen(Game game) {
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


        // 01_Grasslands thumbnail
        tGrasslands = new Texture(Gdx.files.internal("maps/thumbnails/01_Grasslands.png"));
        TextureRegion rGrasslands = new TextureRegion(tGrasslands);

        tGrasslandsHover = new Texture(Gdx.files.internal("maps/thumbnails/01_GrasslandsHover.png"));
        TextureRegion rGrasslandsHover = new TextureRegion(tGrasslandsHover);

        tGrasslandsLocked = new Texture(Gdx.files.internal("maps/thumbnails/01_GrasslandsLocked.png"));
        TextureRegion rGrasslandsLocked = new TextureRegion(tGrasslandsLocked);

        // 02_Cave thumbnail
        tCave = new Texture(Gdx.files.internal("maps/thumbnails/02_Cave.png"));
        TextureRegion rCave = new TextureRegion(tCave);

        tCaveHover = new Texture(Gdx.files.internal("maps/thumbnails/02_CaveHover.png"));
        TextureRegion rCaveHover = new TextureRegion(tCaveHover);

        tCaveLocked = new Texture(Gdx.files.internal("maps/thumbnails/02_CaveLocked.png"));
        TextureRegion rCaveLocked = new TextureRegion(tCaveLocked);

        // 03_Sand thumbnail
        tSand = new Texture(Gdx.files.internal("maps/thumbnails/03_Sand.png"));
        TextureRegion rSand= new TextureRegion(tSand);

        tSandHover = new Texture(Gdx.files.internal("maps/thumbnails/03_SandHover.png"));
        TextureRegion rSandHover = new TextureRegion(tSandHover);

        tSandLocked = new Texture(Gdx.files.internal("maps/thumbnails/03_SandHover.png"));
        TextureRegion rSandLocked = new TextureRegion(tSandLocked);



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

        // DEFAULT TextButtonStyle
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        //textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("customFont");
        skin.add("default", textButtonStyle);

        // BLUE TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleBlue = new TextButton.TextButtonStyle();
        textButtonStyleBlue.up = skin.newDrawable(new TextureRegionDrawable(rBlueButton));
        textButtonStyleBlue.down = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        //textButtonStyleBlue.checked = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        textButtonStyleBlue.over = skin.newDrawable(new TextureRegionDrawable(rBlueButtonHover));
        textButtonStyleBlue.font = skin.getFont("customFont");
        skin.add("blueStyle", textButtonStyleBlue);

        // GRASSLANDS TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleGrasslands = new TextButton.TextButtonStyle();
        textButtonStyleGrasslands.up = skin.newDrawable(new TextureRegionDrawable(rGrasslands));
        textButtonStyleGrasslands.down = skin.newDrawable(new TextureRegionDrawable(rGrasslandsHover));
        textButtonStyleGrasslands.down = skin.newDrawable(new TextureRegionDrawable(rGrasslandsHover));
        textButtonStyleGrasslands.over = skin.newDrawable(new TextureRegionDrawable(rGrasslandsHover));
        textButtonStyleGrasslands.font = skin.getFont("customFont");
        skin.add("grasslandsStyle", textButtonStyleGrasslands);

        // CAVE TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleCave = new TextButton.TextButtonStyle();
        textButtonStyleCave.up = skin.newDrawable(new TextureRegionDrawable(rCave));
        textButtonStyleCave.down = skin.newDrawable(new TextureRegionDrawable(rCaveHover));
        textButtonStyleCave.over = skin.newDrawable(new TextureRegionDrawable(rCaveHover));
        textButtonStyleCave.font = skin.getFont("customFont");
        skin.add("caveStyle", textButtonStyleCave);

        // SAND TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleSand = new TextButton.TextButtonStyle();
        textButtonStyleSand.up = skin.newDrawable(new TextureRegionDrawable(rSand));
        textButtonStyleSand.down = skin.newDrawable(new TextureRegionDrawable(rSandHover));
        textButtonStyleSand.over = skin.newDrawable(new TextureRegionDrawable(rSandHover));
        textButtonStyleSand.font = skin.getFont("customFont");
        skin.add("sandStyle", textButtonStyleSand);

        final TextButton bLevel1 = new TextButton("", skin, "grasslandsStyle");
        final TextButton bLevel2 = new TextButton("", skin, "caveStyle");
        final TextButton bLevel3 = new TextButton("", skin, "sandStyle");
        final TextButton bLevel4 = new TextButton("Next\nupdate", skin);
        final TextButton bLevel5 = new TextButton("Next\nupdate", skin);

        final TextButton bBack = new TextButton("Back", skin, "blueStyle");
        final TextButton bAbilities = new TextButton("Abilities", skin, "blueStyle");

        // ScrollPane table
        final Table scrollTable = new Table();
        scrollTable.add(bLevel1).size(200, 200).padLeft(20).padRight(20);
        scrollTable.add(bLevel2).size(200, 200).padLeft(20).padRight(20);
        scrollTable.add(bLevel3).size(200, 200).padLeft(20).padRight(20);
        scrollTable.add(bLevel4).size(200, 200).padLeft(20).padRight(20);
        scrollTable.add(bLevel5).size(200, 200).padLeft(20).padRight(20);

        final ScrollPane scroller = new ScrollPane(scrollTable);

        // Table
        final Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(rBackground));
        table.add(scroller).fill().expand().colspan(2).row();
        table.add(bBack).size(200, 50).bottom().left().padLeft(20).padBottom(20);
        table.add(bAbilities).size(400, 50).bottom().right().padRight(20).padBottom(20);

        // Add table to the stage
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

        bLevel3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 3));
            }
        });

        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        bAbilities.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new AbilityProgressScreen(game));
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
