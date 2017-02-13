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

        Lookup lookup = new Lookup("USA", "1600 amphitheatre parkway, Mountain View California");
        lookup.setGeocode(true); // Must be expressly set to get latitude and longitude.

        Candidate[] candidates = client.send(lookup);

        Candidate firstCandidate = candidates[0];
        System.out.println("Address is " + firstCandidate.getAnalysis().getVerificationStatus() + "\n");
        System.out.println("First Line: " + firstCandidate.getAddress1());
        System.out.println("Second Line: " + firstCandidate.getAddress2());
        System.out.println("Latitude: " + firstCandidate.getMetadata().getLatitude());
        System.out.println("Longitude: " + firstCandidate.getMetadata().getLongitude());
    }
}
