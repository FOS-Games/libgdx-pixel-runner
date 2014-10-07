package com.FOS.Pixel.screens;

import com.FOS.Pixel.AnimationUtil;
import com.FOS.Pixel.Data.AbilityData;
import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.PlayerProp;
import com.FOS.Pixel.handlers.JsonHandler;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
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

    // Ability point textures
    Texture tGlassPanel;
    Texture tSquareShadow;
    Texture tSquareWhite;
    Texture tGlassPanelPlus;
    Texture tGlassPanelPlusHover;
    Texture tGlassPanelPlusLocked;

    GameScreen gameScreen;

    PlayerProp playerProp;


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


        // Ability point UI
        tGlassPanel = new Texture(Gdx.files.internal("ui/glassPanel300x50.png"));
        TextureRegion rGlassPanel = new TextureRegion(tGlassPanel);

        tSquareShadow = new Texture(Gdx.files.internal("ui/squareShadow.png"));
        TextureRegion rSquareShadow = new TextureRegion(tSquareShadow);

        tSquareWhite = new Texture(Gdx.files.internal("ui/squareWhite.png"));
        final TextureRegion rSquareWhite = new TextureRegion(tSquareWhite);

        tGlassPanelPlus = new Texture(Gdx.files.internal("ui/glassPanelPlus.png"));
        TextureRegion rGlassPanelPlus = new TextureRegion(tGlassPanelPlus);

        tGlassPanelPlusHover = new Texture(Gdx.files.internal("ui/glassPanelPlusHover.png"));
        TextureRegion rGlassPanelPlusHover = new TextureRegion(tGlassPanelPlusHover);

        tGlassPanelPlusLocked = new Texture(Gdx.files.internal("ui/glassPanelPlusLocked.png"));
        TextureRegion rGlassPanelPlusLocked = new TextureRegion(tGlassPanelPlusLocked);



        // Get playerSprite
        playerProp = new PlayerProp(gameScreen);
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
        //textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        // BLUE TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleBlue = new TextButton.TextButtonStyle();
        textButtonStyleBlue.up = skin.newDrawable(new TextureRegionDrawable(rBlueButton));
        textButtonStyleBlue.down = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        //textButtonStyleBlue.checked = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        textButtonStyleBlue.over = skin.newDrawable(new TextureRegionDrawable(rBlueButtonHover));
        textButtonStyleBlue.font = skin.getFont("default");
        skin.add("blueStyle", textButtonStyleBlue);

        // GLASSPANEL PLUS TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleGlassPanelPlus = new TextButton.TextButtonStyle();
        textButtonStyleGlassPanelPlus.up = skin.newDrawable(new TextureRegionDrawable(rGlassPanelPlus));
        textButtonStyleGlassPanelPlus.down = skin.newDrawable(new TextureRegionDrawable(rGlassPanelPlusHover));
        //textButtonStyleGlassPanelPlus.checked = skin.newDrawable(new TextureRegionDrawable(rGlassPanelPlusHover));
        textButtonStyleGlassPanelPlus.over = skin.newDrawable(new TextureRegionDrawable(rGlassPanelPlusHover));
        textButtonStyleGlassPanelPlus.font = skin.getFont("default");
        skin.add("glassPanelStyle", textButtonStyleGlassPanelPlus);


        final TextButton bBack = new TextButton("Back", skin, "blueStyle");

        final TextButton bStrengthPlus = new TextButton("", skin, "glassPanelStyle");
        final TextButton bSpeedPlus = new TextButton("", skin, "glassPanelStyle");
        final TextButton bAgilityPlus = new TextButton("", skin, "glassPanelStyle");

        // Player table and container
        final Table playerTable = new Table();
        playerTable.setFillParent(true);
        playerTable.add(new Image(new SpriteDrawable(new AnimatedSprite(animation)))).size(256, 256).center().padRight(100);

        final Container playerContainer = new Container(playerTable);


        // Ability Strength
        final Image iGlassPanelStrength = new Image(rGlassPanel);
        iGlassPanelStrength.setSize(300, 50);
        iGlassPanelStrength.setPosition(-180, 50);

        final Image iSquareShadowStrength01 = new Image(rSquareShadow);
        final Image iSquareShadowStrength02 = new Image(rSquareShadow);
        final Image iSquareShadowStrength03 = new Image(rSquareShadow);
        final Image iSquareShadowStrength04 = new Image(rSquareShadow);
        final Image iSquareShadowStrength05 = new Image(rSquareShadow);

        iSquareShadowStrength01.setSize(19, 26);
        iSquareShadowStrength01.setPosition(-16, 60);

        iSquareShadowStrength02.setSize(19, 26);
        iSquareShadowStrength02.setPosition(8, 60);

        iSquareShadowStrength03.setSize(19, 26);
        iSquareShadowStrength03.setPosition(32, 60);

        iSquareShadowStrength04.setSize(19, 26);
        iSquareShadowStrength04.setPosition(56, 60);

        iSquareShadowStrength05.setSize(19, 26);
        iSquareShadowStrength05.setPosition(80, 60);

        bStrengthPlus.setSize(50, 50);
        bStrengthPlus.setPosition(120, 50);



        // Ability Speed
        final Image iGlassPanelSpeed = new Image(rGlassPanel);
        iGlassPanelSpeed.setSize(300, 50);
        iGlassPanelSpeed.setPosition(-180, -20);

        final Image iSquareShadowSpeed01 = new Image(rSquareShadow);
        final Image iSquareShadowSpeed02 = new Image(rSquareShadow);
        final Image iSquareShadowSpeed03 = new Image(rSquareShadow);
        final Image iSquareShadowSpeed04 = new Image(rSquareShadow);
        final Image iSquareShadowSpeed05 = new Image(rSquareShadow);

        iSquareShadowSpeed01.setSize(19, 26);
        iSquareShadowSpeed01.setPosition(-16, -10);

        iSquareShadowSpeed02.setSize(19, 26);
        iSquareShadowSpeed02.setPosition(8, -10);

        iSquareShadowSpeed03.setSize(19, 26);
        iSquareShadowSpeed03.setPosition(32, -10);

        iSquareShadowSpeed04.setSize(19, 26);
        iSquareShadowSpeed04.setPosition(56, -10);

        iSquareShadowSpeed05.setSize(19, 26);
        iSquareShadowSpeed05.setPosition(80, -10);

        bSpeedPlus.setSize(50, 50);
        bSpeedPlus.setPosition(120, -20);



        // Ability Agility
        final Image iGlassPanelAgility = new Image(rGlassPanel);
        iGlassPanelAgility.setSize(300, 50);
        iGlassPanelAgility.setPosition(-180, -90);

        final Image iSquareShadowAgility01 = new Image(rSquareShadow);
        final Image iSquareShadowAgility02 = new Image(rSquareShadow);
        final Image iSquareShadowAgility03 = new Image(rSquareShadow);
        final Image iSquareShadowAgility04 = new Image(rSquareShadow);
        final Image iSquareShadowAgility05 = new Image(rSquareShadow);

        iSquareShadowAgility01.setSize(19, 26);
        iSquareShadowAgility01.setPosition(-16, -80);

        iSquareShadowAgility02.setSize(19, 26);
        iSquareShadowAgility02.setPosition(8, -80);

        iSquareShadowAgility03.setSize(19, 26);
        iSquareShadowAgility03.setPosition(32, -80);

        iSquareShadowAgility04.setSize(19, 26);
        iSquareShadowAgility04.setPosition(56, -80);

        iSquareShadowAgility05.setSize(19, 26);
        iSquareShadowAgility05.setPosition(80, -80);

        bAgilityPlus.setSize(50, 50);
        bAgilityPlus.setPosition(120, -90);


        final Group strengthGroup = new Group();
        strengthGroup.addActor(iGlassPanelStrength);
        strengthGroup.addActor(iSquareShadowStrength01);
        strengthGroup.addActor(iSquareShadowStrength02);
        strengthGroup.addActor(iSquareShadowStrength03);
        strengthGroup.addActor(iSquareShadowStrength04);
        strengthGroup.addActor(iSquareShadowStrength05);
        strengthGroup.addActor(bStrengthPlus);

        final Group speedGroup = new Group();
        speedGroup.addActor(iGlassPanelSpeed);
        speedGroup.addActor(iSquareShadowSpeed01);
        speedGroup.addActor(iSquareShadowSpeed02);
        speedGroup.addActor(iSquareShadowSpeed03);
        speedGroup.addActor(iSquareShadowSpeed04);
        speedGroup.addActor(iSquareShadowSpeed05);
        speedGroup.addActor(bSpeedPlus);

        final Group agilityGroup = new Group();
        agilityGroup.addActor(iGlassPanelAgility);
        agilityGroup.addActor(iSquareShadowAgility01);
        agilityGroup.addActor(iSquareShadowAgility02);
        agilityGroup.addActor(iSquareShadowAgility03);
        agilityGroup.addActor(iSquareShadowAgility04);
        agilityGroup.addActor(iSquareShadowAgility05);
        agilityGroup.addActor(bAgilityPlus);



        // Ability table and container
        final Table abilityTable = new Table();
        abilityTable.setFillParent(false);
        abilityTable.add(strengthGroup);
        abilityTable.add(speedGroup);
        abilityTable.add(agilityGroup);
        abilityTable.row();
        final Container abilityContainer = new Container(abilityTable);


        // Table
        final Table table = new Table();
        table.debug();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(rBackground));
        table.add(playerContainer).size(400, 400).top().left().padTop(20);
        table.add(abilityContainer).size(400, 400).top().right().padTop(20);
        table.row();
        table.add(bBack).size(200, 50).bottom().left().padLeft(40).padBottom(20).colspan(2);

        // Add table to the stage
        stage.addActor(table);

        // Add white ability point squares
        addWhiteSquares();

        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen(game, gameScreen));
            }
        });

        bStrengthPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // 1. Check op genoeg orbs
                // 2. Check level van Strength
                // 3. Voeg wit blokje toe op de juiste positie
                // 4. SaveData


                Vector2 coords = new Vector2(iSquareShadowStrength01.getX(), iSquareShadowStrength01.getY());
                //iSquareShadowStrength01.localToStageCoordinates(/*in/out*/coords);
                //iSquareShadowStrength01.getStage().stageToScreenCoordinates(/*in/out*/coords);

                Image iWhite = new Image(rSquareWhite);
                iWhite.setSize(19, 26);
                iWhite.setPosition(coords.x, coords.y);
                strengthGroup.addActor(iWhite);
            }
        });

        // TODO: Haal PlayerData op en vul de witte blokjes in bij openen scherm.
        // TODO: Haal Totaal aantal orbs op en zet ze boven aan de screen.
        // TODO: Zet aantal benodige orbs naast [+] bij elke ability.
        // TODO: Render poppetje opnieuw bij het levelen van een ability.
        // TODO: Geef ras ("string") weer onder poppetje.

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    private void addWhiteSquares() {
        System.out.println(playerProp.getPlayerData().getStrengthLevel());
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
