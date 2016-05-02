package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyStreetsException;

import java.io.IOException;

public interface Sender {
    Response send(Request request) throws SmartyStreetsException, IOException;

    int getMaxTimeOut();
    void setMaxTimeOut(int maxTimeOut);
}
