package com.smartystreets.api;

class CustomHeader {
    private final Object value;
    private final String separator;

    CustomHeader(Object value) {
        this(value, null);
    }

    CustomHeader(Object value, String separator) {
        this.value = value;
        this.separator = separator;
    }

    Object getValue() {
        return this.value;
    }

    String getSeparator() {
        return this.separator;
    }

    boolean isAppend() {
        return this.separator != null;
    }
}
