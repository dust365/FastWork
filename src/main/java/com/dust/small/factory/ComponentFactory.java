package com.dust.small.factory;

import com.dust.small.entity.ComponentInfo;

import java.io.File;
import java.util.List;

public abstract class ComponentFactory {

    protected File configFile;

    public abstract List<ComponentInfo> parse() throws Exception;

    public abstract boolean dump(List<ComponentInfo> componentInfoList);

    public ComponentFactory attach(File configFile) {
        this.configFile = configFile;
        return this;
    }

    public static ComponentFactory create(File configFile) {
        String configFileName = configFile.getName();
        if (configFileName.endsWith(".json")) {
            return JsonComponentFactory.getInstance().attach(configFile);
        } else if (configFileName.endsWith(".xml")) {
            return XmlComponentFactory.getInstance().attach(configFile);
        } else {
            return null;
        }
    }
}