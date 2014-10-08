package com.FOS.Pixel.handlers;

import com.FOS.Pixel.Data.PlayerData;
import com.FOS.Pixel.Data.LevelSaveData;
import com.FOS.Pixel.Data.SaveData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Lars on 10/2/2014.
 */
public class SaveHandler {

    private static SaveData saveData;
    private static Json json = new Json();

    public static void Initialize(){

        FileHandle file = Gdx.files.local("saves/savedata.json");
        if(!file.exists()){

            String savestring = json.prettyPrint(new SaveData(
                    new PlayerData(PlayerData.AbilityType.NONE,1,1,1, PlayerData.SkinType.HUMAN),
                    new LevelSaveData[]{
                            new LevelSaveData(0,0,true),
                            new LevelSaveData(0,0,false)
                    },
                    0
            ));
            file.writeString(savestring, true);

        }
        saveData = json.fromJson(SaveData.class,file);
    }

    public static void Save(SaveData data){

        saveData = data;

        FileHandle file = Gdx.files.local("saves/savedata.json");

        if(file.exists()){
            file.delete();
        }

        String savestring = json.prettyPrint(saveData);
        file.writeString(savestring, true);


    }

    public static void ResetSave(){
        saveData = new SaveData(
                new PlayerData(PlayerData.AbilityType.NONE,1,1,1, PlayerData.SkinType.HUMAN),
                new LevelSaveData[]{
                        new LevelSaveData(0,0,true),
                        new LevelSaveData(0,0,false)
                },
                0
        );

        FileHandle file = Gdx.files.local("saves/savedata.json");

        if(file.exists()){
            file.delete();
        }

        String savestring = json.prettyPrint(saveData);
        file.writeString(savestring, true);


    }

    public static SaveData replacePlayerData(PlayerData playerData){
        SaveData data = saveData;
        data.setPlayerData(playerData);
        return data;
    }
    public static  SaveData addPlayerData(PlayerData playerData){
        SaveData data = saveData;
        data.setPlayerData(saveData.getPlayerData().add(playerData,saveData.getPlayerData().getSkinType()));
        return data;
    }

    public static  SaveData upStrength(int level){
        SaveData data = saveData;
        data.setPlayerData(saveData.getPlayerData().add(new PlayerData(PlayerData.AbilityType.NONE,0,level,0, PlayerData.SkinType.HUMAN),saveData.getPlayerData().getSkinType()));
        return data;
    }
    public static  SaveData upSpeed(int level){
        SaveData data = saveData;
        data.setPlayerData(saveData.getPlayerData().add(new PlayerData(PlayerData.AbilityType.NONE,0,0,level, PlayerData.SkinType.HUMAN),saveData.getPlayerData().getSkinType()));
        return data;
    }
    public static  SaveData upJump(int level){
        SaveData data = saveData;
        data.setPlayerData(saveData.getPlayerData().add(new PlayerData(PlayerData.AbilityType.NONE,level,0,0, PlayerData.SkinType.HUMAN),saveData.getPlayerData().getSkinType()));
        return data;
    }



    
    public static SaveData getSaveData() {
        return saveData;
    }
}
