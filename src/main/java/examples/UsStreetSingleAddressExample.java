package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class UsStreetSingleAddressExample {
    public static void main(String[] args) {
// We recommend storing your secret keys in environment variables.
        // for Server-toserver requests, use this code:
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
        ArrayList<String> licenses = new ArrayList<>();
        licenses.add("us-core-cloud");
        Client client = new ClientBuilder(credentials).withLicenses(licenses)
//                .withProxy(Proxy.Type.HTTP, "localhost", 8080) // Uncomment this line to try it with a proxy
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
