package com.smartystreets.api.international_autocomplete;

import com.smartystreets.api.InternationalGeolocateType;
import com.smartystreets.api.Response;
import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.mocks.FakeDeserializer;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ClientTest {
    //region [ Single Lookup ]

    @Test
    public void testSendingSinglePrefixOnlyLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new Result());
        Client client = new Client(sender, serializer);

        client.send(new Lookup("1"));

        assertEquals("http://localhost/?search=1&max_results=5&distance=5", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingSinglePartiallyPopulatedLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new Result());
        Client client = new Client(sender, serializer);
        String expectedURL = "http://localhost/?country=1&search=2&max_results=5&distance=5&include_only_administrative_area=3&include_only_locality=4&include_only_postal_code=5";
        Lookup lookup = new Lookup();
        lookup.setCountry("1");
        lookup.setSearch("2");
        lookup.setAdministrativeArea("3");
        lookup.setLocality("4");
        lookup.setPostalCode("5");

        client.send(lookup);

        assertEquals(expectedURL, capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingSingleFullyPopulatedLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new Result());
        Client client = new Client(sender, serializer);
        String expectedURL = "http://localhost/?country=1&search=2&max_results=10&distance=8&geolocation=postalcode&include_only_administrative_area=3&include_only_locality=4&include_only_postal_code=5&latitude=10.1&longitude=11.2";
        Lookup lookup = new Lookup();
        lookup.setCountry("1");
        lookup.setSearch("2");
        lookup.setMaxResults(10);
        lookup.setDistance(8);
        lookup.setGeolocation(InternationalGeolocateType.POSTAL_CODE);
        lookup.setAdministrativeArea("3");
        lookup.setLocality("4");
        lookup.setPostalCode("5");
        lookup.setLatitude(10.1f);
        lookup.setLongitude(11.2f);

        client.send(lookup);

        assertEquals(expectedURL, capturingSender.getRequest().getUrl());
    }

    //endregion

    //region [ Response Handling ]

    @Test
    public void testDeserializeCalledWithResponseBody() throws Exception {
        Response response = new Response(0, "Hello, World!".getBytes());
        MockSender mockSender = new MockSender(response);
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", mockSender);
        FakeDeserializer deserializer = new FakeDeserializer(new Result());
        Client client = new Client(sender, deserializer);

        client.send(new Lookup("1"));

        assertEquals(response.getPayload(), deserializer.getPayload());
    }

    @Test
    public void testResultCorrectlyAssignedToCorrespondingLookup() throws Exception {
        Lookup lookup = new Lookup("1");
        Result expectedResult = new Result();

        MockSender mockSender = new MockSender(new Response(0, "{[]}".getBytes()));
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", mockSender);
        FakeDeserializer deserializer = new FakeDeserializer(expectedResult);
        Client client = new Client(sender, deserializer);

        client.send(lookup);

        assertArrayEquals(expectedResult.getCandidates(), lookup.getResult());
    }

    //endregion
}
