package com.smartystreets.api.us_street;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.mocks.FakeDeserializer;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ClientTest {
    //region [ Single Lookup ]

    @Test
    public void testSendingSingleFreeformLookup() throws Exception {
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client("http://localhost/", sender, serializer);

        client.send(new AddressLookup("freeform"));

        assertEquals("http://localhost/?street=freeform", sender.getRequest().getUrl());
    }

    @Test
    public void testSendingSingleFullyPopulatedLookup() throws Exception {
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client("http://localhost/", sender, serializer);
        AddressLookup lookup = new AddressLookup();
        lookup.setAddressee("0");
        lookup.setStreet("1");
        lookup.setSecondary("2");
        lookup.setStreet2("3");
        lookup.setUrbanization("4");
        lookup.setCity("5");
        lookup.setState("6");
        lookup.setZipCode("7");
        lookup.setLastline("8");
        lookup.setMaxCandidates(9);

        client.send(lookup);

        assertEquals("http://localhost/?street=1&street2=3&secondary=2&city=5&state=6&zipcode=7&lastline=8&addressee=0&urbanization=4&candidates=9", sender.getRequest().getUrl());
    }

    //endregion

    //region [ Batch Lookup ]

    @Test
    public void testEmptyBatchNotSent() throws Exception {
        RequestCapturingSender sender = new RequestCapturingSender();
        Client client = new Client("/", sender, null);

        client.send(new Batch());

        assertNull(sender.getRequest());
    }

    @Test
    public void testSuccessfullySendsBatchOfAddressLookups() throws Exception {
        byte[] expectedPayload = "Hello, World!".getBytes();
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(expectedPayload);
        Client client = new Client("http://localhost/", sender, serializer);
        Batch batch = new Batch();
        batch.add(new AddressLookup());
        batch.add(new AddressLookup());

        client.send(batch);

        assertArrayEquals(expectedPayload, sender.getRequest().getPayload());
    }

    //endregion

    //region [ Request Headers ]

    @Test
    public void testNoHeadersAddedToRequest() throws Exception {
        this.assertHeadersSetCorrectly(false, false);
    }

    @Test
    public void testIncludeInvalidHeaderCorrectlyAddedToRequest() throws Exception {
        this.assertHeadersSetCorrectly(true, false);
    }

    @Test
    public void testStandardizeOnlyHeaderCorrectlyAddedToRequest() throws Exception {
        this.assertHeadersSetCorrectly(false, true);
    }

    @Test
    public void testIncludeInvalidHeaderCorrectlyAddedToRequestWhenBothBatchOptionsAreSet() throws Exception {
        this.assertHeadersSetCorrectly(true, true);
    }

    private void assertHeadersSetCorrectly(boolean includeInvalid, boolean standardizeOnly) throws Exception {
        RequestCapturingSender sender = new RequestCapturingSender();
        Client client = new Client("http://localhost/", sender, new FakeSerializer(new byte[0]));
        Batch batch = new Batch();
        batch.add(new AddressLookup());

        batch.setStandardizeOnly(standardizeOnly);
        batch.setIncludeInvalid(includeInvalid);
        client.send(batch);

        Request request = sender.getRequest();
        Map<String, String> headers = request.getHeaders();

        if (includeInvalid) {
            assertEquals("true", headers.get("X-Include-Invalid"));
            assertNull(headers.get("X-Standardize-Only"));
        } else if (standardizeOnly) {
            assertEquals("true", headers.get("X-Standardize-Only"));
            assertNull(headers.get("X-Include-Invalid"));
        } else {
            assertNull(headers.get("X-Include-Invalid"));
            assertNull(headers.get("X-Standardize-Only"));
        }
    }

    //endregion

    //region [ Response Handling ]

    @Test
    public void testDeserializeCalledWithResponseBody() throws Exception {
        Response response = new Response(0, "Hello, World!".getBytes());
        MockSender sender = new MockSender(response);
        FakeDeserializer deserializer = new FakeDeserializer(null);
        Client client = new Client("/", sender, deserializer);

        client.send(new AddressLookup());

        assertEquals(response.getPayload(), deserializer.getPayload());
    }

    @Test
    public void testCandidatesCorrectlyAssignedToCorrespondingLookup() throws Exception {
        Candidate[] expectedCandidates = new Candidate[3];
        expectedCandidates[0] = new Candidate(0);
        expectedCandidates[1] = new Candidate(1);
        expectedCandidates[2] = new Candidate(1);
        Batch batch = new Batch();
        batch.add(new AddressLookup());
        batch.add(new AddressLookup());

        MockSender sender = new MockSender(new Response(0, "[]".getBytes()));
        FakeDeserializer deserializer = new FakeDeserializer(expectedCandidates);
        Client client = new Client("/", sender, deserializer);

        client.send(batch);

        assertEquals(expectedCandidates[0], batch.get(0).getResult(0));
        assertEquals(expectedCandidates[1], batch.get(1).getResult(0));
        assertEquals(expectedCandidates[2], batch.get(1).getResult(1));
    }

    //endregion
}