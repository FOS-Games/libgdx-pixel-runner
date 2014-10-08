package com.FOS.Pixel;

import com.FOS.Pixel.Interfaces.ISpeedController;
import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

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

    public PlayerCamera(GameScreen gameScreen, Vector2 spawn,float viewportWidth, float viewportHeight){
        super(viewportWidth, viewportHeight);
        this.gameScreen = gameScreen;
        this.spawn=spawn;
        this.player = gameScreen.getPlayer();
        this.minVelocity =gameScreen.levelData.getMinSpeed();
        ymin = spawn.y+1f;
        ymax = ymin + 6f;
        createBody();
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
        if(outofxrange||outofyrange){
            //TODO:Something dead-like
            System.out.println("DEADD");
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
        int stepcount = steps+2;
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
        },1,1,stepcount);

    }



    @Override
    public void adjustSpeed(Vector2 adjustWith, int steps, float seconds) {
        isAdjusting=true;
        int stepcount = steps+2;
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
