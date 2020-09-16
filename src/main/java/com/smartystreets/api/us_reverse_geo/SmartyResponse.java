package com.smartystreets.api.us_reverse_geo;
import com.google.api.client.util.Key;

public class SmartyResponse {

    @Key("results")
    private Result[] results;

    public Result[] getResults() { return this.results; }

}
