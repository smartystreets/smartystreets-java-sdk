package com.smartystreets.api.us_enrichment.result_types.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecondaryCountResponse {

    private String smartyKey;

    private int count;

    @JsonProperty("smarty_key")
    public String getSmartyKey() {
        return smartyKey;
    }

    @JsonProperty("count")
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "SecondaryCountResponse{" +
                "smartyKey='" + smartyKey + '\'' +
                ", count=" + count +
                '}';
    }
}
