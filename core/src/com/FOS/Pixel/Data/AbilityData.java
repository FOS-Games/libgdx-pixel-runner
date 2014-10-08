package com.FOS.Pixel.Data;

/**
 * Created by Lars on 9/29/2014.
 */
public class AbilityData {
    float multiplier;
    String texturename;
    int cost;

    public AbilityData() {
    }

    public AbilityData(float multiplier, String texturename,int cost) {
        this.multiplier = multiplier;
        this.texturename = texturename;
        this.cost = cost;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public String getTexturename() {
        return texturename;
    }

    public int getCost() {
        return cost;
    }
}
