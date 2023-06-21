package com.dust.small.service;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 可注册成Service,也可以注册成Component
 */
@State(name = "IKComponentCompile", storages = {@Storage("ik-compile.xml")})
public class IKPersistentStateService implements PersistentStateComponent<Element> {

    public static final String PATH_DOCUMENT = "path_document";
    public static final String PATH_UPDATE = "path_update";
    public static final String PATH_LAUNCH_TIP = "path_launch_tip";
    public static final String VALUE_MAX_VERSION = "value_max_version";
    public static final String STATUS_PUBLISH_WITH_DOC = "status_publish_with_doc";
    public static final String SHOW_PERFORM_APK_TIP = "show_perform_apk_tip";
    public static final String CLEAN_COMPONENT_SORT = "clean_component_sort";

    public static final String DEFAULT_DOCUMENT = "http://10.111.174.27:9191/help.html";
    public static final String DEFAULT_UPDATE = "http://10.111.174.27:9191/version.json";
    public static final String DEFAULT_LAUNCH_TIP = "http://10.111.174.27:9191/launchtip.json";
    public static final int DEFAULT_MAX_VERSION = 100;
    public static final boolean DEFAULT_PUBLISH_WITH_DOC = false;
    public static final boolean DEFAULT_SHOW_PERFORM_APK_TIP = true;
    public static final String DEFAULT_CLEAN_COMPONENT_SORT = "0"; //0：正序 1：逆序

    private Map<String, String> mValueMap;

    private IKPersistentStateService() {
        mValueMap = new HashMap<>();
        mValueMap.put(PATH_DOCUMENT, DEFAULT_DOCUMENT);
        mValueMap.put(PATH_UPDATE, DEFAULT_UPDATE);
        mValueMap.put(PATH_LAUNCH_TIP, DEFAULT_LAUNCH_TIP);
        mValueMap.put(VALUE_MAX_VERSION, String.valueOf(DEFAULT_MAX_VERSION));
        mValueMap.put(STATUS_PUBLISH_WITH_DOC, String.valueOf(DEFAULT_PUBLISH_WITH_DOC));
        mValueMap.put(SHOW_PERFORM_APK_TIP, String.valueOf(DEFAULT_SHOW_PERFORM_APK_TIP));
        mValueMap.put(CLEAN_COMPONENT_SORT, DEFAULT_CLEAN_COMPONENT_SORT);
    }

    public static IKPersistentStateService getInstance() {
        return ServiceManager.getService(IKPersistentStateService.class);
    }

    @Nullable
    @Override
    public Element getState() {
        Element rootElement = new Element("root");
        for (Map.Entry<String, String> entry : mValueMap.entrySet()) {
            Element element = new Element(entry.getKey());
            element.setText(entry.getValue());
            rootElement.addContent(element);
        }
        return rootElement;
    }

    @Override
    public void loadState(@NotNull Element element) {
        List<Element> children = element.getChildren();
        for (Element child : children) {
            mValueMap.put(child.getName(), child.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    public String getValue(String key) {
        return mValueMap.get(key);
    }

    public void setValue(String key, String value) {
        mValueMap.put(key, value);
    }
}
