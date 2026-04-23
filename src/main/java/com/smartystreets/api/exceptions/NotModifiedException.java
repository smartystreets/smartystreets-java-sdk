package com.smartystreets.api.exceptions;

public class NotModifiedException extends SmartyException {
    private final String responseEtag;

    public NotModifiedException() {
        super();
        this.responseEtag = null;
    }

    public NotModifiedException(String message) {
        super(message);
        this.responseEtag = null;
    }

    public NotModifiedException(String message, String responseEtag) {
        super(message);
        this.responseEtag = responseEtag;
    }

    public String getResponseEtag() {
        return this.responseEtag;
    }
}
