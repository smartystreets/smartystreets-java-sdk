package com.smartystreets.api.international_street;

import com.google.api.client.util.Key;

public class Changes extends RootLevel {

    //region [ Fields ]

    @Key("components")
    private Components components;

    //endregion

    //region [ Getters ]

    public Components getComponents() {
        return components;
    }

    //endregion
}