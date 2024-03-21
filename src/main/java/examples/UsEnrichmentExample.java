package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.NotModifiedException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.*;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_financial.PropertyFinancialLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_principal.PropertyPrincipalLookup;
import com.smartystreets.api.us_enrichment.result_types.Result;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;


import java.io.IOException;
import java.util.Arrays;

public class UsEnrichmentExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        // for server-to-server requests, use this code:
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));


        // for client-side requests (browser/mobile), use this code:
        //SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));
        //make sure to Remove this when you are not testing. This is to hit rivendell
        Client client = new ClientBuilder(credentials).buildUsEnrichmentClient();

        PrincipalResponse[] results = null;
        String smartyKey = "1682393594";
        String include = "group_structural,sale_date";
        String exclude = "";
        String etag = "";
        //Example of Etag: GU4TINZRHA4TQMY

        PropertyPrincipalLookup principalLookup = new PropertyPrincipalLookup(smartyKey, include, exclude, etag);
        PropertyFinancialLookup financialLookup = new PropertyFinancialLookup(smartyKey, include, exclude, etag);
        GeoReferenceLookup geoReferenceLookup = new GeoReferenceLookup(smartyKey, etag);

        //DONE with this for property principle lookup, just need to clean it up and finish for property financial and financial lookup.

        try {
            results = client.sendPropertyPrincipal(principalLookup);


        } catch (NotModifiedException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (results != null) {
            System.out.println(Arrays.toString(results));
        } else {
            System.out.println("Result was null");
        }

        FinancialResponse[] financialResults = null;
        try {
            financialResults = client.sendPropertyFinancial(financialLookup);
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (results != null) {
            System.out.println(Arrays.toString(financialResults));
        } else {
            System.out.println("Result was null");
        }

        GeoReferenceResponse[] geoReferenceResults = null;
        try {
            geoReferenceResults = client.sendGeoReference(geoReferenceLookup);
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (results != null) {
            System.out.println(Arrays.toString(geoReferenceResults));
        } else {
            System.out.println("Result was null");
        }
    }


}
