import com.smartystreets.api.Credentials;
import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_zipcode.*;

import java.io.IOException;

public class UsZipCodeGetExample {
    public static void main(String[] args) {
        Credentials credentials = new StaticCredentials("YOUR AUTH-ID HERE", "YOUR AUTH-TOKEN HERE");
        Client client = new ClientBuilder(credentials).build();

        Lookup lookup = new Lookup();
        lookup.setCity("Mountain View");
        lookup.setState("California");

        try {
            client.send(lookup);
        }
        catch (SmartyException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        Result result = lookup.getResult();
        ZipCode[] zipCodes = result.getZipCodes();
        CityState[] cityStates = result.getCityStates(); // TODO: Should we rename CityState to CityAndState, or something?

        for (CityState cityState : cityStates) {
            System.out.println("\nCity: " + cityState.getCity());
            System.out.println("State: " + cityState.getState());
            System.out.println("Mailable City: " + cityState.getMailableCity());
        }

        for (ZipCode zip : zipCodes) {
            System.out.println("\nZIP Code: " + zip.getZipCode());
            System.out.println("Latitude: " + zip.getLatitude());
            System.out.println("Longitude: " + zip.getLongitude());
        }
    }
}
