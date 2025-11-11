package com.smartystreets.api.international_postal_code;

import com.smartystreets.api.Response;
import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.mocks.FakeDeserializer;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.MockCrashingSender;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {
    @Test
    public void testSendingSingleFullyPopulatedLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost/", capturingSender);
        FakeSerializer serializer = new FakeSerializer(null);
        Client client = new Client(sender, serializer);
        Lookup lookup = new Lookup();
        lookup.setInputId("foo");
        lookup.setCountry("BR");
        lookup.setLocality("Sao Paulo");
        lookup.setAdministrativeArea("SP");
        lookup.setPostalCode("02516");

        client.send(lookup);
        assertTrue(capturingSender.getRequest().getUrl().contains("input_id=foo"));
        assertTrue(capturingSender.getRequest().getUrl().contains("country=BR"));
        assertTrue(capturingSender.getRequest().getUrl().contains("locality=Sao+Paulo"));
        assertTrue(capturingSender.getRequest().getUrl().contains("administrative_area=SP"));
        assertTrue(capturingSender.getRequest().getUrl().contains("postal_code=02516"));
    }

    @Test
    public void testMissingCountryRejected() {
        MockCrashingSender sender = new MockCrashingSender();
        Client client = new Client(sender, null);
        Lookup lookup = new Lookup();
        lookup.setLocality("SomeLocality");
        assertThrows(SmartyException.class, () -> client.send(lookup));
    }

    @Test
    public void testAllInputFieldsMissingThrows() {
        MockCrashingSender sender = new MockCrashingSender();
        Client client = new Client(sender, null);
        Lookup lookup = new Lookup();
        assertThrows(SmartyException.class, () -> client.send(lookup));
    }

    @Test
    public void testAcceptsLookupsWithEnoughInfo() throws Exception {
        RequestCapturingSender sender = new RequestCapturingSender();
        FakeSerializer serializer = new FakeSerializer(new Candidate[]{});
        Client client = new Client(sender, serializer);
        Lookup lookup = new Lookup();

        lookup.setCountry("US");
        lookup.setLocality("a locality");
        client.send(lookup);
        lookup.setLocality(null);
        lookup.setAdministrativeArea("admin");
        client.send(lookup);
        lookup.setAdministrativeArea(null);
        lookup.setPostalCode("10001");
        client.send(lookup);
    }

    @Test
    public void testDeserializeCalledWithResponseBody() throws Exception {
        Response response = new Response(0, "World!".getBytes());
        MockSender sender = new MockSender(response);
        FakeDeserializer deserializer = new FakeDeserializer(new Candidate[]{});
        Client client = new Client(sender, deserializer);
        Lookup lookup = new Lookup();
        lookup.setCountry("US");
        lookup.setLocality("a locality");
        client.send(lookup);
        assertArrayEquals(new Candidate[]{}, lookup.getResult());
        assertArrayEquals(new Candidate[]{}, deserializer.deserialize(response.getPayload(), Candidate[].class));
    }
    
    @Test
    public void testCandidatesCorrectlyAssignedToLookup() throws Exception {
        Candidate[] expectedCandidates = new Candidate[2];
        expectedCandidates[0] = new Candidate();
        expectedCandidates[1] = new Candidate();
        Lookup lookup = new Lookup();
        lookup.setCountry("US");
        lookup.setLocality("Chicago");
        MockSender sender = new MockSender(new Response(0, "[]".getBytes()));
        FakeDeserializer deserializer = new FakeDeserializer(expectedCandidates);
        Client client = new Client(sender, deserializer);
        client.send(lookup);
        assertEquals(expectedCandidates[0], lookup.getResult(0));
        assertEquals(expectedCandidates[1], lookup.getResult(1));
    }
}
