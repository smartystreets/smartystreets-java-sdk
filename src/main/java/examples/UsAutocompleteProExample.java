package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete_pro.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

public class UsAutocompleteProExample {
    public static void main(String[] args) throws IOException, SmartyException {
        // We recommend storing your secret keys in environment variables.
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));
        Client client = new ClientBuilder(credentials).buildUsAutocompleteProApiClient();
        Lookup lookup = new Lookup("1 King St Apt");

        client.send(lookup);

        System.out.println("*** Result with no filter ***");
        System.out.println();
        for (Suggestion suggestion : lookup.getResult()) {
            System.out.println(suggestion.getStreetLine());
            System.out.println(suggestion.getSecondary());
            System.out.println(suggestion.getCity());
            System.out.println(", ");
            System.out.println(suggestion.getState());
            System.out.println(", ");
            System.out.println(suggestion.getZipcode());
        }

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/cloud/us-autocomplete-api#http-request-input-fields

        lookup.addStateFilter("MA");
        lookup.addCityFilter("Dorchester");
        lookup.addCityFilter("Boston");
        lookup.addPreferCity("Dorchester");
        lookup.setMaxSuggestions(5);
        lookup.setPreferRatio(0.33333);

        Suggestion[] suggestions = client.send(lookup); // The client will also return the suggestions directly

        System.out.println();
        System.out.println("*** Result with some filters ***");
        for (Suggestion suggestion : suggestions) {
            System.out.println(suggestion.getStreetLine());
            System.out.println(suggestion.getSecondary());
            System.out.println(suggestion.getCity());
            System.out.println(", ");
            System.out.println(suggestion.getState());
            System.out.println(", ");
            System.out.println(suggestion.getZipcode());
        }
    }
}
