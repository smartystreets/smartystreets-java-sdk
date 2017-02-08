package com.smartystreets.api.us_extract;

import com.smartystreets.api.GoogleSerializer;
import com.smartystreets.api.us_street.Candidate;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ResultTest {
    private final GoogleSerializer googleSerializer = new GoogleSerializer();
    private static final String responsePayload = "{\"meta\":{\"lines\":1,\"unicode\":true,\"address_count\":2,\"verified_count\":3,\"bytes\":4,\"character_count\":5},\"addresses\":[{\"text\":\"6\",\"verified\":true,\"line\":7,\"start\":8,\"end\":9,\"api_output\":[{}]},{\"text\":\"10\"}]}";

    @Test
    public void testAllFieldsFilledCorrectly() throws IOException {
        Result result = this.googleSerializer.deserialize(responsePayload.getBytes(), Result.class);

        Metadata metadata = result.getMetadata();
        assertNotNull(metadata);
        assertEquals(1, metadata.getLines());
        assertEquals(true, metadata.isUnicode());
        assertEquals(2, metadata.getAddressCount());
        assertEquals(3, metadata.getVerifiedCount());
        assertEquals(4, metadata.getBytes());
        assertEquals(5, metadata.getCharacterCount());

        Address address = result.getAddress(0);
        assertNotNull(address);
        assertEquals("6", address.getText());
        assertEquals(true, address.isVerified());
        assertEquals(7, address.getLine());
        assertEquals(8, address.getStart());
        assertEquals(9, address.getEnd());
        assertEquals("10", result.getAddresses()[1].getText());

        Candidate[] candidates = address.getCandidates();
        assertNotNull(candidates);
    }
}
