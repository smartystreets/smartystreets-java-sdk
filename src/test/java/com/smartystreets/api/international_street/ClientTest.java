package com.smartystreets.api.international_street;

import com.smartystreets.api.Response;
import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.mocks.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testSendingFreeformLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        Lookup lookup = new Lookup( "freeform", "USA");

        client.send(lookup);

        assertEquals("http://localhost/?country=USA&freeform=freeform", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingSingleFullyPopulatedLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        String expectedUrl = "http://localhost/?country=0&geocode=true&language=native&freeform=1" +
                "&address1=2&address2=3&address3=4&address4=5&organization=6&locality=7&administrative_area=8&postal_code=9";
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        Lookup lookup = new Lookup();
        lookup.setCountry("0");
        lookup.setGeocode(true);
        lookup.setLanguage(LanguageMode.NATIVE);
        lookup.setFreeform("1");
        lookup.setAddress1("2");
        lookup.setAddress2("3");
        lookup.setAddress3("4");
        lookup.setAddress4("5");
        lookup.setOrganization("6");
        lookup.setLocality("7");
        lookup.setAdministrativeArea("8");
        lookup.setPostalCode("9");

        client.send(lookup);

        assertEquals(expectedUrl, capturingSender.getRequest().getUrl());
    }

    @Test
    public void testEmptyLookupRejected() throws Exception {
        exception.expect(SmartyException.class);
        MockCrashingSender sender = new MockCrashingSender();
        Client client = new Client(sender, null);

        client.send(new Lookup());
    }

    @Test
    public void testRejectsLookupsWithOnlyCountry() throws Exception {
        exception.expect(SmartyException.class);
        MockCrashingSender sender = new MockCrashingSender();
        Client client = new Client(sender, null);
        Lookup lookup = new Lookup();
        lookup.setCountry("0");

        client.send(lookup);
    }

    @Test
    public void testRejectsLookupsWithOnlyCountryAndAddress1() throws Exception {
        exception.expect(SmartyException.class);
        MockCrashingSender sender = new MockCrashingSender();
        Client client = new Client(sender, null);
        Lookup lookup = new Lookup();
        lookup.setCountry("0");
        lookup.setAddress1("1");

        client.send(lookup);
    }

    @Test
    public void testRejectsLookupsWithOnlyCountryAndAddress1AndLocality() throws Exception {
        exception.expect(SmartyException.class);
        MockCrashingSender sender = new MockCrashingSender();
        Client client = new Client(sender, null);
        Lookup lookup = new Lookup();
        lookup.setCountry("0");
        lookup.setAddress1("1");
        lookup.setLocality("2");

        client.send(lookup);
    }

    @Test
    public void testRejectsLookupsWithOnlyCountryAndAddress1AndAdministrativeArea() throws Exception {
        exception.expect(SmartyException.class);
        MockCrashingSender sender = new MockCrashingSender();
        Client client = new Client(sender, null);
        Lookup lookup = new Lookup();
        lookup.setCountry("0");
        lookup.setAddress1("1");
        lookup.setAdministrativeArea("2");

        client.send(lookup);
    }

    @Test
    public void testAcceptsLookupsWithEnoughInfo() throws Exception {
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        Lookup lookup = new Lookup();

        lookup.setCountry("0");
        lookup.setFreeform("1");
        client.send(lookup);

        lookup.setFreeform(null);
        lookup.setAddress1("1");
        lookup.setPostalCode("2");
        client.send(lookup);

        lookup.setPostalCode(null);
        lookup.setLocality("3");
        lookup.setAdministrativeArea("4");
        client.send(lookup);
    }

    @Test
    public void testDeserializeCalledWithResponseBody() throws Exception {
        Response response = new Response(0, "Hello, World!".getBytes());
        MockSender sender = new MockSender(response);
        FakeDeserializer deserializer = new FakeDeserializer(null);
        Client client = new Client(sender, deserializer);

        client.send(new Lookup("1","2"));

        assertEquals(response.getPayload(), deserializer.getPayload());
    }

    @Test
    public void testCandidatesCorrectlyAssignedToLookup() throws Exception {
        Candidate[] expectedCandidates = new Candidate[2];
        expectedCandidates[0] = new Candidate();
        expectedCandidates[1] = new Candidate();
        Lookup lookup = new Lookup("1", "2");

        MockSender sender = new MockSender(new Response(0, "[]".getBytes()));
        FakeDeserializer deserializer = new FakeDeserializer(expectedCandidates);
        Client client = new Client(sender, deserializer);

        client.send(lookup);

        assertEquals(expectedCandidates[0], lookup.getResult(0));
        assertEquals(expectedCandidates[1], lookup.getResult(1));
    }
}
