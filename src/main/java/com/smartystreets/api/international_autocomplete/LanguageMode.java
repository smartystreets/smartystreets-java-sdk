package com.smartystreets.api.international_autocomplete;

/**
 * When not set, the output language will match the default for the country. When set to <b>NATIVE</b> the<br>
 *     results will always be in the language of the output country whenever possible. When set to <b>LATIN</b><br>
 *     the results will always be provided using the Latin character set, with accents and other diacritics removed.
 *     <p><b>Note: </b><i>For French diacritics in Canada, you must specify NATIVE.</i></p>
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
