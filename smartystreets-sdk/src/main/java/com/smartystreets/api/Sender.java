package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public interface Sender {
    String VERSION = "alpha";
    Response send(Request request) throws SmartyException, IOException;
}
