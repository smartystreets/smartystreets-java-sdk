package com.smartystreets.api.us_extract;


import com.google.api.client.util.Key;
import com.smartystreets.api.us_street.Candidate;

/**
 * @see <a href="https://smartystreets.com/docs/cloud/us-extract-api#http-response-status">SmartyStreets US Extract API docs</a>
 */
public class Address {
    //region [ Fields ]

    @Key("text")
    private String text;

    @Key("verified")
    private boolean verified;

    @Key("line")
    private int line;

    @Key("start")
    private int start;

    @Key("end")
    private int end;

    @Key("api_output")
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

    public Candidate[] getCandidates() {
        return candidates;
    }

    public Candidate getCandidate(int index) {
        return candidates[index];
    }

    //endregion
}
