package com.smartystreets.api;

public class StaticCredentials implements Credentials {
    private String authId;
    private String authToken;

    public StaticCredentials(String authId, String authToken) {
        this.authId = authId;
        this.authToken = authToken;
    }

    public void sign(Request request) {
        request.putParameter("auth-id", this.authId);
        request.putParameter("auth-token", this.authToken);
    }
}
