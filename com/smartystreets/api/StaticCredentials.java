package com.smartystreets.api;

public class StaticCredentials implements Credentials{
    private String authId;
    private String authToken;

    public StaticCredentials(String websiteKey) {
        this.authId = websiteKey;
    }

    public StaticCredentials(String authId, String authToken) {
        this.authId = authId;
        this.authToken = authToken;
    }

    public void sign(Request request) {
        request.appendParameter("auth-id", this.authId);
        request.appendParameter("auth-token", this.authToken);
    }
}
