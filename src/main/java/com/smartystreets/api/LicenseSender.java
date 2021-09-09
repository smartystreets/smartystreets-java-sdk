package com.smartystreets.api;


import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.List;

public class LicenseSender implements Sender{
    private List<String> licenses;
    private Sender inner;

    public LicenseSender(List<String> licenses, Sender inner) {
        this.licenses = licenses;
        this.inner = inner;
    }

    public Response send(Request request) throws IOException, SmartyException {
        if (!this.licenses.isEmpty()) {
            StringBuilder licenses = new StringBuilder();
            for (String license : this.licenses)
            {
                licenses.append(license);
                licenses.append(",");
            }
            request.putParameter("license", licenses.toString());
        }
        return this.inner.send(request);
    }
}
