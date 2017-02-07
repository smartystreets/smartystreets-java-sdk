package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class UsStreetSingleAddressExample {
    public static void main(String[] args) {
        // We recommend storing your secret keys in environment variables.
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));
        Client client = new ClientBuilder(credentials).buildUSStreetAPIClient();

        Lookup lookup = new Lookup();
        lookup.setStreet("1600 Amphitheatre Pkwy");
        lookup.setCity("Mountain View");
        lookup.setState("CA");

        try {
            client.send(lookup);
        }
        catch (SmartyException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        ArrayList<Candidate> results = lookup.getResult();

        if (results.isEmpty()) {
            System.out.println("No candidates. This means the address is not valid.");
            return;
        }

        Candidate firstCandidate = results.get(0);

        System.out.println("Address is valid. (There is at least one candidate)\n");
        System.out.println("ZIP Code: " + firstCandidate.getComponents().getZipCode());
        System.out.println("County: " + firstCandidate.getMetadata().getCountyName());
        System.out.println("Latitude: " + firstCandidate.getMetadata().getLatitude());
        System.out.println("Longitude: " + firstCandidate.getMetadata().getLongitude());
    }
}
