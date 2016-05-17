package com.smartystreets.api;

import java.util.Map;

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

    //region [ Setters ]

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    //endregion
}


