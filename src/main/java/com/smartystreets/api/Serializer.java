package com.smartystreets.api;

import com.smartystreets.api.us_reverse_geo.SmartyResponse;
import okhttp3.Headers;

import java.io.IOException;

public interface Serializer {
    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] payload, Class<T> type, Headers headers) throws IOException;

    <T> T deserialize(byte[] payload, Class<T> type) throws IOException;


}
