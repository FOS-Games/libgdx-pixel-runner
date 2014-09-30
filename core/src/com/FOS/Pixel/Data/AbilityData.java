package com.FOS.Pixel.Data;

import com.FOS.Pixel.handlers.JsonHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Lars on 9/29/2014.
 */
public class AbilityData{

    AbilityType data[];
    int jump;
    int strength;
    int speed;

    public AbilityData() {
    }

    public AbilityData(AbilityType[] data, int jump, int strength, int speed) {
        this.data = data;
        this.jump = jump;
        this.strength = strength;
        this.speed = speed;
    }

    public AbilityData(AbilityType data, int level, int jump, int strength, int speed) {
        this.data = new AbilityType[]{data};
        this.jump = jump;
        this.strength = strength;
        this.speed = speed;
    }

    public AbilityData add(AbilityData x){

        ArrayList<AbilityType> collection = new ArrayList<AbilityType>();
        collection.addAll(Arrays.asList(this.data));
        collection.addAll(Arrays.asList(x.data));
        HashSet<AbilityType> hs = new HashSet<AbilityType>(collection);
        collection.clear();
        collection.addAll(hs);
        AbilityType[] abilityTypes =(AbilityType[]) collection.toArray();
        return new AbilityData(abilityTypes,this.jump+x.jump,this.strength+x.strength,this.speed+x.speed);
    }

}

