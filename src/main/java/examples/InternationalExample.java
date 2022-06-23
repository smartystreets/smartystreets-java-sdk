package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.international_street.*;

import java.io.IOException;

import java.util.ArrayList;

public class InternationalExample {
    public static void main(String[] args) throws IOException, SmartyException {
        // We recommend storing your secret keys in environment variables.
        // for server-to-server requests, use this code:
        // string authId = System.getenv("SMARTY_AUTH_ID");
        // string authToken = System.getenv("SMARTY_AUTH_TOKEN");
        // StaticCredentials credentials = new StaticCredentials(authId, authToken);

        // for client-side requests (browser/mobile), use this code:
        String key = System.getenv("SMARTY_AUTH_WEB");
        String hostname = System.getenv("SMARTY_AUTH_REFERER");
        SharedCredentials credentials = new SharedCredentials(key, hostname);

        //            The appropriate license values to be used for your subscriptions
        //            can be found on the Subscriptions page of the account dashboard.
        //            https://www.smartystreets.com/docs/cloud/licensing
        ArrayList<String> licenses = new ArrayList<String>();
        licenses.add("international-global-plus-cloud");
        Client client = new ClientBuilder(credentials).withLicenses(licenses)
                .buildInternationalStreetApiClient();

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/cloud/international-street-api#http-input-fields

        Lookup lookup = new Lookup("Rua Padre Antonio D'Angelo 121 Casa Verde, Sao Paulo", "Brazil");
        lookup.setInputId("ID-8675309"); // Optional ID from your system
        lookup.setOrganization("John Doe");
        lookup.setAddress1("Rua Padre Antonio D'Angelo 121");
        lookup.setAddress2("Casa Verde");
        lookup.setLocality("Sao Paulo");
        lookup.setAdministrativeArea("SP");
        lookup.setCountry("Brazil");
        lookup.setPostalCode("02516-050");
        lookup.setGeocode(true); // Must be expressly set to get latitude and longitude.

        Candidate[] candidates = client.send(lookup); // The candidates are also stored in the lookup's 'result' field.

        Candidate firstCandidate = candidates[0];
        System.out.println("Input ID: " + firstCandidate.getInputId());
        System.out.println("Address is " + firstCandidate.getAnalysis().getVerificationStatus());
        System.out.println("Address precision: " + firstCandidate.getAnalysis().getAddressPrecision() + "\n");

        System.out.println("First Line: " + firstCandidate.getAddress1());
        System.out.println("Second Line: " + firstCandidate.getAddress2());
        System.out.println("Third Line: " + firstCandidate.getAddress3());
        System.out.println("Fourth Line: " + firstCandidate.getAddress4());
        System.out.println("Latitude: " + firstCandidate.getMetadata().getLatitude());
        System.out.println("Longitude: " + firstCandidate.getMetadata().getLongitude());
    }
}
