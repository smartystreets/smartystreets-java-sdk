package com.smartystreets.api;

public enum InternationalGeolocateType {

    NONE(""), IP_ADDRESS("ip_address");
    private final String name;

    InternationalGeolocateType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
