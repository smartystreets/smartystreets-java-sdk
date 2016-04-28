package com.smartystreets.api;

import java.util.Map;

/**
 * Created by Neo on 4/27/16.
 */
public class Response {

    private int statusCode = 0;
    private String status;
    private Map<String, String> headers;
    private String rawJSON;

    public Response(){}

    public Response(int statusCode, Map<String, String> headers, String rawJSON) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.rawJSON = rawJSON;
    }

    /**** Getters ********************************************************************************/

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatus() {
        return this.status;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getRawJSON() {
        return this.rawJSON;
    }

    /**** Setters ********************************************************************************/

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setRawJSON(String rawJSON) {
        this.rawJSON = rawJSON;
    }
}
