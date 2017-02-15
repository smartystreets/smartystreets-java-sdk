package com.smartystreets.api.mocks;

import com.smartystreets.api.Serializer;
import java.io.IOException;

public class FakeSerializer implements Serializer {

    private final byte[] bytes;
    private Object result;

    public FakeSerializer(byte[] bytes) {
        this.bytes = bytes;
    }

    public FakeSerializer(Object result) {
        this.result = result;
        this.bytes = null;
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return this.bytes;
    }

    @Override
    public <T> T deserialize(byte[] payload, Class<T> type) throws IOException {
        return (T)this.result;
    }
}
