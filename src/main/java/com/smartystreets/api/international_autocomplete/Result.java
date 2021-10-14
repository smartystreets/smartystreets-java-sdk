package com.smartystreets.api.international_autocomplete;

import com.google.api.client.util.Key;

public class Result {
    @Key("candidates")
    private Candidate[] candidates;

    public Candidate[] getCandidates() {
        return this.candidates;
    }

    public Candidate getSuggestion(int index) {
        return this.candidates[index];
    }
}
