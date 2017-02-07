package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_zipcode.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

public class UsZipCodeSingleLookupExample {
    public static void main(String[] args) {
        // We recommend storing your secret keys in environment variables.
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));
        Client client = new ClientBuilder(credentials).buildUSZIPCodeAPIClient();

        Lookup lookup = new Lookup();
        lookup.setCity("Mountain View");
        lookup.setState("California");

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

        Result result = lookup.getResult();
        ZipCode[] zipCodes = result.getZipCodes();
        City[] cities = result.getCities();

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
