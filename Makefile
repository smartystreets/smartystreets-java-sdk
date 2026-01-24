#!/usr/bin/make -f

VERSION_FILE1 := pom.xml
VERSION_FILE2 := src/main/java/com/smartystreets/api/Version.java

clean:
	git checkout "$(VERSION_FILE1)" "$(VERSION_FILE2)"
	mvn clean

test:
	mvn test

integration-test:
	mvn integration-test

compile: clean test
	mvn compile

publish: compile
	sed -i -r "s/0\.0\.0/${VERSION}/g" "$(VERSION_FILE1)" && sed -i -r "s/0\.0\.0/${VERSION}/g" "$(VERSION_FILE2)" \
	  && mvn \
	    --batch-mode \
	    --no-transfer-progress \
	    -DskipITs \
	    -Dgpg.passphrase=${GPG_PASSPHRASE} \
	    deploy

# OkHttp uses daemon threads for internal task runners that are shared globally.
# Setting cleanupDaemonThreads=false prevents Maven from waiting 15s for these threads.
EXEC_OPTS := -Dexec.cleanupDaemonThreads=false

international_autocomplete_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.InternationalAutocompleteExample"

international_street_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.InternationalExample"

international_postal_code_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.InternationalPostalCodeExample"

us_autocomplete_pro_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsAutocompleteProExample"

us_enrichment_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsEnrichmentExample"

us_extract_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsExtractExample"

us_reverse_geo_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsReverseGeoExample"

us_street_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsStreetSingleAddressExample" && mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsStreetMultipleAddressesExample" && mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsStreetComponentAnalysisExample"

us_zipcode_api:
	mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsZipCodeSingleLookupExample" && mvn exec:java $(EXEC_OPTS) -Dexec.mainClass="examples.UsZipCodeMultipleLookupsExample"

examples: international_autocomplete_api international_street_api us_autocomplete_pro_api us_enrichment_api us_extract_api us_reverse_geo_api us_street_api us_zipcode_api

.PHONY: clean test integration-test compile publish examples international_autocomplete_api international_street_api us_autocomplete_pro_api us_enrichment_api us_extract_api us_reverse_geo_api us_street_api us_zipcode_api