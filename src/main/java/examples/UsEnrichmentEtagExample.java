package examples;

import com.smartystreets.api.BasicAuthCredentials;
import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.exceptions.NotModifiedException;
import com.smartystreets.api.us_enrichment.Client;
import com.smartystreets.api.us_enrichment.lookup_types.business.BusinessDetailLookup;
import com.smartystreets.api.us_enrichment.lookup_types.business.BusinessSummaryLookup;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessSummaryResponse;

public class UsEnrichmentEtagExample {
    public static void main(String[] args) {
        BasicAuthCredentials credentials = new BasicAuthCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        try (Client client = new ClientBuilder(credentials).buildUsEnrichmentClient()) {
            String smartyKey = "1962995076";

            String businessId = exerciseSummaryEtag(client, smartyKey);
            if (businessId == null) return;

            exerciseDetailEtag(client, businessId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String exerciseSummaryEtag(Client client, String smartyKey) {
        System.out.println("=== Business.Summary ETag round trip ===");

        BusinessSummaryLookup first = new BusinessSummaryLookup(smartyKey);
        try {
            client.sendBusinessSummary(first);
        } catch (Exception ex) {
            System.out.println("  Initial Summary call failed: " + ex.getMessage());
            return null;
        }

        BusinessSummaryResponse[] initialResults = first.getResults();
        String capturedEtag = first.getResponseEtag();
        System.out.println("  Call 1 (no Etag): captured Etag=" + display(capturedEtag) +
                ", results=" + (initialResults == null ? 0 : initialResults.length));

        if (capturedEtag == null || capturedEtag.isEmpty()) {
            System.out.println("  Server did not return an Etag header; skipping conditional calls.");
            return firstBusinessId(initialResults);
        }

        BusinessSummaryLookup second = new BusinessSummaryLookup(smartyKey);
        second.setRequestEtag(capturedEtag);
        try {
            client.sendBusinessSummary(second);
            System.out.println("  Call 2 (matching Etag): 200 — server did NOT honor the conditional. Etag=" + display(second.getResponseEtag()));
        } catch (NotModifiedException ex) {
            System.out.println("  Call 2 (matching Etag): 304 NotModifiedException — caller treats this as cache-valid. Refreshed Etag=" + display(ex.getResponseEtag()));
        } catch (Exception ex) {
            System.out.println("  Call 2 unexpected failure: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            return null;
        }

        BusinessSummaryLookup third = new BusinessSummaryLookup(smartyKey);
        third.setRequestEtag(capturedEtag + "X");
        try {
            client.sendBusinessSummary(third);
            System.out.println("  Call 3 (mutated Etag): 200 as expected. Etag=" + display(third.getResponseEtag()));
        } catch (NotModifiedException ex) {
            System.out.println("  Call 3 (mutated Etag): 304 — UNEXPECTED. Server treated a different Etag as matching.");
        } catch (Exception ex) {
            System.out.println("  Call 3 unexpected failure: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }

        return firstBusinessId(initialResults);
    }

    private static void exerciseDetailEtag(Client client, String businessId) {
        System.out.println();
        System.out.println("=== Business.Detail ETag round trip (businessId: " + businessId + ") ===");

        BusinessDetailLookup first = new BusinessDetailLookup(businessId);
        try {
            client.sendBusinessDetail(first);
        } catch (Exception ex) {
            System.out.println("  Initial Detail call failed: " + ex.getMessage());
            return;
        }

        String capturedEtag = first.getResponseEtag();
        System.out.println("  Call 1 (no Etag): captured Etag=" + display(capturedEtag) +
                ", businessId=" + (first.getResult() == null ? "<null>" : first.getResult().getBusinessId()));

        if (capturedEtag == null || capturedEtag.isEmpty()) {
            System.out.println("  Server did not return an Etag header; skipping conditional calls.");
            return;
        }

        BusinessDetailLookup second = new BusinessDetailLookup(businessId);
        second.setRequestEtag(capturedEtag);
        try {
            client.sendBusinessDetail(second);
            System.out.println("  Call 2 (matching Etag): 200 — server did NOT honor the conditional. Etag=" + display(second.getResponseEtag()));
        } catch (NotModifiedException ex) {
            System.out.println("  Call 2 (matching Etag): 304 NotModifiedException — caller treats this as cache-valid. Refreshed Etag=" + display(ex.getResponseEtag()));
        } catch (Exception ex) {
            System.out.println("  Call 2 unexpected failure: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            return;
        }

        BusinessDetailLookup third = new BusinessDetailLookup(businessId);
        third.setRequestEtag(capturedEtag + "X");
        try {
            client.sendBusinessDetail(third);
            System.out.println("  Call 3 (mutated Etag): 200 as expected. Etag=" + display(third.getResponseEtag()));
        } catch (NotModifiedException ex) {
            System.out.println("  Call 3 (mutated Etag): 304 — UNEXPECTED. Server treated a different Etag as matching.");
        } catch (Exception ex) {
            System.out.println("  Call 3 unexpected failure: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private static String firstBusinessId(BusinessSummaryResponse[] results) {
        if (results == null || results.length == 0) return null;
        if (results[0].getBusinesses() == null || results[0].getBusinesses().length == 0) return null;
        return results[0].getBusinesses()[0].getBusinessId();
    }

    private static String display(String s) {
        return (s == null || s.isEmpty()) ? "<none>" : s;
    }
}
