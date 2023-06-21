package com.dust.small.factory;

import com.dust.small.entity.ComponentInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

//确保顺序的同时，忽略某些字段
public class ComponentInfoSerializer implements JsonSerializer<ComponentInfo> {
    @Override
    public JsonElement serialize(ComponentInfo componentInfo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject componentJson = new JsonObject();
        componentJson.addProperty("name", componentInfo.name);
        componentJson.addProperty("clazz", componentInfo.clazz);
        componentJson.addProperty("version", componentInfo.version);
        componentJson.addProperty("type", componentInfo.type);
        componentJson.addProperty("mode", componentInfo.mode);
        componentJson.addProperty("resetMode", componentInfo.resetMode);
        componentJson.addProperty("deep", componentInfo.deep);
        return componentJson;
    }
}
