package examples;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;

public class UsAutocompleteExample {
    public static void main(String[] args) throws IOException, SmartyException {
        // We recommend storing your secret keys in environment variables.
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));
        Client client = new ClientBuilder(credentials).buildUSAutocompleteAPIClient();
        Lookup lookup = new Lookup("4770 Lincoln Ave O");

        client.send(lookup);

        Result result = lookup.getResult();

        System.out.println("*** Result with no filter ***");
        System.out.println();
        for (Suggestion suggestion : result.getSuggestions()) {
            System.out.println(suggestion.getText());
        }

        lookup.addStateFilter("IL");
        lookup.setMaxSuggestions(5);

        client.send(lookup);

        result = lookup.getResult();

        System.out.println();
        System.out.println("*** Result with some filters ***");
        for (Suggestion suggestion : result.getSuggestions()) {
            System.out.println(suggestion.getText());
        }
    }
}
