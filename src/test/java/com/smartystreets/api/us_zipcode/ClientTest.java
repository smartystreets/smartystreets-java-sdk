package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.Response;
import com.smartystreets.api.mocks.FakeDeserializer;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;

public class ClientTest {
    //region [ Single Lookup ]

    @Test
    public void testSendingSingleZipOnlyLookup() throws Exception {
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client("http://localhost/", sender, serializer);

        client.send(new Lookup("1"));

        assertEquals("http://localhost/?zipcode=1", sender.getRequest().getUrl());
    }

    @Test
    public void testSendingSingleFullyPopulatedLookup() throws Exception {
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client("http://localhost/", sender, serializer);
        Lookup lookup = new Lookup();
        lookup.setCity("1");
        lookup.setState("2");
        lookup.setZipCode("3");

        client.send(lookup);

        assertEquals("http://localhost/?city=1&state=2&zipcode=3", sender.getRequest().getUrl());
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
    public void testSuccessfullySendsBatchOfLookups() throws Exception {
        byte[] expectedPayload = "Hello, World!".getBytes();
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(expectedPayload);
        Client client = new Client("http://localhost/", sender, serializer);
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
        Client client = new Client("/", sender, deserializer);

        client.send(new Lookup());

        assertEquals(response.getPayload(), deserializer.getPayload());
    }

    @Test
    public void testCandidatesCorrectlyAssignedToCorrespondingLookup() throws Exception {
        Result[] expectedCandidates = new Result[2];
        expectedCandidates[0] = new Result();
        expectedCandidates[1] = new Result();
        Batch batch = new Batch();
        batch.add(new Lookup());
        batch.add(new Lookup());

        MockSender sender = new MockSender(new Response(0, "[]".getBytes()));
        FakeDeserializer deserializer = new FakeDeserializer(expectedCandidates);
        Client client = new Client("/", sender, deserializer);

        client.send(batch);

        assertEquals(expectedCandidates[0], batch.get(0).getResult());
        assertEquals(expectedCandidates[1], batch.get(1).getResult());
    }

    //endregion












    /******** PREVIOUS VERSION ******/

//    private Batch batch;
//    private Lookup lookup1;
//    private Lookup lookup2;
//    private Lookup lookup3;
//    private final String expectedJsonPayload = "[{\"city\":\"Washington\",\"state\":\"District of Columbia\",\"zipcode\":\"20500\"},{\"city\":\"Provo\",\"input_id\":\"test id\",\"state\":\"Utah\"},{\"zipcode\":\"54321\"}]";
//    private final String expectedJsonResponse = "[{\"input_index\":0,\"city_states\":[{\"city\":\"Washington\",\"state_abbreviation\":\"DC\",\"state\":\"District of Columbia\",\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"20500\",\"zipcode_type\":\"S\",\"default_city\":\"Washington\",\"county_fips\":\"11001\",\"county_name\":\"District of Columbia\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Provo\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":2,\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}]";
//    Request request;
//
//    @Before
//    public void setData() throws Exception {
//        this.batch = new Batch();
//        this.lookup1 = new Lookup("Washington", "District of Columbia", "20500");
//        this.lookup2 = new Lookup("Provo", "Utah").setInputId("test id");
//        this.lookup3 = new Lookup("54321");
//        this.batch.add(lookup1);
//        this.batch.add(lookup2);
//        this.batch.add(lookup3);
//        this.request = new Request("https://api.smartystreets.com/street-address");
//    }

//    @Test
//    public void testSending1Lookup() throws Exception {
//        Client client = new ClientBuilder().withSender(new MockSender()).build();
//
//        client.send(this.lookup2);
//
//        Result result = this.lookup2.getResult();
//
//        assertNotNull(result);
//        assertEquals("Salt Lake City", result.getCityState(0).getCity());
//    }
//
//    @Test
//    public void testSuccessfullySendsBatchOfLookups() throws Exception {
//        Client client = new ClientBuilder().withSender(new MockSender()).build();
//        client.send(this.batch);
//
//        this.assertFieldsAreCorrect();
//    }
//
//    @Test
//    public void testCorrectlyPopulatesQueryString() throws Exception {
//        Client client = new ClientBuilder().build();
//        client.populateQueryString(this.batch.get(0), this.request);
//
//        assertEquals("https://api.smartystreets.com/street-address?city=Washington&state=District+of+Columbia&zipcode=20500", request.getUrl());
//    }
//
//    @Test
//    public void testCorrectlyAssignsResultsToLookups() throws Exception {
//        Result[] results = new GoogleSerializer().deserialize(this.expectedJsonResponse.getBytes(), Result[].class);
//        Client client = new ClientBuilder().build();
//
//        client.assignResultsToLookups(this.batch, results);
//
//        this.assertFieldsAreCorrect();
//    }
//
//    private void assertFieldsAreCorrect() throws Exception {
//        Client client = new ClientBuilder().withSender(new MockSender()).build();
//        client.send(this.batch);
//
//        Result result = this.lookup1.getResult();
//
//        assertNotNull(result);
//        assertEquals("District of Columbia", result.getCityState(0).getState());
//        assertEquals("Washington", result.getZipCode(0).getDefaultCity());
//
//        result = this.lookup2.getResult();
//
//        assertNotNull(result);
//        assertEquals("Salt Lake City", result.getCityState(0).getCity());
//
//        result = this.lookup3.getResult();
//
//        assertNotNull(result);
//        assertNull(result.getCityStates());
//        assertEquals(2, result.getInputIndex());
//        assertEquals("invalid_zipcode", result.getStatus());
//        assertEquals("Invalid ZIP Code.", result.getReason());
//    }
}