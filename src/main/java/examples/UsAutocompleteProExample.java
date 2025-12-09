package examples;

import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete_pro.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

import java.util.ArrayList;

public class UsAutocompleteProExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        // for server-to-server requests, use this code:
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        // for client-side requests (browser/mobile), use this code:
        // SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));

        Client client = new ClientBuilder(credentials).buildUsAutocompleteProApiClient();
        Lookup lookup = new Lookup("1042 W Center");
        lookup.setMaxResults(5);

        try {
            client.send(lookup);

            System.out.println("*** Result with no filter ***");
            System.out.println();
            printResult(lookup);

            // Documentation for input fields can be found at:
            // https://smartystreets.com/docs/cloud/us-autocomplete-pro-api#pro-http-request-url

            lookup.addCityFilter("Denver,Aurora,CO");
            lookup.addCityFilter("Orem,UT");
            lookup.addPreferState("CO");
            lookup.setPreferRatio(33);
            lookup.setSource("all");

            //uncomment the below line to add a custom parameter
            //lookup.addCustomParameter("source", "all");

            client.send(lookup); // The client will also return the suggestions directly

            System.out.println();
            System.out.println("*** Result with some filters ***");
            printResult(lookup);
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
