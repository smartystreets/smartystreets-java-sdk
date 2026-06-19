package examples;

import com.smartystreets.api.BasicAuthCredentials;
import com.smartystreets.api.exceptions.BatchFullException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsStreetLookupsWithMatchStrategyExamples {

    private static final String SEPARATOR = "======================================================================";

    // One row per (address, strategy) lookup, kept parallel to the batch order.
    private static class Case {
        final String label;
        final String address;
        final MatchType strategy;

        Case(String label, String address, MatchType strategy) {
            this.label = label;
            this.address = address;
            this.strategy = strategy;
        }
    }

    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        BasicAuthCredentials credentials = new BasicAuthCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        try (Client client = new ClientBuilder(credentials).buildUsStreetApiClient()) {
            Batch batch = new Batch();

            // Each address is run through all three match strategies so you can compare how
            // 'strict', 'enhanced', and 'invalid' each handle a valid, an invalid, and an
            // ambiguous address.
            //   - strict:   only returns candidates that are valid, mailable addresses.
            //   - enhanced: returns a more comprehensive dataset (requires a US Core or Rooftop license).
            //   - invalid:  most permissive; always returns at least one candidate (a best-guess standardization).
            // Documentation for input fields: https://smartystreets.com/docs/us-street-api#input-fields
            String[][] addresses = {
                {"valid (real, deliverable)",    "1600 Amphitheatre Pkwy", "Mountain View", "CA", "94043"},
                {"invalid (no such address)",    "9999 W 1150 S",          "Provo",         "UT", "84601"},
                {"ambiguous (missing ZIP/unit)", "1 Rosedale St",          "Baltimore",     "MD", ""},
            };
            MatchType[] strategies = {MatchType.STRICT, MatchType.ENHANCED, MatchType.INVALID};

            List<Case> cases = new ArrayList<Case>();

            for (String[] address : addresses) {
                String label = address[0], street = address[1], city = address[2], state = address[3], zip = address[4];
                for (MatchType strategy : strategies) {
                    Lookup lookup = new Lookup();
                    lookup.setStreet(street);
                    lookup.setCity(city);
                    lookup.setState(state);
                    lookup.setZipCode(zip);
                    lookup.setMatch(strategy);
                    lookup.setMaxCandidates(10); // allow ambiguous addresses to return more than one match
                    batch.add(lookup);
                    cases.add(new Case(label, street + ", " + city + ", " + state, strategy));
                }
            }

            client.send(batch);

            List<Lookup> lookups = batch.getAllLookups();
            String lastAddress = null;

            for (int i = 0; i < batch.size(); i++) {
                Case c = cases.get(i);

                if (!c.address.equals(lastAddress)) {
                    System.out.println("\n" + SEPARATOR);
                    System.out.println(" Address: " + c.address + "  [" + c.label + "]");
                    System.out.println(SEPARATOR);
                    lastAddress = c.address;
                }

                List<Candidate> candidates = lookups.get(i).getResult();
                System.out.println("\n--- '" + c.strategy.getName() + "' strategy ---");

                if (candidates.isEmpty()) {
                    System.out.println("  0 candidates - no match returned under this strategy.");
                    continue;
                }

                System.out.println("  " + candidates.size() + " candidate(s):");
                for (Candidate candidate : candidates) {
                    System.out.println("    [" + candidate.getCandidateIndex() + "] " + candidate.getDeliveryLine1() + "  " + candidate.getLastLine());
                }
            }
        } catch (BatchFullException ex) {
            System.out.println("Oops! Batch was already full.");
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
