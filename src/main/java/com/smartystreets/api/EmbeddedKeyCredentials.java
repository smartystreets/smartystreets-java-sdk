package com.smartystreets.api;

/**
 * EmbeddedKeyCredentials is useful if you want to use a website key without specifying a hostname.
 */
public class EmbeddedKeyCredentials implements Credentials {
    private final String id;

    public EmbeddedKeyCredentials(String id) {
        this.id = id;
    }

    public void sign(Request request) {
        request.putParameter("key", this.id);
    }
}
