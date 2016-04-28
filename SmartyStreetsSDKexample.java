/**
 * Created by Neo on 4/7/16.
 */

import com.smartystreets.api.us_street.*;
import com.smartystreets.api.Credentials;
import java.util.ArrayList;
import java.util.Iterator;

public class SmartyStreetsSDKexample {
    public static void main(String args[]) {
        Credentials credentials = getSSCredentialsFromFile("/ss-credentials.txt");
        ArrayList<AddressLookup> addressesToVerify = getAddressesFromCSV("/all-them-addresses.csv");
        Client client = new Client(credentials); // Alternate constructor accepts Id and token as separate parameters

        try {
            AddressLookup firstLookup = new AddressLookup("1600 amphitheatre parkway, Mountain View, California"); // Different signature for freeform
            client.send(firstLookup);
            ArrayList<Candidate> result = firstLookup.getResult();

            if (!result.isEmpty())
                assert result.get(0).getZIPCode() == THE_RIGHT_ZIPCODE;

            Batch batch = new Batch();

            batch.setIncludeInvalid(true);
            boolean success = batch.add(addressesToVerify.get(0)); // Just one address


            if (!success)
                throw new Exception("Something awful has happened.");

            client.send(batch);

            Candidate outputAddress = batch.get("address 1").getResult(0); // Get by input_id, or input_index?

            if (outputAddress.isValid())
                shoutForJoy();
            else cry();

            batch.clear(); // Clears input and output, but not settings
            int numAdded = batch.add(addressesToVerify); // Multiple addresses
            assert numAdded > 0;

            client.send(batch);

            Iterator<AddressLookup> iterator = batch.iterator(); // Java loves iterators. This one is for the list of addresses in the response

            while (iterator.hasNext()) {
                AddressLookup current = iterator.next();
                ArrayList<Candidate> candidates = current.getResult();


                if (candidates.isEmpty())
                    continue;

                Candidate cleanAddress = candidates.get(0);

                if (cleanAddress.isValid() && cleanAddress.isActive() && !cleanAddress.getState().equals("HI")) {
                    singHappySong();
                } else {
                    badAddresses.add(cleanAddress);
                }
            }
        }
        catch(BadCredentialsException ex){
            // stuff
        }
        catch(NoActiveSubscriptionException ex){ // These aren't the only Exceptions, just a couple.
            // different stuff
        }
        catch(Exception ex){
            // other stuff
        }
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