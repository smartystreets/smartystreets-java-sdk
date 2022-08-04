package examples;

import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete_pro.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

import java.util.ArrayList;

public class UsAutocompleteProExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));
//        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        //            The appropriate license values to be used for your subscriptions
        //            can be found on the Subscriptions page of the account dashboard.
        //            https://www.smartystreets.com/docs/cloud/licensing
        ArrayList<String> licenses = new ArrayList<>();
        licenses.add("us-autocomplete-pro-cloud");
        Client client = new ClientBuilder(credentials).withLicenses(licenses).buildUsAutocompleteProApiClient();
        Lookup lookup = new Lookup("1042 W Center");
        lookup.setMaxResults(5);

        try {
            client.send(lookup);

            System.out.println("*** Result with no filter ***");
            System.out.println();
            printResult(lookup);

            // Documentation for input fields can be found at:
            // https://smartystreets.com/docs/cloud/us-autocomplete-api#pro-http-request-url

            lookup.addStateFilter("MA");
            lookup.addCityFilter("Denver");
            lookup.addCityFilter("Orem");
            lookup.addPreferState("UT");
            lookup.addPreferState("CO");
            lookup.setSelected("1042 W Center St Apt A (24) Orem UT 84057");
            lookup.setPreferRatio(33);
            lookup.setSource("all");

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
