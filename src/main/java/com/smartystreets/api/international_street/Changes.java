package com.smartystreets.api.international_street;

import java.io.Serializable;

public class Changes extends RootLevel implements Serializable {

    //region [ Fields ]

    private String country;
    private Components components;

    //endregion

    //region [ Getters ]

    public String getCountry() {
        return country;
    }

    public Components getComponents() {
        return components;
    }

    //endregion
}