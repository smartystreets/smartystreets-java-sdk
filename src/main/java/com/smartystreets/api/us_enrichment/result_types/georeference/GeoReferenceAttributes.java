package com.smartystreets.api.us_enrichment.result_types.georeference;

import com.smartystreets.api.us_enrichment.result_types.Attributes;
import com.smartystreets.api.us_enrichment.result_types.EnrichmentToStringer;

public class GeoReferenceAttributes extends Attributes{
        public CensusBlock census_block;
        public CensusCountyDivision census_county_division;
        public CensusTract censusTract;
        public CoreBasedStatArea core_based_stat_area;
        public Place place;

        public static class CensusBlock extends EnrichmentToStringer {
            public String accuracy;
            public String geoid;
        }

        public static class CensusCountyDivision extends EnrichmentToStringer{
            public String accuracy;
            public String code;
            public String name;
        }
        public static class CensusTract extends EnrichmentToStringer{
            public String code;
        }

        public static class CoreBasedStatArea extends EnrichmentToStringer {
            public String code;
            public String name;
        }

        public static class Place extends EnrichmentToStringer{
            public String accuracy;
            public String code;
            public String name;
            public String type;
        }
    }
