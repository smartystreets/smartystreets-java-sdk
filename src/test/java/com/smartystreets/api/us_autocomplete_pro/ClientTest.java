package com.smartystreets.api.us_autocomplete_pro;

import com.smartystreets.api.GeolocateType;
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

        assertEquals("http://localhost/?search=1&prefer_geolocation=city", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingSingleFullyPopulatedLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new Result());
        Client client = new Client(sender, serializer);
        String expectedURL = "http://localhost/?search=1&max_results=2&include_only_cities=3&include_only_states=4&prefer_ratio=60&prefer_geolocation=state";
        Lookup lookup = new Lookup();
        lookup.setSearch("1");
        lookup.setMaxSuggestions(2);
        lookup.addCityFilter("3");
        lookup.addStateFilter("4");
        lookup.setPreferRatio(60);
        lookup.setGeolocateType(GeolocateType.STATE);

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

        assertArrayEquals(expectedResult.getSuggestions(), lookup.getResult());
    }

    //endregion
}
