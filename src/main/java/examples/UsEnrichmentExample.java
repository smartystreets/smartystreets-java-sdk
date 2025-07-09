package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.NotModifiedException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.*;
import com.smartystreets.api.us_enrichment.lookup_types.property_financial.PropertyFinancialLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_principal.PropertyPrincipalLookup;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryCountLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryLookup;

import com.smartystreets.api.us_enrichment.result_types.Result;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.Secondary;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;

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
        //SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));
        //make sure to Remove this when you are not testing. This is to hit rivendell
        Client client = new ClientBuilder(credentials).buildUsEnrichmentClient();

        String smartyKey = "1682393594";
        String include = "group_structural,sale_date";
        String exclude = "";

        SecondaryLookup secondaryLookup = new SecondaryLookup();

        // ************************ Property Principal ************************
//         PropertyPrincipalLookup principalLookup = new PropertyPrincipalLookup();
//         principalLookup.setSmartyKey(smartyKey);
//         principalLookup.setInclude(include);
//         // principalLookup.setAddressSearch(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));
//         // principalLookup.setEtag("GMYDMOBZGIZTGMJQ");
//
//         PrincipalResponse[] principalResults = null;
//         try {
//             principalResults = client.sendPropertyPrincipal(principalLookup);
//         } catch (NotModifiedException ex) {
//             System.out.println(ex.getMessage());
//             return;
//         } catch (SmartyException | IOException | InterruptedException ex) {
//             System.out.println(ex.getMessage());
//             ex.printStackTrace();
//         }
//
//         if(principalResults != null){
//             System.out.println("Address Lookup:\n" + Arrays.toString(principalResults));
//         } else {
//             System.out.println("Result was null");
//         }

        // ************************ Property Financial ************************
//         PropertyFinancialLookup financialLookup = new PropertyFinancialLookup();
//         financialLookup.setSmartyKey(smartyKey);
//
//         FinancialResponse[] financialResults = null;
//         try {
//             financialResults = client.sendPropertyFinancial(financialLookup);
//         } catch (NotModifiedException ex) {
//             System.out.println(ex.getMessage());
//             return;
//         } catch (SmartyException | IOException | InterruptedException ex) {
//             System.out.println(ex.getMessage());
//             ex.printStackTrace();
//         }
//
//         if (financialResults != null) {
//             System.out.println(Arrays.toString(financialResults));
//         } else {
//             System.out.println("Result was null");
//         }

        // ************************ GeoReference ************************
        GeoReferenceLookup geoReferenceLookup = new GeoReferenceLookup("");
        geoReferenceLookup.setSmartyKey(smartyKey);
        // geoReferenceLookup.setEtag("your-etag-value");
        // geoReferenceLookup.setAddressSearch(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));

        GeoReferenceResponse[] geoReferenceResults = null;
        try {
            geoReferenceResults = client.sendGeoReference(geoReferenceLookup);
        } catch (NotModifiedException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (geoReferenceResults != null) {
            System.out.println(Arrays.toString(geoReferenceResults));
        } else {
            System.out.println("Result was null");
        }

        GeoReferenceLookup geoReferenceLookup2 = new GeoReferenceLookup("2010");
        geoReferenceLookup2.setSmartyKey(smartyKey);
        geoReferenceResults = null;
        try {
            geoReferenceResults = client.sendGeoReference(geoReferenceLookup2);
        } catch (NotModifiedException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (geoReferenceResults != null) {
            System.out.println(Arrays.toString(geoReferenceResults));
        } else {
            System.out.println("Result was null");
        }


        // ************************ Secondary Count ************************
//         SecondaryCountResponse[] secondaryCountResults = null;
//         try {
//             secondaryCountResults = client.sendSecondaryCountLookup("325023201");
//         } catch (NotModifiedException ex) {
//             System.out.println(ex.getMessage());
//             return;
//         } catch (SmartyException | IOException | InterruptedException ex) {
//             System.out.println(ex.getMessage());
//             ex.printStackTrace();
//         }
//
//         if (secondaryCountResults != null){
//             System.out.println(Arrays.toString(secondaryCountResults));
//         } else {
//             System.out.println("Result was null");
//         }

        // ************************ Secondary ************************
//         SecondaryResponse[] secondaryResults = null;
//         try {
//             secondaryResults = client.sendSecondaryLookup("325023201");
//         } catch (NotModifiedException ex) {
//             System.out.println(ex.getMessage());
//             return;
//         } catch (SmartyException | IOException | InterruptedException ex) {
//             System.out.println(ex.getMessage());
//             ex.printStackTrace();
//         }
//
//         if (secondaryResults != null){
//             System.out.println(Arrays.stream(secondaryResults).flatMap(s -> s.getSecondaries().stream() ).map(Secondary::toString).collect(Collectors.joining("\n")));
//         } else {
//             System.out.println("Result was null");
//         }

    }


}
