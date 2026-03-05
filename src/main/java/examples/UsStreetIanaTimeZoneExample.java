package examples;

import com.smartystreets.api.BasicAuthCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.List;

public class UsStreetIanaTimeZoneExample {
	public static void main(String[] args) {
		BasicAuthCredentials credentials = new BasicAuthCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));

		try (Client client = new ClientBuilder(credentials)
			.withFeatureIanaTimeZone()
			.buildUsStreetApiClient()) {

			Lookup lookup = new Lookup();
			lookup.setStreet("1 Rosedale");
			lookup.setSecondary("APT 2");
			lookup.setCity("Baltimore");
			lookup.setState("MD");
			lookup.setZipCode("21229");
			lookup.setMatch(MatchType.ENHANCED);

			client.send(lookup);

			List<Candidate> results = lookup.getResult();

			if (results.isEmpty()) {
				return;
			}

			Candidate firstCandidate = results.get(0);
			Metadata metadata = firstCandidate.getMetadata();

			System.out.println("IANA Timezone Results:");
			System.out.println("time_zone: " + metadata.getTimeZone());
			System.out.println("utc_offset: " + metadata.getUtcOffset());
			System.out.println("dst: " + metadata.obeysDst());
			System.out.println("iana_time_zone: " + metadata.getIanaTimeZone());
			System.out.println("iana_utc_offset: " + metadata.getIanaUtcOffset());
			System.out.println("iana_dst: " + metadata.obeysIanaDst());
		} catch (SmartyException | IOException | InterruptedException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
}
