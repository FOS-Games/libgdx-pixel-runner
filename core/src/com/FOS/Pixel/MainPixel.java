package com.FOS.Pixel;

import com.FOS.Pixel.handlers.JsonHandler;
import com.FOS.Pixel.handlers.SaveHandler;
import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.Game;

public class MainPixel extends Game {
	@Override
    public  void create(){

        SaveHandler.Initialize();

        setScreen(new GameScreen(this,1));
    }
}
