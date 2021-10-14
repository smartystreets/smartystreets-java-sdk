package com.smartystreets.api.us_autocomplete;

import com.google.api.client.util.Key;

public class Result {
    @Key("suggestions")
    private Suggestion[] suggestions;

    public Suggestion[] getSuggestions() {
        return this.suggestions;
    }

    public Suggestion getSuggestion(int index) {
        return this.suggestions[index];
    }
}
