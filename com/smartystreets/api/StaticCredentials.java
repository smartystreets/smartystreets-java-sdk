package com.smartystreets.api;

import com.smartystreets.api.exceptions.MissingAuthTokenOnPOSTException;

/**
 * Created by Neo on 4/25/16.
 */
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

    public void sign(Request request) throws MissingAuthTokenOnPOSTException {
        if (request.getMethod() == "POST" && this.authToken == null)
            throw new MissingAuthTokenOnPOSTException("POSTs require both an Auth Id and an Auth Token");

        String urlString = request.getUrlString();

        urlString += "auth-id=" + this.authId;

        if (authToken != null)
            urlString += "&auth-token=" + this.authToken;


        request.setUrlString(urlString);
    }

    /**** Getters ********************************************************************************/

    public String getAuthId() {
        return authId;
    }

    public String getAuthToken() {
        return authToken;
    }
}
