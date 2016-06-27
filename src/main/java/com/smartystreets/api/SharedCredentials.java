package com.smartystreets.api;

public class SharedCredentials implements Credentials {
    private String id;
    private String hostname;

    public SharedCredentials(String id, String hostname) {
        this.id = id;
        this.hostname = hostname;
    }

    public void sign(Request request) {
        request.putParameter("auth-id", this.id);
        request.setHostname(this.hostname);
    }
}
