package com.smartystreets.api.us_street;

public enum OutputFormat {
    DEFAULT("default"), PROJECT_USA("project-usa");

    private final String name;

    OutputFormat(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
