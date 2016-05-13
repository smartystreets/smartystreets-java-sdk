package com.smartystreets.api;

import java.io.IOException;

public interface Serializer {

    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] payload, Class<T> type) throws IOException ;
}
