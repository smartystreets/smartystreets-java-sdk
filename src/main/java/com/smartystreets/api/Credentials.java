package com.smartystreets.api;

/**
 * Credentials are classes that 'sign' requests by adding SmartyStreets authentication keys.
 */
public interface Credentials {
    void sign(Request request);
}
