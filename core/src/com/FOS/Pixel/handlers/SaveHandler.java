package com.FOS.Pixel.handlers;

import com.FOS.Pixel.Data.AbilityData;
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
        //TODO: check file present
        if(!file.exists()){

            String savestring = json.prettyPrint(new SaveData(
                    new AbilityData(),
                    "jemoeder",
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

    public static SaveData getSaveData() {
        return saveData;
    }
}
