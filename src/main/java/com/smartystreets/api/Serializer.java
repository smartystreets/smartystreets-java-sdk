package com.smartystreets.api;

public interface Serializer<T> {

    byte[] serialize(Object obj);

    T deserialize(byte[] payload);
}
