package com.smartystreets.api;

import com.smartystreets.api.exceptions.MissingAuthTokenOnPOSTException;

public interface Credentials {
    void sign(Request request) throws MissingAuthTokenOnPOSTException;
}
