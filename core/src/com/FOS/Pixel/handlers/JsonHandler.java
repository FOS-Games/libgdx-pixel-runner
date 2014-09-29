package com.FOS.Pixel.handlers;

import com.FOS.Pixel.LevelData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Created by Lars on 9/29/2014.
 */


public class JsonHandler {
    private String JSON_PATH;
    private Json json = new Json();
    JsonReader jsonReader = new JsonReader();

    public JsonHandler(String path){
        this.JSON_PATH = path;

    }

//    public LevelData readLevel(int levelNumber){
//        System.out.println(jsonReader.parse(Gdx.files.internal(JSON_PATH + "/leveldata.json")));
//
//        return  new LevelData();
//    }



}
