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
        // We recommend storing your authentication credentials in environment variables.
        // for server-to-server requests, use this code:
        //StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        // for client-side requests (browser/mobile), use this code:
        SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));

        Client client = new ClientBuilder(credentials)
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
            System.out.println("Smartykey: " + address.getSmartykey());
            System.out.println();
        }
    }
}
