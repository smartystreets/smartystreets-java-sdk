package com.smartystreets.api;

class CustomHeader {
    final Object value;
    final String separator;

    CustomHeader(Object value) {
        this(value, null);
    }

    CustomHeader(Object value, String separator) {
        this.value = value;
        this.separator = separator;
    }

    boolean isAppend() {
        return this.separator != null;
    }
}
