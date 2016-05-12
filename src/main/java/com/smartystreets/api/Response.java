package com.smartystreets.api;

import com.google.api.client.http.HttpResponse;

import java.util.Map;

public class Response {

    private int statusCode = 0;
    private String status;
    private Map<String, String> headers;
    private byte[] rawResponse;
    private HttpResponse innerResponse;

    public Response(){}

    public Response(int statusCode, Map<String, String> headers, byte[] rawResponse) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.rawResponse = rawResponse;
    }

    //region [ Getters ]

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatus() {
        return this.status;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public byte[] getRawResponse() {
        return this.rawResponse;
    }

    public HttpResponse getInnerResponse() {
        return this.innerResponse;
    }

    //endregion

    //region [ Setters ]

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setRawResponse(byte[] rawResponse) {
        this.rawResponse = rawResponse;
    }

    public void setInnerResponse(HttpResponse innerResponse) {
        this.innerResponse = innerResponse;
    }

    //endregion
}


