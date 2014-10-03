package com.FOS.Pixel.Data;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lars on 10/2/2014.
 */
public class SaveData {

    PlayerData playerData;
    LevelSaveData[] levelSaveDatas;
    int totalOrbs;

    public SaveData() {
    }

    public SaveData(PlayerData playerData, LevelSaveData[] levelSaveDatas, int totalOrbs) {
        this.playerData = playerData;
        this.levelSaveDatas = levelSaveDatas;
        this.totalOrbs = totalOrbs;
    }

    public void saveLevelData(int level, LevelSaveData saveData){
        if(this.levelSaveDatas.length <level){
            ArrayList<LevelSaveData> levelSaveDataArrayList = new ArrayList<LevelSaveData>(Arrays.asList(levelSaveDatas));
            levelSaveDataArrayList.add(level-1, saveData);
            levelSaveDatas = levelSaveDataArrayList.toArray(levelSaveDatas);
        }
        else{
            levelSaveDatas[level-1] = saveData;
        }
    }

    public PlayerData getPlayerData() {
        return playerData;
    }


    public LevelSaveData[] getLevelSaveDatas() {
        return levelSaveDatas;
    }

    public int getTotalOrbs() {
        return totalOrbs;
    }
}
