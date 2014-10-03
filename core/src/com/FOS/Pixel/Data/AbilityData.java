package com.FOS.Pixel.Data;

/**
 * Created by Lars on 9/29/2014.
 */
public class AbilityData {
    float multiplier;
    String texturename;

    public AbilityData() {
    }

    public AbilityData(float multiplier, String texturename) {
        this.multiplier = multiplier;
        this.texturename = texturename;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public String getTexturename() {
        return texturename;
    }
}
