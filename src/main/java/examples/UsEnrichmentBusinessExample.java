package examples;

import com.smartystreets.api.BasicAuthCredentials;
import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.Client;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessDetailResponse;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessEntry;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessSummaryResponse;

import java.io.IOException;

public class UsEnrichmentBusinessExample {
    public static void main(String[] args) {
        BasicAuthCredentials credentials = new BasicAuthCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        try (Client client = new ClientBuilder(credentials).buildUsEnrichmentClient()) {
            String smartyKey = "1962995076";

            BusinessSummaryResponse[] summaryResults;
            try {
                summaryResults = client.sendBusinessSummary(smartyKey);
            } catch (SmartyException | InterruptedException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
                return;
            }

            if (summaryResults == null || summaryResults.length == 0) {
                System.out.println("No response returned for SmartyKey " + smartyKey);
                return;
            }

            BusinessSummaryResponse summary = summaryResults[0];
            if (summary.getBusinesses() == null || summary.getBusinesses().length == 0) {
                System.out.println("SmartyKey " + smartyKey + " has no business tenants");
                return;
            }

            System.out.println("Summary results for SmartyKey: " + smartyKey);
            for (BusinessEntry entry : summary.getBusinesses()) {
                System.out.println("  - " + entry.getCompanyName() + " (ID: " + entry.getBusinessId() + ")");
            }

            BusinessEntry first = summary.getBusinesses()[0];
            System.out.println();
            System.out.println("Fetching details for business: " + first.getCompanyName() + " (ID: " + first.getBusinessId() + ")");

            BusinessDetailResponse detail;
            try {
                detail = client.sendBusinessDetail(first.getBusinessId());
            } catch (SmartyException | InterruptedException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
                return;
            }

            if (detail != null) {
                System.out.println();
                System.out.println("Detail result:");
                System.out.println(detail);
            } else {
                System.out.println();
                System.out.println("No detail result returned");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
