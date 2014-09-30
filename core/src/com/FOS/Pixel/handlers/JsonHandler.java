package com.FOS.Pixel.handlers;

import com.FOS.Pixel.Data.AbilityParser;
import com.FOS.Pixel.Data.AbilityType;
import com.FOS.Pixel.Data.LevelData;
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

    public static LevelData readLevel(int levelNumber) {
        OrderedMap<String, LevelData> jsonmap = json.fromJson(OrderedMap.class, Gdx.files.local("leveldata.json"));
        return jsonmap.get("level" + levelNumber);
    }

    public static void test() {
        AbilityParser test = new AbilityParser(1.1f, "jemoeder.png");
        ArrayList<typeholder> x = new ArrayList<typeholder>();
        x.add(new typeholder(AbilityType.SPEED.toString(), new Abilitylevel[]{
                new Abilitylevel("Level1", test),
                new Abilitylevel("Level2", test),
                new Abilitylevel("Level3", test),
                new Abilitylevel("Level4", test),
                new Abilitylevel("Level5", test)

        }));
        x.add(new typeholder(AbilityType.STRENGTH.toString(), new Abilitylevel[]{
                new Abilitylevel("Level1", test),
                new Abilitylevel("Level2", test),
                new Abilitylevel("Level3", test),
                new Abilitylevel("Level4", test),
                new Abilitylevel("Level5", test)

        }));
        x.add(new typeholder(AbilityType.JUMP.toString(), new Abilitylevel[]{
                new Abilitylevel("Level1", test),
                new Abilitylevel("Level2", test),
                new Abilitylevel("Level3", test),
                new Abilitylevel("Level4", test),
                new Abilitylevel("Level5", test)

        }));

        String printdata = json.prettyPrint(x);
        FileHandle fileHandle = Gdx.files.local("abilitydata.json");
        fileHandle.writeString(printdata, true);

    }

    public static AbilityParser getAbiltydata(String type, int level) {

        ArrayList<typeholder> test = json.fromJson(ArrayList.class, typeholder.class, Gdx.files.local("abilitydata.json"));

        Abilitylevel[] levelholder = null;

        for (typeholder x : test) {
            if (x.Type.equals( type)) {
                levelholder = x.Level;
            }
        }
        if (levelholder != null) {
            for (Abilitylevel x : levelholder) {
                if (x.level.equals( "Level"+level)){
                    return x.abilityData;

                }
            }
        }

        return null;
    }


}

class Abilitylevel {
    String level;
    AbilityParser abilityData;

    Abilitylevel(String level, AbilityParser abilityData) {
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
