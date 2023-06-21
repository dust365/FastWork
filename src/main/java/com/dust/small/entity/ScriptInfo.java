package com.dust.small.entity;

public class ScriptInfo {

    public String name;
    public String description;
    public String code;

    @Override
    public String toString() {
        return "ScriptInfo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
