package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.international_autocomplete.Client;
import com.smartystreets.api.international_autocomplete.Lookup;
import com.smartystreets.api.international_autocomplete.Candidate;

import java.io.IOException;

public class InternationalAutocompleteExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        // for server-to-server requests, use this code:
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        // for client-side requests (browser/mobile), use this code:
        //SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));

        Client client = new ClientBuilder(credentials).buildInternationalAutcompleteApiClient();
        Lookup lookup = new Lookup("Louis");

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/cloud/international-address-autocomplete-api#pro-http-request-url


        lookup.setCountry("FRA");
        lookup.setLocality("Paris");

        try {
            client.send(lookup);

            System.out.println("*** Result ***");
            System.out.println();
            printResult(lookup);
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void printResult(Lookup lookup) {
        for (Candidate candidate : lookup.getResult()) {
            if(candidate.getAddressText() != null) {
                System.out.println("Entries: " + candidate.getEntries());
                System.out.println("Address Text: " + candidate.getAddressText());
                System.out.println("Address ID: " + candidate.getAddressID() + "\n");
            } else {
                System.out.println("Street: " + candidate.getStreet());
                System.out.println("Locality: " + candidate.getLocality());
                System.out.println("Administrative Area: " + candidate.getAdministrativeArea());
                System.out.println("Postal Code: " + candidate.getPostalCode());
                System.out.println("Country: " + candidate.getCountryISO3());
            }
        }
    }
}
