package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.NotModifiedException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.*;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_financial.PropertyFinancialLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_principal.PropertyPrincipalLookup;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import com.smartystreets.api.us_enrichment.lookup_types.risk.RiskLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryCountLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryLookup;

import com.smartystreets.api.us_enrichment.result_types.Result;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.result_types.risk.RiskResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.Secondary;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UsEnrichmentExample {
    public static void main(String[] args) {
        // We recommend storing your authentication credentials in environment variables.
        // for server-to-server requests, use this code:
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));
        // for client-side requests (browser/mobile), use this code:
        //SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));

        Client client = new ClientBuilder(credentials).buildUsEnrichmentClient();

        String smartyKey = "87844267";
        String smartyKeyWithSecondaries = "325023201";
        String include = "";   // example: "group_structural,sale_date";
        String exclude = "";
        String features = "financial";

        // ************************ Property Principal ************************
        PropertyPrincipalLookup principalLookup = new PropertyPrincipalLookup();
        principalLookup.setSmartyKey(smartyKey);
        principalLookup.setInclude(include);
        principalLookup.setFeatures(features);
        // To perform an address lookup instead of by SmartyKey you would uncomment the following line and comment setting the SmartyKey
        // principalLookup.setAddressSearch(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));
        // To set the ETag value from a previous call, uncomment the following line and set the appropriate ETag value
        // principalLookup.setEtag("GMYDMOBZGIZTGMJQ");

        PrincipalResponse[] principalResults = null;
        try {
            principalResults = client.sendPropertyPrincipal(principalLookup);
        } catch (NotModifiedException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if(principalResults != null){
            System.out.println("Address Lookup:\n" + Arrays.toString(principalResults));
        } else {
            System.out.println("Result was null");
        }

        // ************************ Property Financial ************************
        PropertyFinancialLookup financialLookup = new PropertyFinancialLookup();
        financialLookup.setSmartyKey(smartyKey);
        //financialLookup.setAddressSearch(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));
        //financialLookup.setEtag("GEYTENBXGY3TKMRU");

        FinancialResponse[] financialResults = null;
        try {
            financialResults = client.sendPropertyFinancial(financialLookup);
        } catch (NotModifiedException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (financialResults != null) {
            System.out.println(Arrays.toString(financialResults));
        } else {
            System.out.println("Result was null");
        }

        // ************************ GeoReference ************************
        GeoReferenceLookup geoReferenceLookup = new GeoReferenceLookup(""); // the parameter is the subset (2010 or 2020)
        geoReferenceLookup.setSmartyKey(smartyKey);
        //geoReferenceLookup.setAddressSearch(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));
        //geoReferenceLookup.setEtag("GEYTENBXGY3TKMRU");

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

        // ************************ Risk ************************
        RiskLookup riskLookup = new RiskLookup();
        riskLookup.setSmartyKey(smartyKey);
        //riskLookup.setAddressSearch(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));
        //riskLookup.setEtag("GEYTENBXGY3TKMRU");

        RiskResponse[] riskResults = null;
        try {
            riskResults = client.sendRisk(riskLookup);
        } catch (NotModifiedException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (riskResults != null) {
            System.out.println(Arrays.toString(riskResults));
        } else {
            System.out.println("Result was null");
        }

        // ************************ Secondary Count ************************
        SecondaryCountLookup secondaryCountLookup = new SecondaryCountLookup();
        secondaryCountLookup.setSmartyKey(smartyKeyWithSecondaries);
        //secondaryCountLookup.setAddressSearch(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));
        //secondaryCountLookup.setEtag("GEYTENBXGY3TKMRU");

        SecondaryCountResponse[] secondaryCountResults = null;
        try {
            secondaryCountResults = client.sendSecondaryCount(secondaryCountLookup);
        } catch (NotModifiedException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (secondaryCountResults != null){
            System.out.println(Arrays.toString(secondaryCountResults));
        } else {
            System.out.println("Result was null");
        }

        // ************************ Secondary ************************
        SecondaryLookup secondaryLookup = new SecondaryLookup();
        secondaryLookup.setSmartyKey(smartyKeyWithSecondaries);
        //secondaryLookup.setAddressSearch(new AddressSearch().withStreet("56 Union Ave").withCity("Somerville").withState("NJ").withZipcode("08876"));
        //secondaryLookup.setEtag("GEYTENBXGY3TKMRU");

        SecondaryResponse[] secondaryResults = null;
        try {
            secondaryResults = client.sendSecondary(secondaryLookup);
        } catch (NotModifiedException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (secondaryResults != null){
            System.out.println(Arrays.toString(secondaryResults));
            System.out.println(Arrays.stream(secondaryResults).flatMap(s -> s.getSecondaries().stream() ).map(Secondary::toString).collect(Collectors.joining("\n")));
        } else {
            System.out.println("Result was null");
        }

    }

}
