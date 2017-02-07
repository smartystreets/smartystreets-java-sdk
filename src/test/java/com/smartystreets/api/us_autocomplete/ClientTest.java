package com.smartystreets.api.us_autocomplete;


import com.smartystreets.api.GeolocateType;
import com.smartystreets.api.Response;
import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.mocks.FakeDeserializer;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    //region [ Single Lookup ]

    @Test
    public void testSendingSinglePrefixOnlyLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);

        client.send(new Lookup("1"));

        assertEquals("http://localhost/?prefix=1&suggestions=10&geolocate=true&geolocate_precision=city", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingSingleFullyPopulatedLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        String expectedURL = "http://localhost/?prefix=1&suggestions=2&city_filter=3&state_filter=4&prefer=5&geolocate=true&geolocate_precision=state";
        Lookup lookup = new Lookup();
        lookup.setPrefix("1");
        lookup.setMaxSuggestions(2);
        lookup.addCityFilter("3");
        lookup.addStateFilter("4");
        lookup.addPrefer("5");
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
        FakeDeserializer deserializer = new FakeDeserializer(null);
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

        assertEquals(expectedResult, lookup.getResult());
    }

    //endregion
}
