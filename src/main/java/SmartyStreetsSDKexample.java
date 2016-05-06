import com.smartystreets.api.HttpSender;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import com.smartystreets.api.Credentials;
import com.smartystreets.api.RetrySender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SmartyStreetsSDKexample {

    public static void main(String args[]) {
        final String THE_RIGHT_ZIPCODE = "84606";

//        Credentials credentials = getSSCredentialsFromFile("/ss-credentials.txt");
        Credentials credentials = new StaticCredentials("auth-id", "auth-token");
        ArrayList<AddressLookup> addressesToVerify = new ArrayList<>();

        AddressLookup validAddress = new AddressLookup("1600 Amphitheatre Parkway, Mountain View, California");
        validAddress.setInputId("address 1");
        AddressLookup almostValidAddress = new AddressLookup("160 Amphitheatre Parkway, Mountain View, California");
        AddressLookup invalidAddress = new AddressLookup("150 Park Road, Gotham City, New York");
        AddressLookup invalidAddress2 = new AddressLookup();
        invalidAddress2.setStreet("150 Park Road");
        invalidAddress2.setCity("Gotham City");
        invalidAddress2.setState("New York");

        addressesToVerify.add(validAddress);
        addressesToVerify.add(almostValidAddress);
        addressesToVerify.add(invalidAddress);
        addressesToVerify.add(invalidAddress2);

//        ArrayList<AddressLookup> addressesToVerify = getAddressesFromCSV("/all-them-addresses.csv");
        Client client = new ClientBuilder(credentials)
                .withMaxTimeout(10000)
                .retryAtMost(3)
                .build();

        try {
            AddressLookup firstLookup = new AddressLookup("1600 amphitheatre parkway, Mountain View, California"); // Different signature for freeform
            client.send(firstLookup);
            ArrayList<Candidate> result = firstLookup.getResult();

            if (!result.isEmpty())
                assert result.get(0).getZIPCode() == THE_RIGHT_ZIPCODE;

            Batch batch = new Batch();

            batch.add(addressesToVerify.get(0)); // Just one address

            client.send(batch);
            batch.setIncludeInvalid(true);

            Candidate outputAddress = batch.get("address 1").getResult(0); // Get by input_id, or input_index?

            if (outputAddress.isValid())
                say(outputAddress.getDeliveryLine1() + " is valid");
            else
                say(outputAddress.getDeliveryLine1() + " is not valid");

            batch.clear(); // Clears input and output, but not settings
            batch.add(addressesToVerify.get(0));
            batch.add(addressesToVerify.get(1));
            assert batch.size() == 2;

            client.send(batch);

            Iterator<AddressLookup> iterator = batch.iterator(); // Java loves iterators. This one is for the list of addresses in the response

            while (iterator.hasNext()) {
                AddressLookup current = iterator.next();
                ArrayList<Candidate> candidates = current.getResult();

                if (candidates.isEmpty())
                    continue;

                Candidate cleanAddress = candidates.get(0);

                if (cleanAddress.isValid() && cleanAddress.isActive() && cleanAddress.getState().equals("CA"))
                    say(cleanAddress.getDeliveryLine1() + " is a valid California address");
                else
                    say(cleanAddress.getDeliveryLine1() + " is not a valid California address");
            }

            AddressLookup secondLookup = new AddressLookup("ServiceUnavailable 555 E 555 N Provo, Utah");
            client.send(secondLookup);
        }
        catch(SmartyException ex){ // These aren't the only Exceptions, just a couple.
            say(ex.getMessage());
        }
        catch (IOException ex) {
            say(ex.getMessage());
        }
        finally {
            say("I'm done");
        }

//        catch(Exception ex){
//            say(ex.getMessage());
//        }
    }

    private static void say(String message) {
        System.out.println(message);
    }
}

/*
Some thoughts:

1. We can use polymorphism to make two different types of SSAddress---US and International. This way they can each have
some unique fields, but can be stored in the same collection, etc.
    **** Jonathan would rather use composition

2. I think it would be nice for the user to be able to access values in the response without having to dig deep.
i.e., they should call `outputAddress.time_zone` and not `outputAddress.metadata.time_zone`.

3. I am not attached to the name convention I threw together. I used "time_zone" in the example in order to be consistent
with the raw response. We might want to use "timeZone" in order to conform more tightly to the Java convention.
    **** We're going with the Java convention.

4. Do we want to require them to know whether an address is US or international when they create an SSAddress instance,
or do we want to also provide a constructor that can determine this itself?
    **** They will use a separate namespace for each API, so they will have to sort their addresses themselves.
    * We could provide a function that checks for US to make this easier.

5. Do we allow them to add more addresses in a request than can be sent at once to the street API? It would be convenient
for them if we allow it, and the SDK batches it for them.
    **** Sounds like we want to only allow them to fill a batch to its capacity, and tell them how many addresses were loaded
 */