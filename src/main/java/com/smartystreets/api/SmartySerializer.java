package com.smartystreets.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.json.JsonMapper;
import okhttp3.Headers;

import java.io.*;

public class SmartySerializer implements Serializer {

    public SmartySerializer() {}

    public byte[] serialize(Object obj) throws IOException {
        JsonMapper mapper = JsonMapper.builder()
            .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
            .build();
        return mapper.writeValueAsBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] payload, Class<T> type, Headers headers) throws IOException {
        JsonMapper mapper = JsonMapper.builder()
            .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
            .build();
        // Note: FAIL_ON_UNKNOWN_PROPERTIES is false by default in Jackson 3.x
        return mapper.readValue(payload, type);

    }

    public <T> T deserialize(byte[] payload, Class<T> type) throws IOException {
        JsonMapper mapper = JsonMapper.builder()
            .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
            .build();
        // Note: FAIL_ON_UNKNOWN_PROPERTIES is false by default in Jackson 3.x
        return mapper.readValue(payload, type);
    }
}
