package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.Closeable;
import java.io.IOException;

public interface Sender extends Closeable {
    Response send(Request request) throws SmartyException, IOException, InterruptedException;

    /**
     * Closes this sender and releases any system resources associated with it.
     * For the OkHttp-based sender, this shuts down connection pools and threads.
     * Default implementation does nothing.
     */
    @Override
    default void close() throws IOException {
        // Default no-op for decorators that don't hold resources
    }
}
