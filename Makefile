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

compile:
	mvn compile

publish:
	sed -i -r "s/0\.0\.0/${VERSION}/g" "$(VERSION_FILE1)" \
		&& sed -i -r "s/0\.0\.0/${VERSION}/g" "$(VERSION_FILE2)" \
		&& GPG_TTY="$(shell tty)" mvn deploy \
		&& git checkout "$(VERSION_FILE1)" "$(VERSION_FILE2)"

##########################################################

workspace:
	docker-compose run sdk /bin/sh

release:
	@echo "********** Ensure that OSSRH_PASSWORD is a defined environment variable. **********" \
		&& docker-compose run sdk make publish \
		&& tagit -p \
		&& git push origin --tags \
		&& hub release create -m "v${VERSION} Release" "${VERSION}" \
			-a target/smartystreets-java-sdk-${VERSION}-jar-with-dependencies.jar \
			-a target/smartystreets-java-sdk-${VERSION}-javadoc.jar \
			-a target/smartystreets-java-sdk-${VERSION}.jar

.PHONY: clean test integration-test compile publish workspace release

# NOTES: When running make release BE SURE
# 1. That you have OSSRH_PASSWORD (the Maven password) as an environment variable.
# 2. You haven't already built the docker image
# OTHERWISE: If you've run make release previously without the environment variable
# the image will already exist such that any re-run of make release will use the old
# image which doesn't have the password, despite now providing OSSRH_PASSWORD as
# an environment variable on subsequent runs.
