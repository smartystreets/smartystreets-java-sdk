package examples;

import com.smartystreets.api.BasicAuthCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

// This example is for US Autocomplete (V2). It has the same name as a previous product
// which has been deprecated since 2022, which we refer to as US Autocomplete Basic.
// If you are still using US Autocomplete Basic, this SDK will not work.
public class UsAutocompleteExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        // for client-side requests (browser/mobile), use this code:
        // SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));
        BasicAuthCredentials credentials = new BasicAuthCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        try (Client client = new ClientBuilder(credentials).buildUsAutocompleteApiClient()) {
            Lookup lookup = new Lookup("1042 W Center");
            lookup.setMaxResults(5);

            client.send(lookup);

            System.out.println("*** Result with no filter ***");
            System.out.println();
            printResult(lookup);

            // Documentation for input fields can be found at:
            // https://www.smarty.com/docs/apis/us-autocomplete-v2/reference#http-request-input-fields

            lookup.addCityFilter("Denver,Aurora,CO");
            lookup.addCityFilter("Orem,UT");
            lookup.addPreferState("CO");
            lookup.setPreferRatio(33);
            lookup.setSource("all");

            client.send(lookup); // The client will also return the suggestions directly

            System.out.println();
            System.out.println("*** Result with some filters ***");
            printResult(lookup);

            // Expand the secondaries of a result that has an entry_id by passing it back as the selected address.
            String entryId = null;
            for (Suggestion suggestion : lookup.getResult()) {
                if (suggestion.getEntryId() != null && suggestion.getEntryId().length() > 0)
                    entryId = suggestion.getEntryId();
            }

            if (entryId != null) {
                lookup.setSelected(entryId);
                client.send(lookup);

                System.out.println();
                System.out.println("*** Secondaries ***");
                printResult(lookup);
            }
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void printResult(Lookup lookup) {
        for (Suggestion suggestion : lookup.getResult()) {
            System.out.print(suggestion.getStreetLine());
            System.out.print(" ");
            System.out.print(suggestion.getSecondary());
            System.out.print(" ");
            System.out.print(suggestion.getCity());
            System.out.print(", ");
            System.out.print(suggestion.getState());
            System.out.print(", ");
            System.out.println(suggestion.getZipcode());
        }
    }
}
