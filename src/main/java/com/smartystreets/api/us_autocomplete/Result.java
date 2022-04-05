package com.smartystreets.api.us_autocomplete;

import java.io.Serializable;

public class Result implements Serializable {
    private Suggestion[] suggestions;

    public Suggestion[] getSuggestions() {
        return this.suggestions;
    }

    public Suggestion getSuggestion(int index) {
        return this.suggestions[index];
    }
}
