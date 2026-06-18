package com.smartystreets.api.us_autocomplete;

public enum Source {
    ALL("all"), POSTAL("postal");

    private final String name;

    Source(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
