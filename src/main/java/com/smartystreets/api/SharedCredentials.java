package com.smartystreets.api;

/**
 * SharedCredentials is useful if you want to use a website key.
 */
public class SharedCredentials implements Credentials {
    private String id;
    private String hostname;

    public SharedCredentials(String id, String hostname) {
        this.id = id;
        this.hostname = hostname;
    }

    public void sign(Request request) {
        request.putParameter("key", this.id);
        request.putHeader("Referer", this.hostname);
    }
}
