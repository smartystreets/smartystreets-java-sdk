package com.smartystreets.api.us_reverse_geo;

import java.io.Serializable;

public class SmartyResponse implements Serializable {

    private Result[] results;

    public Result[] getResults() { return this.results; }

}
