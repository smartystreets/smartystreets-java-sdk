package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_reverse_geo.Client;
import com.smartystreets.api.us_reverse_geo.Lookup;
import com.smartystreets.api.us_reverse_geo.Result;

import java.io.IOException;

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

        Client client = new ClientBuilder(credentials)
//                .withProxy(Proxy.Type.HTTP, "localhost", 8080) // Uncomment this line to try it with a proxy
                .buildUsReverseGeoClient();

        Lookup lookup = new Lookup(40.27644, -111.65747);

        try {
            client.send(lookup);
        } catch (SmartyException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Result[] results = lookup.getResponse().getResults();

        System.out.printf("Results for input: (%f, %f)\n\n", lookup.getLatitude(), lookup.getLongitude());
        for (Result result : results) {
            System.out.println("Latitude: " + result.getLatitude().toString());
            System.out.println("Longitude: " + result.getLongitude().toString());
            System.out.println("Distance: " + result.getDistance().toString());
            System.out.println("Street: " + result.getStreet());
            System.out.println("City: " + result.getCity());
            System.out.println("State Abbreviation: " + result.getStateAbbreviation());
            System.out.println("ZIP Code: " + result.getZipCode());
            System.out.println();
        }
    }
}
