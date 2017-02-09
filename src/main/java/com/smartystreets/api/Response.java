package com.smartystreets.api;

public class Response {
    private int statusCode;
    private byte[] payload;

    public Response(int statusCode, byte[] payload) {
        this.statusCode = statusCode;
        this.payload = payload;
    }

    //region [ Getters ]

    public int getStatusCode() {
        return this.statusCode;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    //endregion
}


