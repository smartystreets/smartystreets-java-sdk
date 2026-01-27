package com.smartystreets.api;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class SmartyApiIT {

    static StaticCredentials credentials;

    @BeforeClass
    public static void setupCredentials() throws Exception {
        String authId = System.getenv("SMARTY_AUTH_ID");
        assertNotNull(authId);
        String authToken = System.getenv("SMARTY_AUTH_TOKEN");
        assertNotNull(authToken);
        credentials = new StaticCredentials(authId, authToken);
    }

    @Test
    public void testInternationalAutocomplete() throws Exception {
        com.smartystreets.api.international_autocomplete.Client client = new ClientBuilder(credentials).buildInternationalAutcompleteApiClient();

        com.smartystreets.api.international_autocomplete.Lookup lookup = new com.smartystreets.api.international_autocomplete.Lookup("Louis");
        lookup.setCountry("FRA");
        lookup.setLocality("Paris");
        lookup.setPostalCode("75008");

        client.send(lookup);

        com.smartystreets.api.international_autocomplete.Candidate[] candidates = lookup.getResult();
        assertNotNull(candidates);
        assertTrue(candidates.length > 0);
    }

    @Test
    public void testInternational() throws Exception {
        com.smartystreets.api.international_street.Client client = new ClientBuilder(credentials)
                .buildInternationalStreetApiClient();

        com.smartystreets.api.international_street.Lookup lookup = new com.smartystreets.api.international_street.Lookup("Rua Padre Antonio D'Angelo 121 Casa Verde, Sao Paulo", "Brazil");
        lookup.setInputId("ID-8675309");
        lookup.setOrganization("John Doe");
        lookup.setAddress1("Rua Padre Antonio D'Angelo 121");
        lookup.setAddress2("Casa Verde");
        lookup.setLocality("Sao Paulo");
        lookup.setAdministrativeArea("SP");
        lookup.setCountry("Brazil");
        lookup.setPostalCode("02516-050");
        lookup.setGeocode(true);

        com.smartystreets.api.international_street.Candidate[] candidates = client.send(lookup);

        assertNotNull(candidates);
        assertTrue(candidates.length > 0);
        assertTrue(candidates[0].getMetadata().getLatitude() > -23.50949 && candidates[0].getMetadata().getLatitude() < -23.50946);
        assertTrue(candidates[0].getMetadata().getLongitude() > -46.660731 && candidates[0].getMetadata().getLongitude() < -46.660729);
    }

    @Test
    public void testUSAutocompletePro() throws Exception {
        com.smartystreets.api.us_autocomplete_pro.Client client = new ClientBuilder(credentials).buildUsAutocompleteProApiClient();
        com.smartystreets.api.us_autocomplete_pro.Lookup lookup = new com.smartystreets.api.us_autocomplete_pro.Lookup("1042 W Center");
        lookup.setMaxResults(5);


        client.send(lookup);

        assertNotNull(lookup.getResult());
        assertEquals(5, lookup.getResult().length);

        lookup.addStateFilter("MA");
        lookup.addCityFilter("Denver");
        lookup.addCityFilter("Orem");
        lookup.addPreferState("UT");
        lookup.addPreferState("CO");
        lookup.setSelected("1042 W Center St Apt A (24) Orem UT 84057");
        lookup.setPreferRatio(33);
        lookup.setSource("all");

        client.send(lookup);

        assertNotNull(lookup.getResult());
        assertEquals(24, lookup.getResult().length);
    }

    @Test
    public void testUSExtract() throws Exception {
        com.smartystreets.api.us_extract.Client client = new ClientBuilder(credentials).buildUsExtractApiClient();
        String text = "Here is some text.\r\nMy address is 3785 Las Vegs Av." +
                "\r\nLos Vegas, Nevada." +
                "\r\nMeet me at 1 Rosedale Baltimore Maryland, not at 123 Phony Street, Boise Idaho.";

        com.smartystreets.api.us_extract.Lookup lookup = new com.smartystreets.api.us_extract.Lookup(text);
        lookup.isAggressive(true);
        lookup.addressesHaveLineBreaks();
        lookup.getAddressesPerLine();

        com.smartystreets.api.us_extract.Result result = client.send(lookup);

        com.smartystreets.api.us_extract.Metadata metadata = result.getMetadata();
        assertEquals(3, metadata.getAddressCount());
        assertEquals(2, metadata.getVerifiedCount());
    }

    @Test
    public void testReverseGeo() throws Exception {
        com.smartystreets.api.us_reverse_geo.Client client = new ClientBuilder(credentials)
                .buildUsReverseGeoClient();

        com.smartystreets.api.us_reverse_geo.Lookup lookup = new com.smartystreets.api.us_reverse_geo.Lookup(40.27644, -111.65747);

        client.send(lookup);

        com.smartystreets.api.us_reverse_geo.Result[] results = lookup.getResponse().getResults();

        assertNotNull(results);
        assertEquals(10, results.length);
    }

    @Test
    public void testUsStreetMultiple() throws Exception {
        com.smartystreets.api.us_street.Client client = new ClientBuilder(credentials)
                .buildUsStreetApiClient();
        com.smartystreets.api.us_street.Batch batch = new com.smartystreets.api.us_street.Batch();

        com.smartystreets.api.us_street.Lookup address0 = new com.smartystreets.api.us_street.Lookup();
        address0.setInputId("24601"); // Optional ID from you system
        address0.setAddressee("John Doe");
        address0.setStreet("1600 amphitheatre parkway");
        address0.setStreet2("closet under the stairs");
        address0.setSecondary("APT 2");
        address0.setUrbanization(""); // Only applies to Puerto Rico addresses
        address0.setCity("Mountain view");
        address0.setState("california");
        address0.setZipCode("94043");
        address0.setMaxCandidates(3);
        address0.setMatch(com.smartystreets.api.us_street.MatchType.INVALID);

        com.smartystreets.api.us_street.Lookup address1 = new com.smartystreets.api.us_street.Lookup("1 Rosedale, Baltimore, Maryland");
        address1.setMaxCandidates(5);

        com.smartystreets.api.us_street.Lookup address2 = new com.smartystreets.api.us_street.Lookup();
        address2.setStreet("123 Bogus Street");
        address2.setLastline("Pretend Lake, Oklahoma");
        address2.setMaxCandidates(1);

        com.smartystreets.api.us_street.Lookup address3 = new com.smartystreets.api.us_street.Lookup();
        address3.setInputId("8675309");
        address3.setStreet("1 Infinite Loop");
        address3.setZipCode("95014");

        batch.add(address0);
        batch.add(address1);
        batch.add(address2);
        batch.add(address3);

        client.send(batch);

        List<com.smartystreets.api.us_street.Lookup> lookups = batch.getAllLookups();

        assertEquals(4, batch.size());
        assertEquals(2, lookups.get(1).getResult().size()); // two results
        assertTrue(lookups.get(2).getResult().isEmpty()); // Bad address
    }

    @Test
    public void testUSStreetSingle() throws Exception {
        com.smartystreets.api.us_street.Client client = new ClientBuilder(credentials)
                .buildUsStreetApiClient();

        com.smartystreets.api.us_street.Lookup lookup = new com.smartystreets.api.us_street.Lookup();
        lookup.setInputId("24601"); // Optional ID from your system
        lookup.setAddressee("John Doe");
        lookup.setStreet("1600 Amphitheatre Pkwy");
        lookup.setStreet2("closet under the stairs");
        lookup.setSecondary("APT 2");
        lookup.setUrbanization(""); // Only applies to Puerto Rico addresses
        lookup.setCity("Mountain View");
        lookup.setState("CA");
        lookup.setZipCode("94043");
        lookup.setMaxCandidates(3);
        lookup.setMatch(com.smartystreets.api.us_street.MatchType.INVALID);

        client.send(lookup);


        List<com.smartystreets.api.us_street.Candidate> results = lookup.getResult();

        assertFalse(results.isEmpty());

        com.smartystreets.api.us_street.Candidate firstCandidate = results.get(0);
        assertEquals("94043", firstCandidate.getComponents().getZipCode());
    }

    @Test
    public void testUsZipCodeSingleLookup() throws Exception {

        com.smartystreets.api.us_zipcode.Client client = new ClientBuilder(credentials).buildUsZipCodeApiClient();

        com.smartystreets.api.us_zipcode.Lookup lookup = new com.smartystreets.api.us_zipcode.Lookup();
        lookup.setInputId("dfc33cb6-829e-4fea-aa1b-b6d6580f0817"); // Optional ID from your system
        lookup.setCity("Mountain View");
        lookup.setState("California");
        lookup.setZipCode("94043");

        client.send(lookup);


        com.smartystreets.api.us_zipcode.Result result = lookup.getResult();
        com.smartystreets.api.us_zipcode.ZipCode[] zipCodes = result.getZipCodes();
        com.smartystreets.api.us_zipcode.City[] cities = result.getCities();

        assertEquals(result.getInputId(), lookup.getInputId());
        assertNotNull(cities);
        assertEquals(1, cities.length);
        assertNotNull(zipCodes);
        assertEquals(1, zipCodes.length);
        assertNull(result.getStatus());
        assertNull(result.getReason());
    }

    @Test
    public void testZipCodeMultipleLookup() throws Exception {
        com.smartystreets.api.us_zipcode.Client client = new ClientBuilder(credentials).buildUsZipCodeApiClient();
        com.smartystreets.api.us_zipcode.Batch batch = new com.smartystreets.api.us_zipcode.Batch();

        com.smartystreets.api.us_zipcode.Lookup lookup0 = new com.smartystreets.api.us_zipcode.Lookup();
        lookup0.setInputId("dfc33cb6-829e-4fea-aa1b-b6d6580f0817"); // Optional ID from your system
        lookup0.setZipCode("42223");

        com.smartystreets.api.us_zipcode.Lookup lookup1 = new com.smartystreets.api.us_zipcode.Lookup();
        lookup1.setCity("Phoenix");
        lookup1.setState("Arizona");

        com.smartystreets.api.us_zipcode.Lookup lookup2 = new com.smartystreets.api.us_zipcode.Lookup();
        lookup2.setInputId("01189998819991197253");
        lookup2.setCity("Provo");
        lookup2.setState("UT");
        lookup2.setZipCode("84604");

        com.smartystreets.api.us_zipcode.Lookup lookup3 = new com.smartystreets.api.us_zipcode.Lookup("cupertino", "CA", "95014");

        batch.add(lookup0);
        batch.add(lookup1);
        batch.add(lookup2);
        batch.add(lookup3);

        client.send(batch);

        Vector<com.smartystreets.api.us_zipcode.Lookup> lookups = batch.getAllLookups();

        assertEquals(4, lookups.size());

        com.smartystreets.api.us_zipcode.Result result = lookups.get(0).getResult();
        assertNull(result.getStatus());
        assertNull(result.getReason());
        assertNotNull(result.getCities());
        assertEquals(1, result.getCities().length);
    }
}
