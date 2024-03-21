package com.smartystreets.api.mocks;

import com.smartystreets.api.Serializer;
import okhttp3.Headers;

import java.io.IOException;

public class FakeDeserializer implements Serializer {

    private final Object deserialized;
    private byte[] payload;

    public FakeDeserializer(Object deserialized) {
        this.deserialized = deserialized;
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] payload, Class<T> type, Headers headers) throws IOException {
        this.payload = payload;
        return (T)deserialized;
    }

    @Override
    public <T> T deserialize(byte[] payload, Class<T> type) throws IOException {
        this.payload = payload;
        return (T)deserialized;
    }

    public byte[] getPayload() {
        return this.payload;
    }
}
