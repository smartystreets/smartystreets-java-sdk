package com.smartystreets.api.international_street;

import java.io.Serializable;

public class Changes extends RootLevel implements Serializable {

    //region [ Fields ]

    private Components components;

    //endregion

    //region [ Getters ]

    public Components getComponents() {
        return components;
    }

    //endregion
}