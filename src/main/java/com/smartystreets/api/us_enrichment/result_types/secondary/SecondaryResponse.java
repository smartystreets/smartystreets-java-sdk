package com.smartystreets.api.us_enrichment.result_types.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartystreets.api.us_enrichment.result_types.Result;
import java.util.ArrayList;

public class SecondaryResponse extends Result {
    private String smartyKey;
    private RootAddress rootAddress;
    private ArrayList<Alias> aliases;
    private ArrayList<Secondary> secondaries;

    @JsonProperty("smarty_key")
    public String getSmartyKey() {
        return smartyKey;
    }

    @JsonProperty("root_address")
    public RootAddress getRootAddress() {
        return rootAddress;
    }

    @JsonProperty("aliases")
    public ArrayList<Alias> getAliases() {
        return aliases;
    }

    @JsonProperty("secondaries")
    public ArrayList<Secondary> getSecondaries() {
        return secondaries;
    }

    @Override
    public String toString() {
        return "SecondaryResponse{" +
                "smartyKey='" + getSmartyKey() + '\'' +
                ", rootAddress=" + getRootAddress() +
                ", aliases=" + getAliases() +
                ", secondaries=" + getSecondaries() +
                '}';
    }
}
