import com.smartystreets.api.Credentials;
import com.smartystreets.api.Response;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;

import java.io.IOException;
import java.util.ArrayList;

public class UsStreetGetExample {
    public static void main(String[] args) {
        Credentials credentials = new StaticCredentials("YOUR AUTH-ID HERE", "YOUR AUTH-TOKEN HERE");
        Client client = new ClientBuilder(credentials).build();

        AddressLookup lookup = new AddressLookup();
        lookup.setStreet("1600 Amphitheatre Pkwy");
        lookup.setCity("Mountain View");
        lookup.setState("CA");

        try {
            client.send(lookup);
        }
        catch (SmartyException ex) {
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            // Handle this
        }

        ArrayList<Candidate> results = lookup.getResult();
        Candidate firstCandidate = results.get(0);

        System.out.println("ZIP Code: " + firstCandidate.getComponents().getZipCode());
        System.out.println("County Name: " + firstCandidate.getMetadata().getCountyName());
        System.out.println("Latitude: " + firstCandidate.getMetadata().getLatitude());
        System.out.println("Longitude: " + firstCandidate.getMetadata().getLongitude());



    }
}
