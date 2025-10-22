package com.smartystreets.api.us_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class MatchInfo implements Serializable {
    private String status;


    private List<String> change;

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }
    
    @JsonProperty("change")
    public List<String> getChange() {
        return change;
    }
}

