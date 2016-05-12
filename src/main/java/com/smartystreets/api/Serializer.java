package com.smartystreets.api;

import java.io.IOException;

public interface Serializer {

    byte[] serialize(Object obj) throws IOException;

    Object deserialize(byte[] payload, Class type) throws IOException ;
}
