package com.smartystreets.api;

import com.google.api.client.http.HttpResponse;

import java.util.Map;

public class Response {

    private int statusCode = 0;
    private String status;
    private Map<String, String> headers;
    private String rawJSON;
    private HttpResponse innerResponse;

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

    public HttpResponse getInnerResponse() {
        return this.innerResponse;
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

    public void setInnerResponse(HttpResponse innerResponse) {
        this.innerResponse = innerResponse;
    }
}
