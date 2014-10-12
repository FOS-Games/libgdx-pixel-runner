package com.FOS.Pixel.handlers;

import com.FOS.Pixel.Data.AbilityData;
import com.FOS.Pixel.Data.LevelData;
import com.FOS.Pixel.Data.PlayerData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.OrderedMap;

import java.util.ArrayList;

/**
 * Created by Lars on 9/29/2014.
 */


public class JsonHandler {
    private static final String JSON_PATH = "";
    private static Json json = new Json();
    private static JsonReader jsonReader = new JsonReader();

    public JsonHandler(String path) {
    }

    /**
     * Gets leveldata from Json file
     * @param levelNumber Number to indicate level
     * @return LevelData class with the data from the json file
     */
    public static LevelData readLevel(int levelNumber) {
        OrderedMap<String, LevelData> jsonmap = json.fromJson(OrderedMap.class, Gdx.files.internal("leveldata.json"));
        return jsonmap.get("Level" + levelNumber);
    }
    public  static int getLevelCount(){
        OrderedMap<String, LevelData> jsonmap = json.fromJson(OrderedMap.class, Gdx.files.internal("leveldata.json"));
        return  jsonmap.size;
    }

//    /**
//     * stupid test method
//     */
//    public static void test() {
//        AbilityData test = new AbilityData(1f, "DEFAULT.png",9999);
//        ArrayList<typeholder> x = new ArrayList<typeholder>();
//        x.add(new typeholder(PlayerData.AbilityType.SPEED.toString(), new Abilitylevel[]{
//                new Abilitylevel("Level1", test),
//                new Abilitylevel("Level2", test),
//                new Abilitylevel("Level3", test),
//                new Abilitylevel("Level4", test),
//                new Abilitylevel("Level5", test)
//
//        }));
//        x.add(new typeholder(PlayerData.AbilityType.STRENGTH.toString(), new Abilitylevel[]{
//                new Abilitylevel("Level1", test),
//                new Abilitylevel("Level2", test),
//                new Abilitylevel("Level3", test),
//                new Abilitylevel("Level4", test),
//                new Abilitylevel("Level5", test)
//
//        }));
//        x.add(new typeholder(PlayerData.AbilityType.JUMP.toString(), new Abilitylevel[]{
//                new Abilitylevel("Level1", test),
//                new Abilitylevel("Level2", test),
//                new Abilitylevel("Level3", test),
//                new Abilitylevel("Level4", test),
//                new Abilitylevel("Level5", test)
//
//        }));
//
//        String printdata = json.prettyPrint(x);
//        FileHandle fileHandle = Gdx.files.local("abilitydata.json");
//        fileHandle.writeString(printdata, true);
//
//        OrderedMap<String, LevelData> leveldatatest = new OrderedMap<String, LevelData>();
//        LevelData  leveltest = new LevelData(1f,2f,1f,120,90,60,"tmxpath/map.tmx","soundpath/sound.mp3","Background.path");
//        leveldatatest.put("Level1",leveltest);
//        leveldatatest.put("Level2",leveltest);
//        printdata = json.prettyPrint(leveldatatest);
//         fileHandle = Gdx.files.local("leveldata.json");
//        fileHandle.writeString(printdata,true);
//
//
//    }


    /**
     * Gets Ability data from json file for given type en level
     * @param type Ability type (recommend to use AbilityType.type.toString())
     * @param level Integer to indicate level
     * @return AbilityParser with data from json. Or null if data is not present.
     */
    public static AbilityData getAbiltydata(String type, int level) {

        ArrayList<typeholder> test = json.fromJson(ArrayList.class, typeholder.class, Gdx.files.internal("abilitydata.json"));

        Abilitylevel[] levelholder = null;
        //System.out.println("Searching for: Type "+type.toString()+",Level"+level);
        for (typeholder x : test) {
            if (x.Type.equals( type)) {
                //System.out.println("Found Type ");
                levelholder = x.Level;
            }
        }
        if (levelholder != null) {
            for (Abilitylevel x : levelholder) {
                //System.out.println(x.level);
                if (x.level.equals( "Level"+level)){

                    //System.out.println("Found level ");
                    return x.abilityData;

                }
            }
        }

        return null;
    }


}

class Abilitylevel {
    String level;
    AbilityData abilityData;

    Abilitylevel(String level, AbilityData abilityData) {
        this.level = level;
        this.abilityData = abilityData;
    }

    Abilitylevel() {
    }
}

class typeholder {
    String Type;
    Abilitylevel[] Level;

    typeholder() {
    }

    typeholder(String type, Abilitylevel[] level) {
        Type = type;
        Level = level;
    }
}

