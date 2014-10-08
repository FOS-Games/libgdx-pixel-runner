package com.FOS.Pixel.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.FOS.Pixel.MainPixel;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Revolve";
        config.width = 800;
        config.height = 480;
		new LwjglApplication(new MainPixel(), config);
	}
}
