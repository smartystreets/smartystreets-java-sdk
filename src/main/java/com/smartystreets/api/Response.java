package com.smartystreets.api;

import java.util.Map;

public class Response {

    private int statusCode;
    private Map<String, String> headers;
    private byte[] payload;

    public Response(int statusCode, Map<String, String> headers, byte[] payload) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.payload = payload;
    }

    //region [ Getters ]

    public int getStatusCode() {
        return this.statusCode;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    //endregion

    //region [ Setters ]

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    //endregion
}


