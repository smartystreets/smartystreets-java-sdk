package com.smartystreets.api.international_postal_code;

/**
 * When not set, results are returned using the Latin character set. When set to <b>NATIVE</b> (JPN only) the<br>
 *     results are returned in the native character set for that country. When set to <b>LATIN</b> the results<br>
 *     will always be provided using the Latin character set with accents and other diacritics removed.
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
