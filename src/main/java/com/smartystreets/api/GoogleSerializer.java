package com.smartystreets.api;

import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GoogleSerializer implements Serializer {
    private final JacksonFactory factory;

    public GoogleSerializer() {
        this.factory = new JacksonFactory();
    }

    public byte[] serialize(Object obj) throws IOException {
        return this.factory.toByteArray(obj);
    }

    public <T> T deserialize(byte[] payload, Class<T> type) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(payload);
        return this.factory.fromInputStream(inputStream, type);
    }
}