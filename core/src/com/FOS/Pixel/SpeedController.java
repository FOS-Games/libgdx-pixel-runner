package com.FOS.Pixel;

import com.FOS.Pixel.Interfaces.ISpeedController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 10/7/2014.
 */
public class SpeedController {
   ArrayList<ISpeedController> controllers = new ArrayList<ISpeedController>();

    public void registerController(ISpeedController x){
        controllers.add(x);
    }

    public void adjustSpeed(Vector2 adjustWith, int steps) {
      for(ISpeedController speedy : controllers){
          speedy.adjustSpeed(adjustWith, steps);
      }

    }



    public void adjustSpeed(Vector2 adjustWith, int steps, float seconds) {
        for(ISpeedController speedy : controllers){
            speedy.adjustSpeed(adjustWith, steps,seconds);
        }
    }
}
