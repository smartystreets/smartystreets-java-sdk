package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_reverse_geo.*;

import java.io.IOException;

import java.util.ArrayList;

public class UsReverseGeoExample {
    public static void main(String[] args) {
// We recommend storing your secret keys in environment variables.
        // for Server-to-server requests, use this code:
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
        ArrayList<String> licenses = new ArrayList<>();
        licenses.add("us-reverse-geocoding-cloud");
        Client client = new ClientBuilder(credentials).withLicenses(licenses)
//                .withProxy(Proxy.Type.HTTP, "localhost", 8080) // Uncomment this line to try it with a proxy
                .buildUsReverseGeoClient();

        Lookup lookup = new Lookup(40.27644, -111.65747);

        try {
            client.send(lookup);
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        Result[] results = lookup.getResponse().getResults();

        System.out.printf("Results for input: (%f, %f)\n\n", lookup.getLatitude(), lookup.getLongitude());
        for (Result result : results) {
            Coordinate coordinate = result.getCoordinate();
            Address address = result.getAddress();
            System.out.println("Latitude: " + coordinate.getLatitude());
            System.out.println("Longitude: " + coordinate.getLongitude());
            System.out.println("Distance: " + result.getDistance().toString());
            System.out.println("Street: " + address.getStreet());
            System.out.println("City: " + address.getCity());
            System.out.println("State Abbreviation: " + address.getStateAbbreviation());
            System.out.println("ZIP Code: " + address.getZipCode());
            System.out.println("License: " + coordinate.getLicense());
            System.out.println();
        }
    }
}
