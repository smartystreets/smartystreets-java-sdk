package examples;

import com.smartystreets.api.BasicAuthCredentials;
import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.Client;
import com.smartystreets.api.us_enrichment.lookup_types.business.BusinessSummaryLookup;
import com.smartystreets.api.us_enrichment.result_types.AddressSearch;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessDetailResponse;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessEntry;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessSummaryResponse;

import java.io.IOException;

public class UsEnrichmentBusinessNameSearchExample {
    public static void main(String[] args) {
        BasicAuthCredentials credentials = new BasicAuthCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        try (Client client = new ClientBuilder(credentials).buildUsEnrichmentClient()) {
            String businessName = "delta air";

            BusinessSummaryLookup lookup = new BusinessSummaryLookup();
            lookup.setAddressSearch(new AddressSearch()
                    .withBusinessName(businessName)
                    .withCity("atlanta"));

            BusinessSummaryResponse[] summaryResults;
            try {
                summaryResults = client.sendBusinessSummary(lookup);
            } catch (SmartyException | InterruptedException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
                return;
            }

            if (summaryResults == null || summaryResults.length == 0) {
                System.out.println("No response returned for the business-name search");
                return;
            }

            BusinessSummaryResponse summary = summaryResults[0];
            if (summary.getBusinesses() == null || summary.getBusinesses().length == 0) {
                System.out.println("No businesses found for this business name search");
                return;
            }

            System.out.println("Summary results for BusinessName: " + businessName);
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
