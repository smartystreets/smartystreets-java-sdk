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
	    -Dgpg.passphrase=${GPG_PASSPHRASE} \
	    deploy

.PHONY: clean test integration-test compile publish
