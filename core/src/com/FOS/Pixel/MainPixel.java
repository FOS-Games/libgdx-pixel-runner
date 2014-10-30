package com.FOS.Pixel;

import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.FOS.Pixel.screens.GameScreen;
import com.FOS.Pixel.screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MainPixel extends Game {

    public LevelAssetManager assetManager = new LevelAssetManager();
	@Override
    public void create(){

        assetManager = new LevelAssetManager();
        SaveHandler.Initialize();
        assetManager.loadPlayerAnimation();
        assetManager.loadOrbAnim();

        //setScreen(new GameScreen(this,1));
        setScreen(new MainMenuScreen(this));



    }
    public void stopGame(){
        Gdx.app.exit();

    }


    @Override
    public void resume() {
        super.resume();
        assetManager = new LevelAssetManager();
        SaveHandler.Initialize();
        assetManager.loadPlayerAnimation();
        assetManager.loadOrbAnim();

        //setScreen(new GameScreen(this,1));
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        super.dispose();
    }
}
