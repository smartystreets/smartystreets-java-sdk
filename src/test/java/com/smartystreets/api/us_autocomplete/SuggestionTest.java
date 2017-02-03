package com.smartystreets.api.us_autocomplete;


import com.smartystreets.api.GoogleSerializer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SuggestionTest {
    private final GoogleSerializer googleSerializer = new GoogleSerializer();
    private static final String responsePayload = "{\"suggestions\":[{\"text\":\"1\"}]}";

    @Test
    public void testAllFieldGetFilledInCorrectly() throws IOException {
        Result result = googleSerializer.deserialize(responsePayload.getBytes(), Result.class);

        assertNotNull(result.getSuggestions()[0]);
        assertEquals("1", result.getSuggestion(0).getText());
        //TODO: assert everything got assigned correctly
    }
}
