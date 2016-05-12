package com.smartystreets.api;

import java.io.IOException;

public interface Serializer<T> {

    byte[] serialize(Object obj) throws IOException;

    T deserialize(byte[] payload) throws IOException ;
}
