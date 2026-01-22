package com.smartystreets.api;

import java.util.Base64;

/**
 * BasicAuthCredentials uses HTTP Basic Authentication (RFC 7617) to send credentials
 * in the Authorization header.
 * <p>Look on the <b>API Keys</b> tab of your SmartyStreets account page to find/generate your keys.</p>
 */
public class BasicAuthCredentials implements Credentials {
    private String authId;
    private String authToken;

    public BasicAuthCredentials(String authId, String authToken) {
        if (authId == null || authId.isEmpty() || authToken == null || authToken.isEmpty()) {
            throw new IllegalArgumentException("credentials (auth id, auth token) required");
        }
        this.authId = authId;
        this.authToken = authToken;
    }

    public void sign(Request request) {
        String credentials = this.authId + ":" + this.authToken;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());
        request.putHeader("Authorization", "Basic " + encoded);
    }
}
