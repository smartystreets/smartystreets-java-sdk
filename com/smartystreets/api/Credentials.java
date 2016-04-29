package com.smartystreets.api;

import com.smartystreets.api.exceptions.MissingAuthTokenOnPOSTException;

/**
 * Created by Neo on 4/25/16.
 */
public interface Credentials {
    void sign(Request request) throws MissingAuthTokenOnPOSTException;
}
