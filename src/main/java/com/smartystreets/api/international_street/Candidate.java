package com.smartystreets.api.international_street;

import com.google.api.client.util.Key;

/**
 * A candidate is a possible match for an address that was submitted.<br>
 *     A lookup can have multiple candidates if the address was ambiguous.
 *
 * @see "https://smartystreets.com/docs/cloud/international-street-api#root"
 */
public class Candidate extends RootLevel {
    //region [ Fields ]

    @Key("components")
    private Components components;

    @Key("metadata")
    private Metadata metadata;

    @Key("analysis")
    private Analysis analysis;

    //endregion

    //region [ Getters ]

    public Components getComponents() {
        return components;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    //endregion
}
