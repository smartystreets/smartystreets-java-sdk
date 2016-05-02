package com.smartystreets.api.us_street;

import com.smartystreets.api.*;

public class ClientBuilder {
    private Credentials signer;
    private int maxRetries = 5;
    private int maxTimeOut = 10000;
    private Sender httpSender = new HttpSender();

    public ClientBuilder() {

    }

    public ClientBuilder(Credentials signer) {
        this.signer = signer;
    }

    public ClientBuilder(String authId, String authToken) {
        this.signer = new StaticCredentials(authId, authToken);
    }

    public ClientBuilder retryAtMost(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public ClientBuilder withMaxTimeout(int maxTimeout) {
        this.maxTimeOut = maxTimeout;
        return this;
    }

    public ClientBuilder withSender(Sender sender) {
        this.httpSender = sender;
        return this;
    }

    public Client build() {
        httpSender.setMaxTimeOut(maxTimeOut);
        Client client = new Client(this.signer, new RetrySender(maxRetries, httpSender));

        return client;
    }
}
