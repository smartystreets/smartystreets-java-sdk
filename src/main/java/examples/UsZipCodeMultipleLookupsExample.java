package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.BatchFullException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_zipcode.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.Vector;

public class UsZipCodeMultipleLookupsExample {
    public static void main(String[] args) {
        // We recommend storing your secret keys in environment variables.
        // for server-to-server requests, use this code:
        // string authId = System.getenv("SMARTY_AUTH_ID");
        // string authToken = System.getenv("SMARTY_AUTH_TOKEN");
        // StaticCredentials credentials = new StaticCredentials(authId, authToken);

        // for client-side requests (browser/mobile), use this code:
        String key = System.getenv("SMARTY_AUTH_WEB");
        String hostname = System.getenv("SMARTY_AUTH_REFERER");
        SharedCredentials credentials = new SharedCredentials(key, hostname);

        Client client = new ClientBuilder(credentials).buildUsZipCodeApiClient();
        Batch batch = new Batch();

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/us-zipcode-api#input-fields

        Lookup lookup0 = new Lookup();
        lookup0.setInputId("dfc33cb6-829e-4fea-aa1b-b6d6580f0817"); // Optional ID from your system
        lookup0.setZipCode("42223");   // A Lookup may have a ZIP Code, city and state, or city, state, and ZIP Code

        Lookup lookup1 = new Lookup();
        lookup1.setCity("Phoenix");
        lookup1.setState("Arizona");

        Lookup lookup2 = new Lookup();
        lookup2.setInputId("01189998819991197253");
        lookup2.setCity("Provo");
        lookup2.setState("UT");
        lookup2.setZipCode("84604");

        Lookup lookup3 = new Lookup("cupertino", "CA", "95014"); // You can also set these with arguments

        try{
            batch.add(lookup0);
            batch.add(lookup1);
            batch.add(lookup2);
            batch.add(lookup3);

            client.send(batch);
        }
        catch(BatchFullException ex) {
            System.out.println("Oops! Batch was already full.");
        }
        catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        Vector<Lookup> lookups = batch.getAllLookups();

        for (int i=0; i < batch.size(); i++) {
            Result result = lookups.get(i).getResult();
            System.out.println("Lookup " + i + ":\n");

            if (result.getStatus() != null) {
                System.out.println("Status: " + result.getStatus());
                System.out.println("Reason: " + result.getReason());
                continue;
            }

            System.out.println("Input ID: " + result.getInputId());

            City[] cities = result.getCities();
            System.out.println(cities.length + " City and State match(es):");

            for (City city : cities) {
                System.out.println("City: " + city.getCity());
                System.out.println("State: " + city.getState());
                System.out.println("Mailable City: " + city.getMailableCity());
                System.out.println();
            }

            ZipCode[] zipCodes = result.getZipCodes();
            System.out.println(zipCodes.length + " ZIP Code match(es):");

            for (ZipCode zipCode : zipCodes) {
                System.out.println("ZIP Code: " + zipCode.getZipCode());
                System.out.println("County: " + zipCode.getCountyName());
                if (zipCode.getAlternateCounties() != null)
                    System.out.println("First alternate county: " + zipCode.getAlternateCounty(0).getCountyName());
                System.out.println("Latitude: " + zipCode.getLatitude());
                System.out.println("Longitude: " + zipCode.getLongitude());
                System.out.println();
            }
            System.out.println("***********************************");
        }
    }
}
