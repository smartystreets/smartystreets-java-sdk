package com.smartystreets.api.us_autocomplete;


import com.smartystreets.api.SmartySerializer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SuggestionTest {
    private final SmartySerializer smartySerializer = new SmartySerializer();
    private static final String responsePayload = "{\"suggestions\":[{\"smarty_key\":\"1\",\"entry_id\":\"2\",\"street_line\":\"3\",\"city\":\"4\",\"state\":\"5\"}]}";

    @Test
    public void testAllFieldGetFilledInCorrectly() throws IOException {
        Result result = smartySerializer.deserialize(responsePayload.getBytes(), Result.class);

        assertNotNull(result.getSuggestions()[0]);
        assertEquals("1", result.getSuggestion(0).getSmartyKey());
        assertEquals("2", result.getSuggestion(0).getEntryId());
        assertEquals("3", result.getSuggestion(0).getStreetLine());
        assertEquals("4", result.getSuggestion(0).getCity());
        assertEquals("5", result.getSuggestion(0).getState());
    }
}
