package com.FOS.Pixel;

import com.FOS.Pixel.Interfaces.ISpeedController;
import com.FOS.Pixel.handlers.SaveHandler;
import com.FOS.Pixel.screens.GameScreen;
import com.FOS.Pixel.screens.LevelSelectScreen;
import com.FOS.Pixel.screens.SettingsScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.text.DecimalFormat;

/**
 * Created by Lars on 9/26/2014.
 */
public class PlayerCamera extends OrthographicCamera implements ISpeedController {
    private GameScreen gameScreen;
    private Vector2 spawn;
    private Body body;
    private Fixture fixture;
    private Player player;
    private boolean isSearching = false;
    private boolean isAdjusting = false;

    public Vector2 velocity;
    public float minVelocity;

    private float ymin;
    private float ymax;

    Texture tBlueButton;
    Texture tBlueButtonHover;
    Texture tBlueButtonPressed;
    Stage stage;
    BitmapFont font;
    Skin skin;
    Dialog dialog;
    Dialog finishdialog;

    float viewportW;
    float viewportH;


    public PlayerCamera(GameScreen gameScreen, Vector2 spawn, float viewportWidth, float viewportHeight){
        super(viewportWidth, viewportHeight);
        this.gameScreen = gameScreen;
        this.spawn=spawn;
        this.player = gameScreen.getPlayer();
        this.minVelocity =gameScreen.levelData.getMinSpeed();
        this.viewportW = viewportWidth;
        this.viewportH = viewportHeight;
        ymin = spawn.y+1f;
        ymax = ymin + 6f;
        createBody();
        createStage();
    }

    private void createBody(){
        BodyDef bdef= new BodyDef();
        bdef.fixedRotation = true;
        bdef.position.set(spawn);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale = 0;
        body = gameScreen.getWorld().createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.1f,0.1f);
        fixture = body.createFixture(shape,0);
        fixture.setSensor(true);
        shape.dispose();
    }

    private void createStage() {
        stage = new Stage(new StretchViewport(800, 480));
        Gdx.input.setInputProcessor(stage);

        tBlueButton = new Texture(Gdx.files.internal("ui/blueButton.png"));
        TextureRegion rBlueButton = new TextureRegion(tBlueButton);

        tBlueButtonHover = new Texture(Gdx.files.internal("ui/blueButtonHover.png"));
        TextureRegion rBlueButtonHover = new TextureRegion(tBlueButtonHover);

        tBlueButtonPressed = new Texture(Gdx.files.internal("ui/blueButtonPressed.png"));
        TextureRegion rBlueButtonPressed = new TextureRegion(tBlueButtonPressed);

        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());

        // Create custom font
        FreeTypeFontGenerator generatorAbility = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterAbility = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterAbility.size = 24;
        font = generatorAbility.generateFont(parameterAbility);
        skin.add("customFont", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable(new TextureRegionDrawable(rBlueButton));
        textButtonStyle.down = skin.newDrawable(new TextureRegionDrawable(rBlueButtonPressed));
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

        dialog = new Dialog("", skin, "dialog"){
            protected void result (Object object) {
                if (object.equals(true)) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(gameScreen.game, gameScreen.level));

                }
                else {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen(gameScreen.game, gameScreen));

                }

            }
        }.text("You died! :(\nTry again?").button("Yes", true).button("No", false);

        finishdialog = new Dialog("", skin, "dialog"){
            protected void result (Object object) {
                if (object.equals(true)) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen(gameScreen.game, gameScreen));

                }
                else {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen(gameScreen.game, gameScreen));

                }

            }
        }.text("You made it :D \nOrbs Collected: "+ gameScreen.orbs).button("Select new Level", true);
    }

    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void update() {
        super.update();

        if(player!=null && body!=null) {
            checkPlayerxPosition();
            checkPlayeryPosition();
            this.position.x = body.getPosition().x;
            this.position.y = body.getPosition().y;
            playerUpdateSpeed();
            checkPlayerDeath();
        }

    }

    private void checkPlayeryPosition() {
        boolean inrange = player.getBody().getPosition().y > ymin &&player.getBody().getPosition().y <ymax;

        if(inrange){
            this.body.setTransform(this.body.getPosition().x,player.getBody().getPosition().y, this.body.getAngle());
        }else if(!inrange && player.getBody().getPosition().y < ymin){

            this.body.setTransform(this.body.getPosition().x,ymin, this.body.getAngle());
        }
        else if(!inrange && player.getBody().getPosition().y > ymax){

            this.body.setTransform(this.body.getPosition().x,ymax, this.body.getAngle());
        }
    }

    private void checkPlayerDeath() {
        boolean outofxrange = player.getBody().getPosition().x<body.getPosition().x-(viewportWidth/2)||player.getBody().getPosition().x>body.getPosition().x+(viewportWidth/2);
        boolean outofyrange = player.getBody().getPosition().y<body.getPosition().y-(viewportHeight/2);
        if((outofxrange||outofyrange)&&!gameScreen.isDeath){
            gameScreen.saveOrbs();
            gameScreen.death.play();
            gameScreen.isDeath = true;
            this.body.setLinearVelocity(1,0);
            minVelocity=1;
            dialog.show(stage);
        }
    }

    private void checkPlayerxPosition() {
        DecimalFormat df = new DecimalFormat("###.#");
        float playerx = Float.valueOf(df.format(player.getBody().getPosition().x).replace(",", "."));
        float camerax = Float.valueOf(df.format(body.getPosition().x).replace(",", "."));

//        System.out.println("Player x = "+playerx+", Camera x = "+camerax+" || "+(playerx == camerax));

        if(!isAdjusting){
            if(this.body.getPosition().x<player.getBody().getPosition().x){
                this.body.setTransform(this.body.getPosition().x+0.01f,this.body.getPosition().y,this.body.getAngle());
            }else if(this.body.getPosition().x>player.getBody().getPosition().x) {
                this.body.setTransform(this.body.getPosition().x - 0.01f, this.body.getPosition().y, this.body.getAngle());
            }else{
                this.body.setTransform(player.getBody().getPosition(),this.body.getAngle());
            }
        }
        if(camerax==playerx && isSearching && isAdjusting){
            minVelocity = player.minVelocity;
            this.body.setTransform(playerx,body.getPosition().y,body.getAngle());
//            System.out.println("Found");
            isSearching=false;
            isAdjusting=false;
        }

        boolean outofrange = this.body.getPosition().x > player.getBody().getPosition().x+0.15 || this.body.getPosition().x < player.getBody().getPosition().x-0.15;
        if (outofrange && !isSearching){
//            System.out.println("Searching...");
            isSearching=true;
        }
    }
    public void playerUpdateSpeed(){
        velocity = this.body.getLinearVelocity();
        this.body.setLinearVelocity(minVelocity, 0);
    }

    @Override
    public void update(boolean updateFrustum) {
        super.update(updateFrustum);

    }

    @Override
    public void adjustSpeed(Vector2 adjustWith, int steps) {
        isAdjusting=true;
        float seconds = 1;
        int stepcount;
        if(adjustWith.x>0) {
            stepcount = steps + 1;
        }else if(adjustWith.x<0) {
            stepcount = steps + 5;
            seconds++;
        }else{
            stepcount=steps;
        }
        float xincr= adjustWith.x/(float)stepcount;

        float yincr= adjustWith.y/(float)stepcount;
        final Vector2 incrsteps = new Vector2(xincr,yincr);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                body.setLinearVelocity(body.getLinearVelocity().add(incrsteps));
                minVelocity += incrsteps.x;
            }
        },seconds,seconds,stepcount);

    }



    @Override
    public void adjustSpeed(Vector2 adjustWith, int steps, float seconds) {
        isAdjusting=true;
        int stepcount;
        if(adjustWith.x>0) {
            stepcount = steps + 1;
        }else if(adjustWith.x<0) {
            stepcount = steps + 5;
            seconds++;
        }else{
            stepcount=steps;
        }
        float xincr=adjustWith.x/(float)stepcount;
        float yincr= adjustWith.y/(float)stepcount;
        final Vector2 incrsteps = new Vector2(xincr,yincr);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                body.setLinearVelocity(body.getLinearVelocity().add(incrsteps));
                minVelocity += incrsteps.x;
            }
        },seconds,seconds,stepcount);
    }
}
