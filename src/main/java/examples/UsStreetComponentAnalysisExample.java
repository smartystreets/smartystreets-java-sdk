package examples;

import com.smartystreets.api.BasicAuthCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.json.JsonMapper;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.List;

public class UsStreetComponentAnalysisExample {
        public static void main(String[] args)  {
        // We recommend storing your authentication credentials in environment variables.
        // for client-side requests (browser/mobile), use this code:
        // SharedCredentials credentials = new SharedCredentials(System.getenv("SMARTY_AUTH_WEB"), System.getenv("SMARTY_AUTH_REFERER"));
        BasicAuthCredentials credentials = new BasicAuthCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

        try (Client client = new ClientBuilder(credentials)
            .withFeatureComponentAnalysis() // To add component analysis feature you need to specify when you create the client.
            .buildUsStreetApiClient()) {

            Lookup lookup = new Lookup();
            lookup.setStreet("1 Rosedale");
            lookup.setSecondary("APT 2");
            lookup.setCity("Baltimore");
            lookup.setState("MD");
            lookup.setZipCode("21229");
            lookup.setMatch(MatchType.ENHANCED); // Enhanced matching is required to return component analysis results.

            client.send(lookup);

            List<Candidate> results = lookup.getResult();

            if (results.isEmpty()) {
                return;
            }

            Candidate firstCandidate = results.get(0);

            // Here is an example of how to access component analysis
            ComponentAnalysis componentAnalysis = firstCandidate.getAnalysis().getComponents();

            JsonMapper mapper = JsonMapper.builder()
                .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
                .build();

            System.out.println("Component Analysis Results:\n" + mapper.writeValueAsString(componentAnalysis));
        } catch (SmartyException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
