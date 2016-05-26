package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class SigningSender implements Sender {
    private final Credentials signer;
    private final Sender inner;

    public SigningSender(Credentials signer, Sender inner) {
        this.signer = signer;
        this.inner = inner;
    }

    public Response send(Request request) throws SmartyException, IOException {
        this.signer.sign(request);
        return this.inner.send(request);
    }
}
