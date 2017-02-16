package com.smartystreets.api.international_street;

/**
 * When not set, the output language will match the language of the input values. When set to <b>NATIVE</b> the<br>
 *     results will always be in the language of the output country. When set to <b>LATIN</b> the results<br>
 *     will always be provided using a Latin character set.
 */
public enum LanguageMode {
    NATIVE("native"), LATIN("latin");

    private final String name;

    LanguageMode(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
