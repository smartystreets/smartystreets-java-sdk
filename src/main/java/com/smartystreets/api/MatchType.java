package com.smartystreets.api;

public enum MatchType {
    STRICT("strict"), RANGE("range"), INVALID("invalid");

    private final String name;

    MatchType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
