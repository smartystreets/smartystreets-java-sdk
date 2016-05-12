package com.smartystreets.api;

import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GoogleSerializer<T> implements Serializer<T> {
    private JacksonFactory factory;
    private Class<T> type;

    public GoogleSerializer(Class<T> type) {
        this.factory = new JacksonFactory();
        this.type = type;
    }

    public byte[] serialize(Object obj) throws IOException {
        return this.factory.toByteArray(obj);
    }

    public T deserialize(byte[] payload) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(payload);
        return this.factory.fromInputStream(inputStream, this.type);
    }
}