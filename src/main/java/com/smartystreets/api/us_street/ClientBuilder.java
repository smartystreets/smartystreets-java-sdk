package com.smartystreets.api.us_street;

import com.smartystreets.api.*;

public class ClientBuilder {
    private Credentials signer;
    private Sender httpSender;
    private int maxRetries;
    private int maxTimeout;

    public ClientBuilder() {
        this.maxRetries = 5;
        this.maxTimeout = 10000;
    }

    public ClientBuilder(Credentials signer) {
        this();
        this.signer = signer;
    }

    public ClientBuilder(String authId, String authToken) {
        this(new StaticCredentials(authId, authToken));
    }

    public ClientBuilder retryAtMost(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public ClientBuilder withMaxTimeout(int maxTimeout) {
        this.maxTimeout = maxTimeout;
        return this;
    }

    public ClientBuilder withSender(Sender sender) {
        this.httpSender = sender;
        return this;
    }

    public Client build() {
        return new Client(this.signer, this.buildSender());
    }

    public Sender buildSender() {
        if (this.httpSender != null)
            return this.httpSender;

        Sender sender = new HttpSender(this.maxTimeout);

        if (this.maxRetries > 0)
            sender = new RetrySender(this.maxRetries, sender);

        return sender;
    }
}
