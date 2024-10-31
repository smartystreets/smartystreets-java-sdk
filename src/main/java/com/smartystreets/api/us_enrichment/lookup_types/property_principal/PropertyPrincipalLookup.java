package com.smartystreets.api.us_enrichment.lookup_types.property_principal;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.AddressSearch;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;

import java.io.IOException;

public class PropertyPrincipalLookup extends Lookup {
    private PrincipalResponse[] results;
    public PropertyPrincipalLookup(String smartyKey) {
        super(smartyKey, "property", "principal");
    }

    public PropertyPrincipalLookup(AddressSearch addressSearch) {
        super(addressSearch, "property", "principal");
    }

    public PrincipalResponse[] getResults() {
        return results;
    }

    public void setResults(PrincipalResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException {
        this.results = serializer.deserialize(payload, PrincipalResponse[].class);
    }
}
