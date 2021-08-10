package com.smartystreets.api.us_street;

public enum MatchType {
    STRICT("strict"), RANGE("range"), INVALID("invalid"), ENHANCED("enhanced");

    private final String name;

    MatchType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
