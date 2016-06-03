package examples;

import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_zipcode.*;

import java.io.IOException;

public class UsZipCodeGetExample {
    public static void main(String[] args) {
        Client client = new ClientBuilder("YOUR AUTH-ID HERE", "YOUR AUTH-TOKEN HERE").build();

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
        CityAndState[] cityStates = result.getCityAndStates();

        for (CityAndState cityState : cityStates) {
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
