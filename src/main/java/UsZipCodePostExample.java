import com.google.api.client.json.Json;
import com.smartystreets.api.Credentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.BatchFullException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_zipcode.*;

import java.io.IOException;
import java.util.Vector;

public class UsZipCodePostExample {
    public static void main(String[] args) {
        Credentials credentials = new StaticCredentials("1c457ace-567f-c4bf-a925-abd57cfdb0b9", "IBrfg7Yvs3xs4qN4SILY");
        Client client = new ClientBuilder(credentials).build();
        Batch batch = new Batch();

        Lookup lookup0 = new Lookup();
        lookup0.setZipCode("12345");   // A Lookup may have a ZIP Code, city and state, or city, state, and ZIP Code

        Lookup lookup1 = new Lookup();
        lookup1.setCity("Houston");
        lookup1.setState("TX");

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

            CityState[] cityStates = result.getCityStates();
            System.out.println(cityStates.length + " City and State match(es):");

            for (CityState cityState : cityStates) {
                System.out.println("City: " + cityState.getCity());
                System.out.println("State: " + cityState.getState());
                System.out.println("Mailable City: " + cityState.getMailableCity());
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
