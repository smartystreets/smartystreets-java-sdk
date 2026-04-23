package com.smartystreets.api;

import okhttp3.Headers;

public class Response {
    private int statusCode;
    private byte[] payload;
    private Headers headers;


    public Response(int statusCode, byte[] payload) {
        this.statusCode = statusCode;
        this.payload = payload;
    }


    public Response(int statusCode, byte[] payload, Headers httpHeaders) {
        this.statusCode = statusCode;
        this.payload = payload;
        this.headers = httpHeaders;
    }


    //region [ Getters ]

    public Headers getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public String getEtag() {
        return headers == null ? null : headers.get("Etag");
    }

    //endregion
}


