package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.international_street.*;

import java.io.IOException;

public class InternationalExample {
    public static void main(String[] args) throws IOException, SmartyException {
        // We recommend storing your secret keys in environment variables.
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));
        Client client = new ClientBuilder(credentials).buildInternationalStreetApiClient();

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
