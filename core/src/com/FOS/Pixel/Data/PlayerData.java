package com.FOS.Pixel.Data;

import com.FOS.Pixel.handlers.JsonHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Lars on 9/29/2014.
 */
public class PlayerData {

    public  static enum AbilityType{
        NONE,
        JUMP,
        STRENGTH,
        SPEED
    }

    public static enum SkinType{
        ORC,
        HUMAN,
        ELF
    }

    AbilityType data[];
    int jump;
    int strength;
    int speed;
    SkinType skinType;

    public PlayerData() {
    }

    public PlayerData(AbilityType[] data, int jump, int strength, int speed, SkinType skinType) {
        this.data = data;
        this.jump = jump;
        this.strength = strength;
        this.speed = speed;
        this.skinType = skinType;
    }

    public PlayerData(AbilityType data, int jump, int strength, int speed, SkinType skinType) {
        this.data = new AbilityType[]{data};
        this.jump = jump;
        this.strength = strength;
        this.speed = speed;
        this.skinType= skinType;
    }

    /**
     *
     * Adds two AbilityData's together
     * @param x AbilityData to be added to this one
     * @param skinType The players desired skin
     * @return New Ability Data with both previous values
     */
    public PlayerData add(PlayerData x,SkinType skinType){
        AbilityType[] abilityTypes;
        if(Arrays.asList(this.data).contains(AbilityType.NONE)){
            abilityTypes = x.data;
        }
        else if(Arrays.asList(x).contains(AbilityType.NONE)){
            abilityTypes = x.data;
        }
        else {
            ArrayList<AbilityType> collection = new ArrayList<AbilityType>();
            collection.addAll(Arrays.asList(this.data));
            collection.addAll(Arrays.asList(x.data));
            HashSet<AbilityType> hs = new HashSet<AbilityType>(collection);
            collection.clear();
            collection.addAll(hs);
            abilityTypes = (AbilityType[]) collection.toArray();
        }
        return new PlayerData(abilityTypes,this.jump+x.jump,this.strength+x.strength,this.speed+x.speed,skinType);
    }

    public AbilityData getAbilityData(AbilityType type){
        int level=0;
        switch (type){
            case JUMP: level=jump;
                break;
            case STRENGTH: level=strength;
                break;
            case SPEED: level = speed;
                break;
            default: level=0;
        }

       return JsonHandler.getAbiltydata(type.toString(), level);

    }

    public int getStrengthLevel() {
        return this.strength;
    }

    public int getAgilityLevel() {
        return this.jump;
    }

    public int getSpeedLevel() {
        return this.speed;
    }

    // TODO : @LARS Voeg een setStrengthLevel(int level) of strengthLevelUp() toe please
    // Ik weet niet hoe ik het huidige level van de strength kan verhogen met 1 (om vervolgens op te slaan).

    public SkinType getSkinType() {
        return skinType;
    }

    @Override
    public String toString() {
        return "Data: "+this.data.toString()+", Jumplevel: "+this.jump+", StrengthLevel: "+ strength+", Speedlevel: "+this.speed+", Skin: "+this.skinType.toString();
    }

    public void setData(AbilityType[] data) {
        this.data = data;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSkinType(SkinType skinType) {
        this.skinType = skinType;
    }
}

