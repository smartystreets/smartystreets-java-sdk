package com.smartystreets.api.mocks;

import com.smartystreets.api.Serializer;
import java.io.IOException;

public class FakeSerializer implements Serializer {

    private final byte[] bytes;

    public FakeSerializer(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return this.bytes;
    }

    @Override
    public <T> T deserialize(byte[] payload, Class<T> type) throws IOException {
        return null;
    }
}
