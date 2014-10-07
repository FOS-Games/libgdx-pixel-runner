package com.FOS.Pixel;

import com.FOS.Pixel.Interfaces.ISpeedController;
import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.graphics.Camera;
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

    public Vector2 velocity;
    public float minVelocity;

    public PlayerCamera(GameScreen gameScreen, Vector2 spawn,float viewportWidth, float viewportHeight){
        super(viewportWidth, viewportHeight);
        this.gameScreen = gameScreen;
        this.spawn=spawn;
        this.player = gameScreen.getPlayer();
        this.minVelocity =gameScreen.levelData.getMinSpeed();
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
            checkPlayerPosition();
            this.position.x = body.getPosition().x;
            this.position.y = body.getPosition().y;
            playerUpdateSpeed();
        }

    }

    private void checkPlayerPosition() {
        DecimalFormat df = new DecimalFormat("###.#");

        float playerx = Float.valueOf(df.format(player.getBody().getPosition().x).replace(",", "."));
        float camerax = Float.valueOf(df.format(body.getPosition().x).replace(",", "."));

        System.out.println("Player x = "+playerx+", Camera x = "+camerax+" || "+(playerx == camerax));

        if(camerax==playerx && isSearching){
            minVelocity = player.minVelocity;
            this.body.setTransform(playerx,body.getPosition().y,body.getAngle());
            System.out.println("Found");
            isSearching=false;
        }

        boolean outofrange = this.body.getPosition().x > player.getBody().getPosition().x+0.15 || this.body.getPosition().x < player.getBody().getPosition().x-0.15;
        if (outofrange && !isSearching){
            System.out.println("Searching...");
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
