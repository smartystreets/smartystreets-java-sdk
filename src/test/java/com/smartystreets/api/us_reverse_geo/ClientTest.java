package com.smartystreets.api.us_reverse_geo;

import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.junit.Assert.assertEquals;

public class ClientTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    //region [ Single Lookup ]

    @Test
    public void testAddressLookupSerializedAndSentWithContext() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/lookup", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);

        Lookup lookup = new Lookup(44.888888888, -111.111111111);
        client.send(lookup);

        assertEquals("http://localhost/lookup?latitude=44.88888889&longitude=-111.11111111", capturingSender.getRequest().getUrl());
    }

    //endregion
}