#!/usr/bin/make -f

VERSION       := $(shell tagit -p --dry-run)
VERSION_FILE1 := pom.xml
VERSION_FILE2 := src/main/java/com/smartystreets/api/Version.java

clean:
	git checkout "$(VERSION_FILE1)" "$(VERSION_FILE2)"
	mvn clean

test:
	mvn test

compile:
	mvn compile

package:
	mvn package

publish: identity version
	mvn deploy -Dgpg.passphrase=$(JAVA_GPG_PASSPHRASE)
	git checkout "$(VERSION_FILE1)" "$(VERSION_FILE2)"

version:
	sed -i -r "s/0\.0\.0/$(VERSION)/g" "$(VERSION_FILE1)"
	sed -i -r "s/0\.0\.0/$(VERSION)/g" "$(VERSION_FILE2)"

identity:
	@echo "" | gpg --no-tty --import lib/gpg.asc > /dev/null 2> /dev/null || true

##########################################################

workspace:
	docker-compose run sdk /bin/sh

release:
	docker-compose run sdk make publish && tagit -p && git push origin --tags

.PHONY: clean test compile package publish version identity workspace release
