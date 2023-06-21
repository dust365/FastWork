package com.dust.small.factory;

import com.dust.small.entity.ComponentInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonComponentFactory extends ComponentFactory {

    private Gson mGson;

    private JsonComponentFactory() {
        mGson = new GsonBuilder()
                .registerTypeAdapter(ComponentInfo.class, new ComponentInfoSerializer())
                .setPrettyPrinting()
                .create();
    }

    private static ComponentFactory INSTANCE = new JsonComponentFactory();

    public static ComponentFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ComponentInfo> parse() throws Exception {
        try (FileReader fileReader = new FileReader(configFile)) {
            return mGson.fromJson(fileReader, new TypeToken<ArrayList<ComponentInfo>>() {
            }.getType());
        }
    }

    @Override
    public boolean dump(List<ComponentInfo> componentInfoList) {
        try (FileWriter fileWriter = new FileWriter(configFile)) {
            mGson.toJson(componentInfoList, componentInfoList.getClass(), fileWriter);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
