package com.smartystreets.api.us_extract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartystreets.api.us_street.Candidate;

import java.io.Serializable;

/**
 * @see <a href="https://smartystreets.com/docs/cloud/us-extract-api#http-response-status">SmartyStreets US Extract API docs</a>
 */
public class Address implements Serializable {
    //region [ Fields ]

    private String text;
    private boolean verified;
    private int line;
    private int start;
    private int end;
    private Candidate[] candidates;

    //endregion

    //region [ Getters ]

    public String getText() {
        return text;
    }

    public boolean isVerified() {
        return verified;
    }

    public int getLine() {
        return line;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @JsonProperty("api_output")
    public Candidate[] getCandidates() {
        return candidates;
    }

    public Candidate getCandidate(int index) {
        return candidates[index];
    }

    //endregion
}
