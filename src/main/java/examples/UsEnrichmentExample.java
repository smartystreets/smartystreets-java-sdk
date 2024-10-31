package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.*;
import com.smartystreets.api.us_enrichment.result_types.Result;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.Secondary;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UsEnrichmentExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        // for server-to-server requests, use this code:
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        // for client-side requests (browser/mobile), use this code:
//        SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));

        Client client = new ClientBuilder(credentials).buildUsEnrichmentClient();

        PrincipalResponse[] results = null;
        try {
            results = client.sendPropertyPrincipalLookup("325023201");
        }
        catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        results = null;
        try {
            results = client.sendPropertyPrincipalLookup(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));
        }
        catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if(results != null){
            System.out.println("Street Lookup:\n" + Arrays.toString(results));
        } else {
            System.out.println("Result was null");
        }

        results = null;
        try {
            results = client.sendPropertyPrincipalLookup("325023201");
        }
        catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if(results != null){
            System.out.println(Arrays.toString(results));
        } else {
            System.out.println("Result was null");
        }

        FinancialResponse[] financialResults = null;
        try {
            financialResults = client.sendPropertyFinancialLookup("325023201");
        }
        catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if(results != null){
            System.out.println(Arrays.toString(financialResults));
        } else {
            System.out.println("Result was null");
        }

        SecondaryCountResponse[] secondaryCountResults = null;
        try {
            secondaryCountResults = client.sendSecondaryCountLookup("325023201");
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (secondaryCountResults != null){
            System.out.println(Arrays.toString(secondaryCountResults));
        } else {
            System.out.println("Result was null");
        }

        SecondaryResponse[] secondaryResults = null;
        try {
            secondaryResults = client.sendSecondaryLookup("325023201");
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (secondaryResults != null){
            System.out.println(Arrays.stream(secondaryResults).flatMap(s -> s.getSecondaries().stream() ).map(Secondary::toString).collect(Collectors.joining("\n")));
        } else {
            System.out.println("Result was null");
        }
    }
}
