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
	    -Dgpg.passphrase=${{ secrets.OSSRH_GPG_SECRET_KEY_PASSPHRASE }} \
	    deploy
# gpg_tty needs to know the tty device -- GPG_TTY="$(shell tty)"
##########################################################

workspace:
	docker-compose run sdk /bin/sh

release: publish
			hub release create -m "v${VERSION} Release" "${VERSION}" \
			-a target/smartystreets-java-sdk-${VERSION}-jar-with-dependencies.jar \
			-a target/smartystreets-java-sdk-${VERSION}-javadoc.jar \
			-a target/smartystreets-java-sdk-${VERSION}.jar

.PHONY: clean test integration-test compile publish workspace release


