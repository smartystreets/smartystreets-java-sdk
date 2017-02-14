package com.smartystreets.api;

/**
 * StaticCredentials takes a SmartyStreets Secret Key Pair, and 'signs' the request with it so the<br>
 * SmartyStreets API knows which SmartyStreets account and subscription is sending it.
 * <p>Look on the <b>API Keys</b> tab of your SmartyStreets account page to find/generate your keys.</p>
 */
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
