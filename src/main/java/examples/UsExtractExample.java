package examples;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_extract.*;
import com.smartystreets.api.us_street.Candidate;

import java.io.IOException;

public class UsExtractExample {
    public static void main(String[] args) throws IOException, SmartyException {
        // We recommend storing your secret keys in environment variables.
        StaticCredentials credentials = new StaticCredentials(System.getenv("SMARTY_AUTH_ID"), System.getenv("SMARTY_AUTH_TOKEN"));
        Client client = new ClientBuilder(credentials).buildUsExtractApiClient();
        String text = "Here is some text.\r\nMy address is 3785 Las Vegs Av." +
                "\r\nLos Vegas, Nevada." +
                "\r\nMeet me at 1 Rosedale Baltimore Maryland, not at 123 Phony Street, Boise Idaho.";

        // Documentation for input fields can be found at:
        // https://smartystreets.com/docs/cloud/us-extract-api#http-request-input-fields

        Lookup lookup = new Lookup(text);
        lookup.isAggressive(true);
        lookup.addressesHaveLineBreaks();
        lookup.getAddressesPerLine();

        Result result = client.send(lookup);

        Metadata metadata = result.getMetadata();
        System.out.println("Found " + metadata.getAddressCount() + " addresses.");
        System.out.println(metadata.getVerifiedCount() + " of them were valid.");
        System.out.println();

        Address[] addresses = result.getAddresses();

        System.out.println("Addresses: \r\n**********************\r\n");
        for (Address address : addresses) {
            System.out.println("\"" + address.getText() + "\"\n");
            System.out.println("Verified? " + address.isVerified());
            if (address.getCandidates().length > 0) {
                System.out.println("\nMatches:");

                for (Candidate candidate : address.getCandidates()) {
                    System.out.println(candidate.getDeliveryLine1());
                    System.out.println(candidate.getLastLine());
                    System.out.println();
                }
            } else System.out.println();

            System.out.println("**********************\n");
        }
    }
}
