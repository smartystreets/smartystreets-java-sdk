package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.international_postal_code.Candidate;
import com.smartystreets.api.international_postal_code.Client;
import com.smartystreets.api.international_postal_code.Lookup;

public class InternationalPostalCodeExample {
    public static void main(String[] args) throws Exception {
        // To run: set environment variables or replace with your credentials.
        String authId = System.getenv("SMARTY_AUTH_ID");
        String authToken = System.getenv("SMARTY_AUTH_TOKEN");

        Client client = new ClientBuilder(authId, authToken)
                .buildInternationalPostalCodeApiClient();

        Lookup lookup = new Lookup();
        lookup.setInputId("ID-8675309");
        lookup.setLocality("Sao Paulo");
        lookup.setAdministrativeArea("SP");
        lookup.setCountry("Brazil");
        lookup.setPostalCode("02516");

        client.send(lookup);

        System.out.println("Results:");
        System.out.println();
        for (Candidate candidate : lookup.getResult()) {
            display(candidate.getInputId());
            display(candidate.getCountryIso3());
            display(candidate.getLocality());
            display(candidate.getDependentLocality());
            display(candidate.getDoubleDependentLocality());
            display(candidate.getSubAdministrativeArea());
            display(candidate.getAdministrativeArea());
            display(candidate.getSuperAdministrativeArea());
            display(candidate.getPostalCode());
            System.out.println();
        }
        System.out.println("OK");
    }
    private static void display(String value) {
        if (value != null && value.length() > 0) {
            System.out.printf("  %s\n", value);
        }
    }
}
