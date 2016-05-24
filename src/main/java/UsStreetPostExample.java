import com.smartystreets.api.Credentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.BatchFullException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class UsStreetPostExample {
    public static void main(String[] args) {
        Credentials credentials = new StaticCredentials("ac73c78f-b01a-3fbf-0cd8-b124db98fc28", "1G3uMAKDNSTtevgFM9U9");
        Client client = new ClientBuilder(credentials).build();
        Batch batch = new Batch();

        AddressLookup address0 = new AddressLookup();
        address0.setStreet("1600 amphitheatre parkway");
        address0.setCity("Mountain view");
        address0.setState("california");

        AddressLookup address1 = new AddressLookup("1 Rosedale, Baltimore, Maryland"); // Freeform addresses work too.
        address1.setMaxCandidates(10); // Allows up to ten possible matches to be returned (default is 1).

        AddressLookup address2 = new AddressLookup("123 Bogus Street, Pretend Lake, Oklahoma");

        AddressLookup address3 = new AddressLookup();
        address3.setStreet("1 Infinite Loop");
        address3.setZipCode("95014"); // You can just input the street and ZIP if you want.

        try {
            batch.add(address0);
            batch.add(address1);
            batch.add(address2);
            batch.add(address3);

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

        Vector<AddressLookup> lookups = batch.getAllLookups();

        for (int i=0; i < batch.size(); i++) {
            ArrayList<Candidate> candidates = lookups.get(i).getResult();

            if (candidates.isEmpty()) {
                System.out.println("Address " + i + " is invalid.\n");
                continue;
            }

            System.out.println("Address " + i + " is valid. (There is at least one candidate)");

            for (Candidate candidate : candidates){
                final Components components = candidate.getComponents();
                final Metadata metadata = candidate.getMetadata();

                System.out.println("\nCandidate " + candidate.getCandidateIndex() + ":");
                System.out.println("Delivery line 1: " + candidate.getDeliveryLine1());
                System.out.println("Last line:       " + candidate.getLastLine());
                System.out.println("ZIP Code:        " + components.getZipCode() + "-" + components.getPlus4Code());
                System.out.println("County:          " + metadata.getCountyName());
                System.out.println("Latitude:        " + metadata.getLatitude());
                System.out.println("Longitude:       " + metadata.getLongitude());
            }
            System.out.println();
        }
    }
}
