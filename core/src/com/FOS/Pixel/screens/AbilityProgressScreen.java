package com.FOS.Pixel.screens;

import com.FOS.Pixel.Data.AbilityData;
import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Data.SaveData;
import com.FOS.Pixel.MainPixel;
import com.FOS.Pixel.PlayerProp;
import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

    Texture tGlassPanelOrbCost;



    PlayerProp playerProp;

    Image iSquareShadowStrength01;
    Image iSquareShadowStrength02;
    Image iSquareShadowStrength03;
    Image iSquareShadowStrength04;
    Image iSquareShadowStrength05;

    Image iSquareShadowSpeed01;
    Image iSquareShadowSpeed02;
    Image iSquareShadowSpeed03;
    Image iSquareShadowSpeed04;
    Image iSquareShadowSpeed05;

    Image iSquareShadowAgility01;
    Image iSquareShadowAgility02;
    Image iSquareShadowAgility03;
    Image iSquareShadowAgility04;
    Image iSquareShadowAgility05;

    Image iBody;
    Image iFeet;
    Image iWings;
    Image iWeapon;

    Animation animationBody;
    Animation animationWings;
    Animation animationFeet;
    Animation animationWeapon;

    TextureRegion rSquareWhite;

    Group strengthGroup;
    Group speedGroup;
    Group agilityGroup;
    Group playerGroup;

    BitmapFont orbFont;
    BitmapFont abilityFont;

    TextField orbText;
    TextField orbText1;
    TextField orbText2;
    TextField orbText3;

    public AbilityProgressScreen(Game game) {
        super(game);
        this.game = game;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(800, 480));
        Gdx.input.setInputProcessor(stage);


        // Background
        background = ((MainPixel)game).assetManager.get("ui/menuBackground.png",Texture.class);
        TextureRegion rBackground = new TextureRegion(background);


        // Default blue buttons
        tBlueButton = ((MainPixel)game).assetManager.get("ui/blueButton.png",Texture.class);
        TextureRegion rBlueButton = new TextureRegion(tBlueButton);

        tBlueButtonHover = ((MainPixel)game).assetManager.get("ui/blueButtonHover.png",Texture.class);
        TextureRegion rBlueButtonHover = new TextureRegion(tBlueButtonHover);

        tBlueButtonPressed =  ((MainPixel)game).assetManager.get("ui/blueButtonPressed.png",Texture.class);
        TextureRegion rBlueButtonPressed = new TextureRegion(tBlueButtonPressed);


        // Ability point UI
        tGlassPanel =  ((MainPixel)game).assetManager.get("ui/glassPanel320x50.png",Texture.class);
        TextureRegion rGlassPanel = new TextureRegion(tGlassPanel);

        tSquareShadow =  ((MainPixel)game).assetManager.get("ui/squareShadow.png",Texture.class);
        TextureRegion rSquareShadow = new TextureRegion(tSquareShadow);

        tSquareWhite =  ((MainPixel)game).assetManager.get("ui/squareWhite.png",Texture.class);
        rSquareWhite = new TextureRegion(tSquareWhite);

        tGlassPanelPlus =  ((MainPixel)game).assetManager.get("ui/glassPanelPlus.png",Texture.class);
        TextureRegion rGlassPanelPlus = new TextureRegion(tGlassPanelPlus);

        tGlassPanelPlusHover =  ((MainPixel)game).assetManager.get("ui/glassPanelPlusHover.png",Texture.class);
        TextureRegion rGlassPanelPlusHover = new TextureRegion(tGlassPanelPlusHover);

        tGlassPanelPlusLocked =  ((MainPixel)game).assetManager.get("ui/glassPanelPlusLocked.png",Texture.class);
        TextureRegion rGlassPanelPlusLocked = new TextureRegion(tGlassPanelPlusLocked);

        tGlassPanelOrbCost =  ((MainPixel)game).assetManager.get("ui/glassPanelOrbCost.png",Texture.class);
        TextureRegion rGlassPanelOrbCost = new TextureRegion(tGlassPanelOrbCost);


        // Get playerSprite
        playerProp = new PlayerProp(game);
        animationBody = playerProp.getAnimation();
        animationFeet = playerProp.getFeetAnimation();
        animationWings = playerProp.getWingAnimation();
        animationWeapon = playerProp.getWeaponAnimation();

        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());

        // Create orb font
        FreeTypeFontGenerator generatorOrb = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenpixel_blocks.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterOrb = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterOrb.size = 36;
        orbFont = generatorOrb.generateFont(parameterOrb);
        //generatorOrb.dispose();
        skin.add("orbFont", orbFont);

        // Create ability font
        FreeTypeFontGenerator generatorAbility = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterAbility = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterAbility.size = 24;
        abilityFont = generatorAbility.generateFont(parameterAbility);
        //abilityFont.dispose();
        skin.add("abilityFont", abilityFont);


        // DEFAULT TextFieldStyle
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.fontColor = Color.DARK_GRAY;
        textFieldStyle.font = skin.getFont("orbFont");
        skin.add("default", textFieldStyle);

        // ABILITY TextFieldStyle
        TextField.TextFieldStyle textFieldStyle1 = new TextField.TextFieldStyle();
        textFieldStyle1.fontColor = Color.WHITE;
        textFieldStyle1.font = skin.getFont("abilityFont");
        skin.add("ability", textFieldStyle1);

        // DEFAULT TextButtonStyle
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("abilityFont");
        textButtonStyle.font.setScale(1, 1);
        skin.add("default", textButtonStyle);

        // BLUE TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleBlue = new TextButton.TextButtonStyle();
        textButtonStyleBlue.up = skin.newDrawable(new TextureRegionDrawable(rBlueButton));
        textButtonStyleBlue.down = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
        textButtonStyleBlue.over = skin.newDrawable(new TextureRegionDrawable(rBlueButtonHover));
        textButtonStyleBlue.font = skin.getFont("abilityFont");
        skin.add("blueStyle", textButtonStyleBlue);

        // GLASSPANEL PLUS TextButtonStyle
        TextButton.TextButtonStyle textButtonStyleGlassPanelPlus = new TextButton.TextButtonStyle();
        textButtonStyleGlassPanelPlus.up = skin.newDrawable(new TextureRegionDrawable(rGlassPanelPlus));
        textButtonStyleGlassPanelPlus.down = skin.newDrawable(new TextureRegionDrawable(rGlassPanelPlusHover));
        textButtonStyleGlassPanelPlus.over = skin.newDrawable(new TextureRegionDrawable(rGlassPanelPlusHover));
        textButtonStyleGlassPanelPlus.font = skin.getFont("abilityFont");
        skin.add("glassPanelStyle", textButtonStyleGlassPanelPlus);


        final TextButton bBack = new TextButton("Back", skin, "blueStyle");

        final TextButton bStrengthPlus = new TextButton("", skin, "glassPanelStyle");
        final TextButton bSpeedPlus = new TextButton("", skin, "glassPanelStyle");
        final TextButton bAgilityPlus = new TextButton("", skin, "glassPanelStyle");

        // Player table and container
        playerGroup = new Group();

        iBody = new Image(new SpriteDrawable(new AnimatedSprite(animationBody)));
        iFeet = new Image(new SpriteDrawable(new AnimatedSprite(animationFeet)));
        iWings = new Image(new SpriteDrawable(new AnimatedSprite(animationWings)));
        iWeapon = new Image(new SpriteDrawable(new AnimatedSprite(animationWeapon)));

        iBody.setSize(256, 256);
        iBody.setPosition(-150, -50);

        iFeet.setSize(256, 256);
        iFeet.setPosition(-150, -50);

        iWings.setSize(256, 256);
        iWings.setPosition(-150, -50);

        iWeapon.setSize(256, 256);
        iWeapon.setPosition(-150, -50);

        playerGroup.addActor(iBody);
        playerGroup.addActor(iFeet);
        playerGroup.addActor(iWings);
        playerGroup.addActor(iWeapon);

        final Container playerContainer = new Container(playerGroup);

        // Ability Strength
        final Image iGlassPanelStrength = new Image(rGlassPanel);
        iGlassPanelStrength.setSize(320, 50);
        iGlassPanelStrength.setPosition(-200, 50);

        iSquareShadowStrength01 = new Image(rSquareShadow);
        iSquareShadowStrength02 = new Image(rSquareShadow);
        iSquareShadowStrength03 = new Image(rSquareShadow);
        iSquareShadowStrength04 = new Image(rSquareShadow);
        iSquareShadowStrength05 = new Image(rSquareShadow);

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
        iGlassPanelSpeed.setSize(320, 50);
        iGlassPanelSpeed.setPosition(-200, -45);

        iSquareShadowSpeed01 = new Image(rSquareShadow);
        iSquareShadowSpeed02 = new Image(rSquareShadow);
        iSquareShadowSpeed03 = new Image(rSquareShadow);
        iSquareShadowSpeed04 = new Image(rSquareShadow);
        iSquareShadowSpeed05 = new Image(rSquareShadow);

        iSquareShadowSpeed01.setSize(19, 26);
        iSquareShadowSpeed01.setPosition(-16, -35);

        iSquareShadowSpeed02.setSize(19, 26);
        iSquareShadowSpeed02.setPosition(8, -35);

        iSquareShadowSpeed03.setSize(19, 26);
        iSquareShadowSpeed03.setPosition(32, -35);

        iSquareShadowSpeed04.setSize(19, 26);
        iSquareShadowSpeed04.setPosition(56, -35);

        iSquareShadowSpeed05.setSize(19, 26);
        iSquareShadowSpeed05.setPosition(80, -35);

        bSpeedPlus.setSize(50, 50);
        bSpeedPlus.setPosition(120, -45);



        // Ability Agility
        final Image iGlassPanelAgility = new Image(rGlassPanel);
        iGlassPanelAgility.setSize(320, 50);
        iGlassPanelAgility.setPosition(-200, -140);

        iSquareShadowAgility01 = new Image(rSquareShadow);
        iSquareShadowAgility02 = new Image(rSquareShadow);
        iSquareShadowAgility03 = new Image(rSquareShadow);
        iSquareShadowAgility04 = new Image(rSquareShadow);
        iSquareShadowAgility05 = new Image(rSquareShadow);

        iSquareShadowAgility01.setSize(19, 26);
        iSquareShadowAgility01.setPosition(-16, -130);

        iSquareShadowAgility02.setSize(19, 26);
        iSquareShadowAgility02.setPosition(8, -130);

        iSquareShadowAgility03.setSize(19, 26);
        iSquareShadowAgility03.setPosition(32, -130);

        iSquareShadowAgility04.setSize(19, 26);
        iSquareShadowAgility04.setPosition(56, -130);

        iSquareShadowAgility05.setSize(19, 26);
        iSquareShadowAgility05.setPosition(80, -130);

        bAgilityPlus.setSize(50, 50);
        bAgilityPlus.setPosition(120, -140);


        strengthGroup = new Group();
        strengthGroup.addActor(iGlassPanelStrength);
        strengthGroup.addActor(iSquareShadowStrength01);
        strengthGroup.addActor(iSquareShadowStrength02);
        strengthGroup.addActor(iSquareShadowStrength03);
        strengthGroup.addActor(iSquareShadowStrength04);
        strengthGroup.addActor(iSquareShadowStrength05);
        strengthGroup.addActor(bStrengthPlus);

        speedGroup = new Group();
        speedGroup.addActor(iGlassPanelSpeed);
        speedGroup.addActor(iSquareShadowSpeed01);
        speedGroup.addActor(iSquareShadowSpeed02);
        speedGroup.addActor(iSquareShadowSpeed03);
        speedGroup.addActor(iSquareShadowSpeed04);
        speedGroup.addActor(iSquareShadowSpeed05);
        speedGroup.addActor(bSpeedPlus);

        agilityGroup = new Group();
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
        //table.debug();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(rBackground));
        table.add(playerContainer).size(400, 400).top().left().padTop(20);
        table.add(abilityContainer).size(400, 400).top().right().padTop(20);
        table.row();
        table.add(bBack).size(200, 50).bottom().left().padLeft(20).padBottom(20).colspan(2);

        // Add table to the stage
        stage.addActor(table);


        // Add Orb Costs
        final Image iGlassPanelOrbCost = new Image(new TextureRegionDrawable(rGlassPanelOrbCost));
        iGlassPanelOrbCost.setSize(115, 25);
        iGlassPanelOrbCost.setPosition(655, 290);
        stage.addActor(iGlassPanelOrbCost);

        final Image iGlassPanelOrbCost2 = new Image(new TextureRegionDrawable(rGlassPanelOrbCost));
        iGlassPanelOrbCost2.setSize(115, 25);
        iGlassPanelOrbCost2.setPosition(655, 195);
        stage.addActor(iGlassPanelOrbCost2);

        final Image iGlassPanelOrbCost3 = new Image(new TextureRegionDrawable(rGlassPanelOrbCost));
        iGlassPanelOrbCost3.setSize(115, 25);
        iGlassPanelOrbCost3.setPosition(655, 100);
        stage.addActor(iGlassPanelOrbCost3);

        // Show orb costs
        showOrbCosts();

        // Add white ability point squares
        addWhiteSquares();

        // Show total Orbs
        showTotalOrbs();

        // Show ability texts
        showAbilityText();


        // Button listeners


        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen(game));
            }
        });

        bStrengthPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // 1. Check op genoeg orbs

                int strengthLevel = playerProp.getPlayerData().getStrengthLevel();
                int strengthCost = playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.STRENGTH).getCost();

                if(strengthLevel != 5 && strengthCost <= SaveHandler.getSaveData().getTotalOrbs()) {
                    Vector2 coords = new Vector2(iSquareShadowStrength01.getX(), iSquareShadowStrength01.getY());
                    Image iWhite = new Image(rSquareWhite);
                    iWhite.setSize(19, 26);
                    iWhite.setPosition(coords.x + strengthLevel * 24, coords.y);
                    strengthGroup.addActor(iWhite);

                    SaveHandler.getSaveData().setTotalOrbs(SaveHandler.getSaveData().getTotalOrbs() - strengthCost);
                    SaveHandler.Save(SaveHandler.getSaveData());

                    SaveHandler.Save(SaveHandler.upStrength(1));

                    rebuildPlayer();
                    reshowTotalOrbs();
                    reshowOrbCosts();
                }
            }
        });

        bSpeedPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // 1. Check op genoeg orbs

                int speedLevel = playerProp.getPlayerData().getSpeedLevel();
                int speedCost = playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.SPEED).getCost();

                if(speedLevel != 5 && speedCost <= SaveHandler.getSaveData().getTotalOrbs()) {
                    Vector2 coords = new Vector2(iSquareShadowSpeed01.getX(), iSquareShadowSpeed01.getY());
                    Image iWhite = new Image(rSquareWhite);
                    iWhite.setSize(19, 26);
                    iWhite.setPosition(coords.x + speedLevel * 24, coords.y);
                    speedGroup.addActor(iWhite);

                    SaveHandler.getSaveData().setTotalOrbs(SaveHandler.getSaveData().getTotalOrbs() - speedCost);
                    SaveHandler.Save(SaveHandler.getSaveData());

                    SaveHandler.Save(SaveHandler.upSpeed(1));

                    rebuildPlayer();
                    reshowTotalOrbs();
                    reshowOrbCosts();
                }
            }
        });

        bAgilityPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // 1. Check op genoeg orbs

                int agilityLevel = playerProp.getPlayerData().getAgilityLevel();
                int agilityCost = playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.JUMP).getCost();

                if(agilityLevel != 5 && agilityCost <= SaveHandler.getSaveData().getTotalOrbs()) {
                    Vector2 coords = new Vector2(iSquareShadowAgility01.getX(), iSquareShadowAgility01.getY());
                    Image iWhite = new Image(rSquareWhite);
                    iWhite.setSize(19, 26);
                    iWhite.setPosition(coords.x + agilityLevel * 24, coords.y);
                    agilityGroup.addActor(iWhite);

                    SaveHandler.getSaveData().setTotalOrbs(SaveHandler.getSaveData().getTotalOrbs() - agilityCost);
                    SaveHandler.Save(SaveHandler.getSaveData());

                    SaveHandler.Save(SaveHandler.upJump(1));

                    rebuildPlayer();
                    reshowTotalOrbs();
                    reshowOrbCosts();
                }
            }
        });

        // TODO: @LARS Negeer TODO'S in AbilityProgressScreen.

        // TODO: Geef ras ("string") weer onder poppetje.

    }

    private void rebuildPlayer() {
        playerGroup.removeActor(iBody);
        playerGroup.removeActor(iWings);
        playerGroup.removeActor(iFeet);
        playerGroup.removeActor(iWeapon);

        playerProp = new PlayerProp(game);

        animationBody = playerProp.getAnimation();
        animationFeet = playerProp.getFeetAnimation();
        animationWings = playerProp.getWingAnimation();
        animationWeapon = playerProp.getWeaponAnimation();

        iBody = new Image(new SpriteDrawable(new AnimatedSprite(animationBody)));
        iFeet = new Image(new SpriteDrawable(new AnimatedSprite(animationFeet)));
        iWings = new Image(new SpriteDrawable(new AnimatedSprite(animationWings)));
        iWeapon = new Image(new SpriteDrawable(new AnimatedSprite(animationWeapon)));

        iBody.setSize(256, 256);
        iBody.setPosition(-150, -50);

        iFeet.setSize(256, 256);
        iFeet.setPosition(-150, -50);

        iWings.setSize(256, 256);
        iWings.setPosition(-150, -50);

        iWeapon.setSize(256, 256);
        iWeapon.setPosition(-150, -50);

        playerGroup.addActor(iBody);
        playerGroup.addActor(iFeet);
        playerGroup.addActor(iWings);
        playerGroup.addActor(iWeapon);


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    private void addWhiteSquares() {
        System.out.println(playerProp.getPlayerData().getStrengthLevel());

        int strengthLevel = playerProp.getPlayerData().getStrengthLevel();
        int speedLevel = playerProp.getPlayerData().getSpeedLevel();
        int agilityLevel = playerProp.getPlayerData().getAgilityLevel();

        Vector2 coordsStrength =  new Vector2(iSquareShadowStrength01.getX(), iSquareShadowStrength01.getY());
        Vector2 coordsSpeed =  new Vector2(iSquareShadowSpeed01.getX(), iSquareShadowSpeed01.getY());
        Vector2 coordsAgility =  new Vector2(iSquareShadowAgility01.getX(), iSquareShadowAgility01.getY());

        for(int i = 0; i < strengthLevel; i++)
        {
            Image iWhite = new Image(rSquareWhite);
            iWhite.setSize(19, 26);
            iWhite.setPosition(coordsStrength.x + (i * 24), coordsStrength.y);
            strengthGroup.addActor(iWhite);
        }

        for(int i = 0; i < speedLevel; i++)
        {
            Image iWhite = new Image(rSquareWhite);
            iWhite.setSize(19, 26);
            iWhite.setPosition(coordsSpeed.x + (i * 24), coordsSpeed.y);
            speedGroup.addActor(iWhite);
        }

        for(int i = 0; i < agilityLevel; i++)
        {
            Image iWhite = new Image(rSquareWhite);
            iWhite.setSize(19, 26);
            iWhite.setPosition(coordsAgility.x + (i * 24), coordsAgility.y);
            agilityGroup.addActor(iWhite);
        }
    }

    private void showTotalOrbs() {
        // TODO: Render total orbs

        int totalOrbs = SaveHandler.getSaveData().getTotalOrbs();
        System.out.println(totalOrbs);

        AnimatedSprite orbAnimSprite = ((MainPixel)game).assetManager.createAnimatedSprite(((MainPixel)game).assetManager.createTextureRegion("sprites/spriteSheet_collectible.png", 15, 1), Animation.PlayMode.LOOP);
        Image iOrb = new Image(new SpriteDrawable(orbAnimSprite));
        iOrb.setPosition(450, 390);
        iOrb.setSize(64, 64);
        stage.addActor(iOrb);

        orbText = new TextField(Integer.toString(totalOrbs), skin);
        orbText.setPosition(495, 395);
        stage.addActor(orbText);
    }

    private void reshowTotalOrbs() {
        orbText.remove();

        int totalOrbs = SaveHandler.getSaveData().getTotalOrbs();
        orbText = new TextField(Integer.toString(totalOrbs), skin);
        orbText.setPosition(495, 395);
        stage.addActor(orbText);
    }

    private void showOrbCosts() {
        AnimatedSprite orbAnimSprite = ((MainPixel)game).assetManager.createAnimatedSprite(((MainPixel)game).assetManager.createTextureRegion("sprites/spriteSheet_collectible.png", 15, 1), Animation.PlayMode.LOOP);
        Image iOrb2 = new Image(new SpriteDrawable(orbAnimSprite));
        iOrb2.setPosition(660, 292);
        iOrb2.setSize(20, 20);
        stage.addActor(iOrb2);

        Image iOrb3 = new Image(new SpriteDrawable(orbAnimSprite));
        iOrb3.setPosition(660, 197);
        iOrb3.setSize(20, 20);
        stage.addActor(iOrb3);

        Image iOrb4 = new Image(new SpriteDrawable(orbAnimSprite));
        iOrb4.setPosition(660, 102);
        iOrb4.setSize(20, 20);
        stage.addActor(iOrb4);


        orbText1 = new TextField(Integer.toString(playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.STRENGTH).getCost()), skin, "ability");
        orbText1.setPosition(685, 287);
        orbText1.setDisabled(true);
        stage.addActor(orbText1);

        orbText2 = new TextField(Integer.toString(playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.SPEED).getCost()), skin, "ability");
        orbText2.setPosition(685, 192);
        orbText2.setDisabled(true);
        stage.addActor(orbText2);

        orbText3 = new TextField(Integer.toString(playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.JUMP).getCost()), skin, "ability");
        orbText3.setPosition(685, 97);
        orbText3.setDisabled(true);
        stage.addActor(orbText3);
    }

    private void reshowOrbCosts() {
        orbText1.remove();
        orbText2.remove();
        orbText3.remove();

        orbText1 = new TextField(Integer.toString(playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.STRENGTH).getCost()), skin, "ability");
        orbText2 = new TextField(Integer.toString(playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.SPEED).getCost()), skin, "ability");
        orbText3 = new TextField(Integer.toString(playerProp.getPlayerData().getAbilityData(PlayerData.AbilityType.JUMP).getCost()), skin, "ability");

        orbText1.setPosition(685, 287);
        orbText2.setPosition(685, 192);
        orbText3.setPosition(685, 97);

        stage.addActor(orbText1);
        stage.addActor(orbText2);
        stage.addActor(orbText3);

    }

    private void showAbilityText() {
        TextField strengthText = new TextField("strength", skin, "ability");
        strengthText.setPosition(410, 315);
        strengthText.setSize(200, 50);
        strengthText.setDisabled(true);
        stage.addActor(strengthText);

        TextField speedText = new TextField("speed", skin, "ability");
        speedText.setPosition(410, 220);
        speedText.setSize(200, 50);
        speedText.setDisabled(true);
        stage.addActor(speedText);

        TextField agilityText = new TextField("agility", skin, "ability");
        agilityText.setPosition(410, 125);
        agilityText.setSize(200, 50);
        speedText.setDisabled(true);
        stage.addActor(agilityText);

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
