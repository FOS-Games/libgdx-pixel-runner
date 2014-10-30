package com.FOS.Pixel.screens;

import com.FOS.Pixel.MainPixel;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by Stefan on 6-10-2014.
 */
public abstract class MenuScreen implements Screen {
    Game game;
    protected MenuScreen(Game game) {
        this.game=game;
        ((MainPixel)game).assetManager.loadGUIAssets();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        ((MainPixel)game).assetManager.unloadGUIAssets();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
