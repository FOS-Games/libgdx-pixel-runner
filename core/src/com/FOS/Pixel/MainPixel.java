package com.FOS.Pixel;

import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.FOS.Pixel.screens.GameScreen;
import com.FOS.Pixel.screens.MainMenuScreen;
import com.badlogic.gdx.Game;

public class MainPixel extends Game {

    public static LevelAssetManager assetManager = new LevelAssetManager();
	@Override
    public void create(){

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
