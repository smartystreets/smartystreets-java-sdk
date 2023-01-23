package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_zipcode.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

public class UsZipCodeSingleLookupExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        // for server-to-server requests, use this code:
        //StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        // for client-side requests (browser/mobile), use this code:
        SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));

        Client client = new ClientBuilder(credentials).buildUsZipCodeApiClient();

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/us-zipcode-api#input-fields

        Lookup lookup = new Lookup();
        lookup.setInputId("dfc33cb6-829e-4fea-aa1b-b6d6580f0817"); // Optional ID from your system
        lookup.setCity("Mountain View");
        lookup.setState("California");
        lookup.setZipCode("94043");

        try {
            client.send(lookup);
        }
        catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        Result result = lookup.getResult();
        ZipCode[] zipCodes = result.getZipCodes();
        City[] cities = result.getCities();

        System.out.println("Input ID: " + result.getInputId());

        for (City city : cities) {
            System.out.println("\nCity: " + city.getCity());
            System.out.println("State: " + city.getState());
            System.out.println("Mailable City: " + city.getMailableCity());
        }

        for (ZipCode zip : zipCodes) {
            System.out.println("\nZIP Code: " + zip.getZipCode());
            System.out.println("Latitude: " + zip.getLatitude());
            System.out.println("Longitude: " + zip.getLongitude());
        }
    }
}
