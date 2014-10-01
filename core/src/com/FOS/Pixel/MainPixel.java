package com.FOS.Pixel;

import com.FOS.Pixel.screens.GameScreen;
import com.badlogic.gdx.Game;

public class MainPixel extends Game {
	@Override
    public  void create(){
        setScreen(new GameScreen(this));
    }
}
