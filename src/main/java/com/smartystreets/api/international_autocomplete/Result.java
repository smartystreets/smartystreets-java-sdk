package com.smartystreets.api.international_autocomplete;

import java.io.Serializable;

public class Result implements Serializable {
    private Candidate[] candidates;

    public Candidate[] getCandidates() {
        return this.candidates;
    }

    public Candidate getCandidate(int index) {
        return this.candidates[index];
    }
}
