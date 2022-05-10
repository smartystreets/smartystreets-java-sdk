package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.BatchFullException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UsStreetMultipleAddressesExample {
    public static void main(String[] args) {
        // We recommend storing your secret keys in environment variables.
        // for Server-toserver requests, use this code:
         String authId = System.getenv("SMARTY_AUTH_ID");
         String authToken = System.getenv("SMARTY_AUTH_TOKEN");
         StaticCredentials credentials = new StaticCredentials(authId, authToken);

        // for client-side requests (browser/mobile), use this code:
//        String key = System.getenv("SMARTY_AUTH_WEB");
//        String hostname = System.getenv("SMARTY_AUTH_REFERER");
//        SharedCredentials credentials = new SharedCredentials(key, hostname);

        //            The appropriate license values to be used for your subscriptions
        //            can be found on the Subscriptions page of the account dashboard.
        //            https://www.smartystreets.com/docs/cloud/licensing
        ArrayList<String> licenses = new ArrayList<String>();
        licenses.add("us-core-cloud");
        Client client = new ClientBuilder(credentials).withLicenses(licenses)
                .buildUsStreetApiClient();
        Batch batch = new Batch();

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/us-street-api#input-fields

        Lookup address0 = new Lookup();
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
        address0.setMatch(MatchType.INVALID); // "invalid" is the most permissive match,
                                              // this will always return at least one result even if the address is invalid.
                                              // Refer to the documentation for additional MatchStrategy options.

        Lookup address1 = new Lookup("1 Rosedale, Baltimore, Maryland"); // Freeform addresses work too.
        address1.setMaxCandidates(5); // Allows up to ten possible matches to be returned (default is 1).

        Lookup address2 = new Lookup();
        address2.setStreet("123 Bogus Street");
        address2.setLastline("Pretend Lake, Oklahoma");
        address2.setMaxCandidates(1);

        Lookup address3 = new Lookup();
        address3.setInputId("8675309");
        address3.setStreet("1 Infinite Loop");
        address3.setZipCode("95014"); // You can just input the street and ZIP if you want.

        try {
            batch.add(address0);
            batch.add(address1);
            batch.add(address2);
            batch.add(address3);

            client.send(batch);
        }
        catch(BatchFullException ex) {
            System.out.println("Oops! Batch was already full.");
        }
        catch (SmartyException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        Vector<Lookup> lookups = batch.getAllLookups();

        for (int i=0; i < batch.size(); i++) {
            List<Candidate> candidates = lookups.get(i).getResult();

            if (candidates.isEmpty()) {
                System.out.println("Address " + i + " is invalid.\n");
                continue;
            }

            System.out.println("Address " + i + " has at least one candidate.\n If the match parameter is set to STRICT, the address is valid.\n Otherwise, check the Analysis output fields to see if the address is valid.");

            for (Candidate candidate : candidates){
                final Components components = candidate.getComponents();
                final Metadata metadata = candidate.getMetadata();

                System.out.println("\nCandidate " + candidate.getCandidateIndex() + ":");
                System.out.println("Input ID: " + candidate.getInputId());
                System.out.println("Delivery line 1: " + candidate.getDeliveryLine1());
                System.out.println("Last line:       " + candidate.getLastLine());
                System.out.println("ZIP Code:        " + components.getZipCode() + "-" + components.getPlus4Code());
                System.out.println("County:          " + metadata.getCountyName());
                System.out.println("Latitude:        " + metadata.getLatitude());
                System.out.println("Longitude:       " + metadata.getLongitude());
            }
            System.out.println();
        }
    }
}
