package com.smartystreets.api;

/**
 * Created by Neo on 4/25/16.
 */
public interface Credentials {
    void sign(Request request) throws Exception;
}
