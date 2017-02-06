package examples;

import com.smartystreets.api.exceptions.BatchFullException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_zipcode.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.Vector;

public class UsZipCodeMultipleLookupsExample {
    public static void main(String[] args) {
        Client client = new ClientBuilder("YOUR AUTH-ID HERE", "YOUR AUTH-TOKEN HERE").buildUSZIPCodeAPIClient();
        Batch batch = new Batch();

        Lookup lookup0 = new Lookup();
        lookup0.setZipCode("12345");   // A Lookup may have a ZIP Code, city and state, or city, state, and ZIP Code

        Lookup lookup1 = new Lookup();
        lookup1.setCity("Phoenix");
        lookup1.setState("Arizona");

        Lookup lookup2 = new Lookup("cupertino", "CA", "95014"); // You can also set these with arguments

        try{
            batch.add(lookup0);
            batch.add(lookup1);
            batch.add(lookup2);

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
            Result result = lookups.get(i).getResult();
            System.out.println("Lookup " + i + ":\n");

            if (result.getStatus() != null) {
                System.out.println("Status: " + result.getStatus());
                System.out.println("Reason: " + result.getReason());
                continue;
            }

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
                System.out.println("Latitude: " + zipCode.getLatitude());
                System.out.println("Longitude: " + zipCode.getLongitude());
                System.out.println();
            }
            System.out.println("***********************************");
        }
    }
}
