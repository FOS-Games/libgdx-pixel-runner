package com.FOS.Pixel.handlers;

import com.FOS.Pixel.Data.AbilityParser;
import com.FOS.Pixel.Data.AbilityType;
import com.FOS.Pixel.Data.LevelData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Created by Lars on 9/29/2014.
 */


public class JsonHandler {
    private static final String JSON_PATH ="";
    private static Json json = new Json();
    private static JsonReader jsonReader = new JsonReader();

    public JsonHandler(String path){}

    public static LevelData readLevel(int levelNumber){
        OrderedMap<String,LevelData> jsonmap = json.fromJson(OrderedMap.class, Gdx.files.local("leveldata.json"));
        return jsonmap.get("level"+levelNumber);
    }

    public static void test(){
        AbilityParser test = new AbilityParser(1.1f,"jemoeder.png");
        OrderedMap<String,OrderedMap<String,AbilityParser>> jsontest = new OrderedMap<String, OrderedMap<String, AbilityParser>>();
        OrderedMap<String,AbilityParser> abilitymap = new OrderedMap<String, AbilityParser>();
        abilitymap.put("Level1",test);
        abilitymap.put("Level2",test);
        abilitymap.put("Level3",test);
        jsontest.put(AbilityType.SPEED.toString(), abilitymap);


        jsontest.put(AbilityType.STRENGTH.toString(), abilitymap);
        jsontest.put(AbilityType.JUMP.toString(), abilitymap);
        String printdata = json.prettyPrint(jsontest);
        FileHandle fileHandle = Gdx.files.local("abilitydata.json");
        fileHandle.writeString(printdata, true);

    }

    public  static AbilityParser getAbiltydata(AbilityType type, int level){

        OrderedMap<String,OrderedMap<String,AbilityParser>> abilityTypeOrderedMap = json.fromJson(OrderedMap.class, Gdx.files.local("abilitydata.json"));
        OrderedMap<String,AbilityParser> levelOrderMap = abilityTypeOrderedMap.get(type.toString());

        return levelOrderMap.get("Level"+level);
    }


}

class Abilitylevel{
    String level;
    AbilityParser abilityData;

    Abilitylevel(String level, AbilityParser abilityData) {
        this.level = level;
        this.abilityData = abilityData;
    }

    Abilitylevel() {
    }
}

