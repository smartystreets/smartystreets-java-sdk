package com.smartystreets.api.us_extract;

import com.smartystreets.api.Response;
import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.mocks.FakeDeserializer;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ClientTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testSendingBodyOnlyLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        String expectedUrl = "http://localhost/?aggressive=false&addr_line_breaks=true&addr_per_line=0";
        byte[] expectedPayload = "Hello, World!".getBytes();

        client.send(new Lookup("Hello, World!"));

        assertEquals(expectedUrl, capturingSender.getRequest().getUrl());
        assertArrayEquals(expectedPayload, capturingSender.getRequest().getPayload());
    }

    @Test
    public void testSendingFullyPopulatedLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        String expectedUrl = "http://localhost/?html=true&aggressive=true&addr_line_breaks=false&addr_per_line=2";
        Lookup lookup = new Lookup("1");
        lookup.isHtml(true);
        lookup.isAggressive(true);
        lookup.setAddressesHaveLineBreaks(false);
        lookup.setAddressesPerLine(2);

        client.send(lookup);

        assertEquals(expectedUrl, capturingSender.getRequest().getUrl());
    }

    @Test
    public void testRejectNullLookup() throws IOException, SmartyException {
        exception.expect(SmartyException.class);

        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);

        client.send(null);

    }

    @Test
    public void testDeserializeCalledWithResponseBody() throws Exception {
        Response response = new Response(0, "Hello, World!".getBytes());
        MockSender sender = new MockSender(response);
        FakeDeserializer deserializer = new FakeDeserializer(null);
        Client client = new Client(sender, deserializer);

        client.send(new Lookup("Hello, World!"));

        assertEquals(response.getPayload(), deserializer.getPayload());
    }

    @Test
    public void testResultCorrectlyAssignedToCorrespondingLookup() throws Exception {
        Result expectedResult = new Result();
        Lookup lookup = new Lookup("Hello, World!");

        MockSender sender = new MockSender(new Response(0, "[]".getBytes()));
        FakeDeserializer deserializer = new FakeDeserializer(expectedResult);
        Client client = new Client(sender, deserializer);

        client.send(lookup);

        assertEquals(expectedResult, lookup.getResult());
    }

    @Test
    public void testContentTypeSetCorrectly() throws IOException, SmartyException {
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        Lookup lookup = new Lookup("Hello, World!");

        client.send(lookup);

        assertEquals("text/plain", sender.getRequest().getContentType());
    }
}
