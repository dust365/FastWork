package com.dust.small.entity;

import com.dust.small.service.IKPersistentStateService;

import org.apache.http.util.TextUtils;

public class ComponentInfo {

    public static final String DEFAULT_VERSION = "0.0.0";

    public static final String MODE_NONE = "none";
    public static final String MODE_BINARY = "binary";
    public static final String MODE_SRC = "src";

    public static final String TYPE_LOCAL = "local";
    public static final String TYPE_SNAPSHOT = "snapshot";
    public static final String TYPE_RELEASE = "release";

    public String name = "-";
    public String clazz = "-";
    public String version = DEFAULT_VERSION;
    public String type = TYPE_LOCAL;
    public String mode = MODE_SRC;
    public String resetMode;
    public int deep;

    //记录从配置文件中读取的依赖方式，不管assembleTab下怎么调整依赖，publishTab显示的是本地依赖列表
    public transient String originMode;
    public transient boolean publishDoc = Boolean.parseBoolean(IKPersistentStateService.getInstance()
            .getValue(IKPersistentStateService.STATUS_PUBLISH_WITH_DOC));
    public transient String publishMsg = "-";

    @Override
    public String toString() {
        return "ComponentInfo{" +
                ", name='" + name + '\'' +
                ", clazz='" + clazz + '\'' +
                ", version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return toString().equals(obj.toString());
    }

    public static String[] supportType() {
        return new String[]{TYPE_LOCAL, TYPE_SNAPSHOT, TYPE_RELEASE};
    }

    public static String[] supportMode() {
        return new String[]{MODE_NONE, MODE_BINARY, MODE_SRC};
    }

    public static boolean isValidName(String name) {
        return !TextUtils.isEmpty(name) && !name.equals("-");
    }
}
