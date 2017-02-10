package com.smartystreets.api.international_street;

public enum LanguageMode {
    MATCH_INPUT("null"), NATIVE("native"), LATIN("latin");

    private final String name;

    LanguageMode(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
