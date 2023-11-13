package com.smartystreets.api;


import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class URLPrefixSender implements Sender{
    private String urlPrefix;
    private Sender inner;

    public URLPrefixSender(String urlPrefix, Sender inner) {
        this.urlPrefix = urlPrefix;
        this.inner = inner;
    }

    public Response send(Request request) throws IOException, SmartyException, InterruptedException {
        if(request.getUrlPrefix() != null && !request.getUrlPrefix().isEmpty()) {
            request.setUrlPrefix(this.urlPrefix + request.getUrlPrefix());
        } else {
            request.setUrlPrefix(this.urlPrefix);
        }
        return this.inner.send(request);
    }
}
