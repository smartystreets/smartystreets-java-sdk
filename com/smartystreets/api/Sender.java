package com.smartystreets.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * Created by Neo on 4/25/16.
 */
public interface Sender {
    Response send(Request request) throws IOException;
}
