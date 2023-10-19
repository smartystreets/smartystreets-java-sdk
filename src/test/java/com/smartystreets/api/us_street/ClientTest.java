package com.smartystreets.api.us_street;

import com.smartystreets.api.Response;
import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.mocks.FakeDeserializer;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {

    //region [ Single Lookup ]

    @Test
    public void testSendingSingleFreeformLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);

        client.send(new Lookup("freeform"));

        assertEquals("http://localhost/?street=freeform&candidates=1", capturingSender.getRequest().getUrl());

    }

    @Test
    public void testSendingSingleFullyPopulatedLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        Lookup lookup = new Lookup();
        lookup.setInputId("1234");
        lookup.setAddressee("0");
        lookup.setStreet("1");
        lookup.setSecondary("2");
        lookup.setStreet2("3");
        lookup.setUrbanization("4");
        lookup.setCity("5");
        lookup.setState("6");
        lookup.setZipCode("7");
        lookup.setLastline("8");
        lookup.setMatch(MatchType.ENHANCED);

        client.send(lookup);

        assertEquals("http://localhost/?input_id=1234&street=1&street2=3" +
                "&secondary=2&city=5&state=6&zipcode=7&lastline=8&addressee=0" +
                "&urbanization=4&match=enhanced&candidates=5", capturingSender.getRequest().getUrl());

    }

    @Test
    public void testSendingSingleFullyPopulatedLookupWithFormatOutput() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        Lookup lookup = new Lookup();
        lookup.setInputId("1234");
        lookup.setAddressee("0");
        lookup.setStreet("1");
        lookup.setSecondary("2");
        lookup.setStreet2("3");
        lookup.setUrbanization("4");
        lookup.setCity("5");
        lookup.setState("6");
        lookup.setZipCode("7");
        lookup.setLastline("8");
        lookup.setMatch(MatchType.ENHANCED);
        lookup.setFormat(OutputFormat.PROJECT_USA);

        client.send(lookup);

        assertEquals("http://localhost/?input_id=1234&street=1&street2=3" +
                "&secondary=2&city=5&state=6&zipcode=7&lastline=8&addressee=0" +
                "&urbanization=4&match=enhanced&format=project-usa&candidates=5", capturingSender.getRequest().getUrl());

    }

    //endregion

    //region [ Batch Lookup ]

    @Test
    public void testEmptyBatchNotSent() {
        RequestCapturingSender sender = new RequestCapturingSender();
        Client client = new Client(sender, null);

        assertThrows(SmartyException.class, () -> client.send(new Batch()));
    }

    @Test
    public void testSuccessfullySendsBatchOfAddressLookups() throws Exception {
        byte[] expectedPayload = "Hello, World!".getBytes();
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(expectedPayload);
        Client client = new Client(sender, serializer);
        Batch batch = new Batch();
        batch.add(new Lookup());
        batch.add(new Lookup());

        client.send(batch);

        assertArrayEquals(expectedPayload, sender.getRequest().getPayload());
    }

    //endregion

    //region [ Response Handling ]

    @Test
    public void testDeserializeCalledWithResponseBody() throws Exception {
        Response response = new Response(0, "Hello, World!".getBytes());
        MockSender sender = new MockSender(response);
        FakeDeserializer deserializer = new FakeDeserializer(null);
        Client client = new Client(sender, deserializer);

        client.send(new Lookup());

        assertEquals(response.getPayload(), deserializer.getPayload());
    }

    @Test
    public void testCandidatesCorrectlyAssignedToCorrespondingLookup() throws Exception {
        Candidate[] expectedCandidates = new Candidate[3];
        expectedCandidates[0] = new Candidate(0);
        expectedCandidates[1] = new Candidate(1);
        expectedCandidates[2] = new Candidate(1);
        Batch batch = new Batch();
        batch.add(new Lookup());
        batch.add(new Lookup());

        MockSender sender = new MockSender(new Response(0, "[]".getBytes()));
        FakeDeserializer deserializer = new FakeDeserializer(expectedCandidates);
        Client client = new Client(sender, deserializer);

        client.send(batch);

        assertEquals(expectedCandidates[0], batch.getAllLookups().get(0).getResult(0));
        assertEquals(expectedCandidates[1], batch.getAllLookups().get(1).getResult(0));
        assertEquals(expectedCandidates[2], batch.getAllLookups().get(1).getResult(1));
    }

    //endregion
}