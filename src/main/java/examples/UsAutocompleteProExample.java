package examples;

import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete_pro.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

public class UsAutocompleteProExample {
    public static void main(String[] args) throws IOException, SmartyException {
        // We recommend storing your authentication credentials in environment variables.
        SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));
        Client client = new ClientBuilder(credentials).buildUsAutocompleteProApiClient();
        Lookup lookup = new Lookup("1042 W Center");

        client.send(lookup);

        System.out.println("*** Result with no filter ***");
        System.out.println();
        printResult(lookup);

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/cloud/us-autocomplete-api#pro-http-request-url

        lookup.addStateFilter("MA");
        lookup.addCityFilter("Denver,Aurora,CO");
        lookup.addCityFilter("Orem,UT");
        lookup.addPreferState("UT");
        lookup.setSelected("1042 W Center St Apt A (24) Orem UT 84057");
        lookup.setMaxSuggestions(5);
        lookup.setPreferRatio(33);

        client.send(lookup); // The client will also return the suggestions directly

        System.out.println();
        System.out.println("*** Result with some filters ***");
        printResult(lookup);
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
