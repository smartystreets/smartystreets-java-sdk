package com.smartystreets.api;

public enum InternationalGeolocateType {

    ADMIN_AREA("adminarea"), LOCALITY("locality"), POSTAL_CODE("postalcode"), GEOCODES("geocodes"), NONE("");
    private final String name;

    InternationalGeolocateType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
