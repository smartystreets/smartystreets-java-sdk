package com.smartystreets.api.us_enrichment.result_types.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartystreets.api.us_enrichment.result_types.Result;

public class SecondaryCountResponse extends Result {
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
