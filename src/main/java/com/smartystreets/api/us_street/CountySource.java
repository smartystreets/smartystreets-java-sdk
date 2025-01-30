package com.smartystreets.api.us_street;

public enum CountySource {
    POSTAL("postal"), GEOGRAPHIC("geographic");

    private final String name;

    CountySource(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
