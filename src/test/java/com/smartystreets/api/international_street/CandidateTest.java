package com.smartystreets.api.international_street;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import com.smartystreets.api.SmartySerializer;

public class CandidateTest {

    @Test
    public void testAllFieldsFilledCorrectly() throws IOException {
        String responsePayload = "[{\"input_id\":\"1234\",\"organization\":\"1\",\"address1\":\"2\",\"address2\":\"3\"," +
                "\"address3\":\"4\",\"address4\":\"5\",\"address5\":\"6\",\"address6\":\"7\",\"address7\":\"8\"," +
                "\"address8\":\"9\",\"address9\":\"10\",\"address10\":\"11\",\"address11\":\"12\",\"address12\":\"13\"," +
                "\"components\":{\"country_iso_3\":\"14\",\"super_administrative_area\":\"15\"," +
                "\"administrative_area\":\"16\",\"administrative_area_iso2\":\"16.1\",\"administrative_area_short\":\"16.2\",\"administrative_area_long\":\"16.3\"," +
                "\"sub_administrative_area\":\"17\",\"dependent_locality\":\"18\"," +
                "\"dependent_locality_name\":\"19\",\"double_dependent_locality\":\"20\",\"locality\":\"21\"," +
                "\"postal_code\":\"22\",\"postal_code_short\":\"23\",\"postal_code_extra\":\"24\"," +
                "\"premise\":\"25\",\"premise_extra\":\"26\",\"premise_number\":\"27\"," +
                "\"premise_prefix_number\":\"27.5\",\"premise_type\":\"28\"," +
                "\"thoroughfare\":\"29\",\"thoroughfare_predirection\":\"30\",\"thoroughfare_postdirection\":\"31\"," +
                "\"thoroughfare_name\":\"32\",\"thoroughfare_trailing_type\":\"33\",\"thoroughfare_type\":\"34\"," +
                "\"dependent_thoroughfare\":\"35\",\"dependent_thoroughfare_predirection\":\"36\"," +
                "\"dependent_thoroughfare_postdirection\":\"37\",\"dependent_thoroughfare_name\":\"38\"," +
                "\"dependent_thoroughfare_trailing_type\":\"39\",\"dependent_thoroughfare_type\":\"40\"," +
                "\"building\":\"41\",\"building_leading_type\":\"42\",\"building_name\":\"43\"," +
                "\"building_trailing_type\":\"44\",\"sub_building_type\":\"45\",\"sub_building_number\":\"46\"," +
                "\"sub_building_name\":\"47\",\"sub_building\":\"48\",\"level_type\":\"48.1\",\"level_number\":\"48.2\"," +
                "\"post_box\":\"49\",\"post_box_type\":\"50\",\"post_box_number\":\"51\"," +
                "\"additional_content\":\"112\",\"delivery_installation\":\"113\",\"delivery_installation_type\":\"114\"," +
                "\"delivery_installation_qualifier_name\":\"115\",\"route\":\"116\",\"route_number\":\"117\"," +
                "\"route_type\":\"118\"}," +
                "\"metadata\":{\"latitude\":52.0,\"longitude\":53.0," +
                "\"geocode_precision\":\"54\",\"max_geocode_precision\":\"55\",\"address_format\":\"56\",\"occupant_use\":\"56.1\"}," +
                "\"analysis\":{\"verification_status\":\"57\",\"address_precision\":\"58\"," +
                "\"max_address_precision\":\"59\",\"changes\":{\"organization\":\"60\"," +
                "\"address1\":\"61\",\"address2\":\"62\",\"address3\":\"63\",\"address4\":\"64\",\"address5\":\"65\"," +
                "\"address6\":\"66\",\"address7\":\"67\",\"address8\":\"68\",\"address9\":\"69\",\"address10\":\"70\"," +
                "\"address11\":\"71\",\"address12\":\"72\",\"components\":{\"super_administrative_area\":\"73\"," +
                "\"administrative_area\":\"74\",\"administrative_area_short\":\"74.1\",\"administrative_area_long\":\"74.2\"," +
                "\"sub_administrative_area\":\"75\",\"building\":\"76\"," +
                "\"dependent_locality\":\"77\",\"dependent_locality_name\":\"78\",\"double_dependent_locality\":\"79\"," +
                "\"country_iso_3\":\"80\",\"locality\":\"81\",\"postal_code\":\"82\",\"postal_code_short\":\"83\"," +
                "\"postal_code_extra\":\"84\",\"premise\":\"85\",\"premise_extra\":\"86\",\"premise_number\":\"87\"," +
                "\"premise_type\":\"88\",\"premise_prefix_number\":\"89\",\"thoroughfare\":\"90\"," +
                "\"thoroughfare_predirection\":\"91\",\"thoroughfare_postdirection\":\"92\"," +
                "\"thoroughfare_name\":\"93\",\"thoroughfare_trailing_type\":\"94\",\"thoroughfare_type\":\"95\"," +
                "\"dependent_thoroughfare\":\"96\",\"dependent_thoroughfare_predirection\":\"97\"," +
                "\"dependent_thoroughfare_postdirection\":\"98\",\"dependent_thoroughfare_name\":\"99\"," +
                "\"dependent_thoroughfare_trailing_type\":\"100\",\"dependent_thoroughfare_type\":\"101\"," +
                "\"building_leading_type\":\"102\",\"building_name\":\"103\",\"building_trailing_type\":\"104\"," +
                "\"sub_building_type\":\"105\",\"sub_building_number\":\"106\",\"sub_building_name\":\"107\"," +
                "\"sub_building\":\"108\",\"level_type\":\"108.1\",\"level_number\":\"108.2\"," +
                "\"post_box\":\"109\",\"post_box_type\":\"110\",\"post_box_number\":\"111\"}}}}]";

        SmartySerializer smartySerializer = new SmartySerializer();
        Candidate candidate = smartySerializer.deserialize(responsePayload.getBytes(), Candidate[].class)[0];

        //region [ Candidate ]
        assertEquals("1234", candidate.getInputId());
        assertEquals("1", candidate.getOrganization());
        assertEquals("2", candidate.getAddress1());
        assertEquals("3", candidate.getAddress2());
        assertEquals("4", candidate.getAddress3());
        assertEquals("5", candidate.getAddress4());
        assertEquals("6", candidate.getAddress5());
        assertEquals("7", candidate.getAddress6());
        assertEquals("8", candidate.getAddress7());
        assertEquals("9", candidate.getAddress8());
        assertEquals("10", candidate.getAddress9());
        assertEquals("11", candidate.getAddress10());
        assertEquals("12", candidate.getAddress11());
        assertEquals("13", candidate.getAddress12());
        //endregion

        //region [ Components ]
        Components components = candidate.getComponents();
        assertNotNull(components);
        assertEquals("14", components.getCountryIso3());
        assertEquals("15", components.getSuperAdministrativeArea());
        assertEquals("16", components.getAdministrativeArea());
        assertEquals("16.1", components.getAdministrativeAreaISO2());
        assertEquals("16.2", components.getAdministrativeAreaShort());
        assertEquals("16.3", components.getAdministrativeAreaLong());
        assertEquals("17", components.getSubAdministrativeArea());
        assertEquals("18", components.getDependentLocality());
        assertEquals("19", components.getDependentLocalityName());
        assertEquals("20", components.getDoubleDependentLocality());
        assertEquals("21", components.getLocality());
        assertEquals("22", components.getPostalCode());
        assertEquals("23", components.getPostalCodeShort());
        assertEquals("24", components.getPostalCodeExtra());
        assertEquals("25", components.getPremise());
        assertEquals("26", components.getPremiseExtra());
        assertEquals("27", components.getPremiseNumber());
        assertEquals("27.5", components.getPremisePrefixNumber());
        assertEquals("28", components.getPremiseType());
        assertEquals("29", components.getThoroughfare());
        assertEquals("30", components.getThoroughfarePredirection());
        assertEquals("31", components.getThoroughfarePostdirection());
        assertEquals("32", components.getThoroughfareName());
        assertEquals("33", components.getThoroughfareTrailingType());
        assertEquals("34", components.getThoroughfareType());
        assertEquals("35", components.getDependentThoroughfare());
        assertEquals("36", components.getDependentThoroughfarePredirection());
        assertEquals("37", components.getDependentThoroughfarePostdirection());
        assertEquals("38", components.getDependentThoroughfareName());
        assertEquals("39", components.getDependentThoroughfareTrailingType());
        assertEquals("40", components.getDependentThoroughfareType());
        assertEquals("41", components.getBuilding());
        assertEquals("42", components.getBuildingLeadingType());
        assertEquals("43", components.getBuildingName());
        assertEquals("44", components.getBuildingTrailingType());
        assertEquals("45", components.getSubBuildingType());
        assertEquals("46", components.getSubBuildingNumber());
        assertEquals("47", components.getSubBuildingName());
        assertEquals("48", components.getSubBuilding());
        assertEquals("48.1", components.getLevelType());
        assertEquals("48.2", components.getLevelNumber());
        assertEquals("49", components.getPostBox());
        assertEquals("50", components.getPostBoxType());
        assertEquals("51", components.getPostBoxNumber());
        assertEquals("112", components.getAdditionalContent());
        assertEquals("113", components.getDeliveryInstallation());
        assertEquals("114", components.getDeliveryInstallationType());
        assertEquals("115", components.getDeliveryInstallationQualifierName());
        assertEquals("116", components.getRoute());
        assertEquals("117", components.getRouteNumber());
        assertEquals("118", components.getRouteType());
        //endregion

        //region [ Metadata ]
        Metadata metadata = candidate.getMetadata();
        assertNotNull(metadata);
        assertEquals(52, metadata.getLatitude(), 0.001);
        assertEquals(53, metadata.getLongitude(), 0.001);
        assertEquals("54", metadata.getGeocodePrecision());
        assertEquals("55", metadata.getMaxGeocodePrecision());
        assertEquals("56", metadata.getAddressFormat());
        assertEquals("56.1", metadata.getOccupantUse());
        //endregion

        //region [ Analysis ]
        Analysis analysis = candidate.getAnalysis();
        assertNotNull(analysis);
        assertEquals("57", analysis.getVerificationStatus());
        assertEquals("58", analysis.getAddressPrecision());
        assertEquals("59", analysis.getMaxAddressPrecision());

        //region [ Changes ]
        Changes changes = analysis.getChanges();
        assertNotNull(changes);
        assertEquals("60", changes.getOrganization());
        assertEquals("61", changes.getAddress1());
        assertEquals("62", changes.getAddress2());
        assertEquals("63", changes.getAddress3());
        assertEquals("64", changes.getAddress4());
        assertEquals("65", changes.getAddress5());
        assertEquals("66", changes.getAddress6());
        assertEquals("67", changes.getAddress7());
        assertEquals("68", changes.getAddress8());
        assertEquals("69", changes.getAddress9());
        assertEquals("70", changes.getAddress10());
        assertEquals("71", changes.getAddress11());
        assertEquals("72", changes.getAddress12());

        //region [ Components ]
        Components ccomponents = changes.getComponents();
        assertNotNull(ccomponents);
        assertEquals("73", ccomponents.getSuperAdministrativeArea());
        assertEquals("74", ccomponents.getAdministrativeArea());
        assertEquals("74.1", ccomponents.getAdministrativeAreaShort());
        assertEquals("74.2", ccomponents.getAdministrativeAreaLong());
        assertEquals("75", ccomponents.getSubAdministrativeArea());
        assertEquals("76", ccomponents.getBuilding());
        assertEquals("77", ccomponents.getDependentLocality());
        assertEquals("78", ccomponents.getDependentLocalityName());
        assertEquals("79", ccomponents.getDoubleDependentLocality());
        assertEquals("80", ccomponents.getCountryIso3());
        assertEquals("81", ccomponents.getLocality());
        assertEquals("82", ccomponents.getPostalCode());
        assertEquals("83", ccomponents.getPostalCodeShort());
        assertEquals("84", ccomponents.getPostalCodeExtra());
        assertEquals("85", ccomponents.getPremise());
        assertEquals("86", ccomponents.getPremiseExtra());
        assertEquals("87", ccomponents.getPremiseNumber());
        assertEquals("88", ccomponents.getPremiseType());
        assertEquals("89", ccomponents.getPremisePrefixNumber());
        assertEquals("90", ccomponents.getThoroughfare());
        assertEquals("91", ccomponents.getThoroughfarePredirection());
        assertEquals("92", ccomponents.getThoroughfarePostdirection());
        assertEquals("93", ccomponents.getThoroughfareName());
        assertEquals("94", ccomponents.getThoroughfareTrailingType());
        assertEquals("95", ccomponents.getThoroughfareType());
        assertEquals("96", ccomponents.getDependentThoroughfare());
        assertEquals("97", ccomponents.getDependentThoroughfarePredirection());
        assertEquals("98", ccomponents.getDependentThoroughfarePostdirection());
        assertEquals("99", ccomponents.getDependentThoroughfareName());
        assertEquals("100", ccomponents.getDependentThoroughfareTrailingType());
        assertEquals("101", ccomponents.getDependentThoroughfareType());
        assertEquals("102", ccomponents.getBuildingLeadingType());
        assertEquals("103", ccomponents.getBuildingName());
        assertEquals("104", ccomponents.getBuildingTrailingType());
        assertEquals("105", ccomponents.getSubBuildingType());
        assertEquals("106", ccomponents.getSubBuildingNumber());
        assertEquals("107", ccomponents.getSubBuildingName());
        assertEquals("108", ccomponents.getSubBuilding());
        assertEquals("108.1", ccomponents.getLevelType());
        assertEquals("108.2", ccomponents.getLevelNumber());
        assertEquals("109", ccomponents.getPostBox());
        assertEquals("110", ccomponents.getPostBoxType());
        assertEquals("111", ccomponents.getPostBoxNumber());
        //endregion
        //endregion
        //endregion
    }
}
