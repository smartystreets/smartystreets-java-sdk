package examples;

import com.smartystreets.api.BasicAuthCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.List;

public class UsStreetSingleAddressExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        // for server-to-server requests, use this code:
        BasicAuthCredentials credentials = new BasicAuthCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        // for client-side requests (browser/mobile), use this code:
        // SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));

        Client client = new ClientBuilder(credentials)
        //        .withProxy(Proxy.Type.HTTP, "localhost", 8080) // Uncomment this line to try it with a proxy
                .buildUsStreetApiClient();

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/us-street-api#input-fields

        Lookup lookup = new Lookup();
        lookup.setInputId("24601"); // Optional ID from your system
        lookup.setAddressee("John Doe");
        lookup.setStreet("1600 Amphitheatre Pkwy");
        lookup.setStreet2("closet under the stairs");
        lookup.setSecondary("APT 2");
        lookup.setUrbanization(""); // Only applies to Puerto Rico addresses
        lookup.setCity("Mountain View");
        lookup.setState("CA");
        lookup.setZipCode("94043");
        lookup.setCountySource(CountySource.GEOGRAPHIC);
        lookup.setMaxCandidates(3);
        lookup.setMatch(MatchType.INVALID); // "invalid" is the most permissive match,
                                            // this will always return at least one result even if the address is invalid.
                                            // Refer to the documentation for additional MatchStrategy options.

        try {
            client.send(lookup);
        }
        catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        List<Candidate> results = lookup.getResult();

        if (results.isEmpty()) {
            System.out.println("No candidates. This means the address is not valid.");
            return;
        }

        Candidate firstCandidate = results.get(0);

        System.out.println("There is at least one candidate.\n If the match parameter is set to STRICT, the address is valid.\n Otherwise, check the Analysis output fields to see if the address is valid.\n");
        System.out.println("Input ID: " + firstCandidate.getInputId());
        System.out.println("ZIP Code: " + firstCandidate.getComponents().getZipCode());
        System.out.println("County: " + firstCandidate.getMetadata().getCountyName());
        System.out.println("Latitude: " + firstCandidate.getMetadata().getLatitude());
        System.out.println("Longitude: " + firstCandidate.getMetadata().getLongitude());
    }
}
