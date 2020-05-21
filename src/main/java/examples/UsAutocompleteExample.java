package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

public class UsAutocompleteExample {
    public static void main(String[] args) throws IOException, SmartyException {
        // We recommend storing your secret keys in environment variables.
        // for Server-toserver requests, use this code:
        // string authId = System.getenv("SMARTY_AUTH_ID");
        // string authToken = System.getenv("SMARTY_AUTH_TOKEN");
        // StaticCredentials credentials = new StaticCredentials(authId, authToken);

        // for client-side requests (browser/mobile), use this code:
        String key = System.getenv("SMARTY_AUTH_WEB");
        String hostname = System.getenv("SMARTY_AUTH_REFERER");
        SharedCredentials credentials = new SharedCredentials(key, hostname);

        Client client = new ClientBuilder(credentials).buildUsAutocompleteApiClient();
        Lookup lookup = new Lookup("4770 Lincoln Ave O");

        client.send(lookup);

        System.out.println("*** Result with no filter ***");
        System.out.println();
        for (Suggestion suggestion : lookup.getResult()) {
            System.out.println(suggestion.getText());
        }

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/cloud/us-autocomplete-api#http-request-input-fields

        lookup.addStateFilter("IL");
        lookup.addCityFilter("Ogden");
        lookup.addPrefer("Ogden, IL");
        lookup.setMaxSuggestions(5);
        lookup.setPreferRatio(0.33333);

        Suggestion[] suggestions = client.send(lookup); // The client will also return the suggestions directly

        System.out.println();
        System.out.println("*** Result with some filters ***");
        for (Suggestion suggestion : suggestions) {
            System.out.println(suggestion.getText());
        }
    }
}
