package com.smartystreets.api;


import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.ArrayList;

public class LicenseSender implements Sender{
    private ArrayList<String> licenses;
    private Sender inner;

    public LicenseSender(ArrayList<String> licenses, Sender inner) {
        this.licenses = licenses;
        this.inner = inner;
    }

    public Response send(Request request) throws IOException, SmartyException {
        if (!this.licenses.isEmpty()) {
            request.putParameter("license", String.join(",", this.licenses));
        }
        return this.inner.send(request);
    }
}
