#!/usr/bin/make -f

SOURCE_VERSION := 1.1

clean:
	mvn clean

build:
	mvn compile

jar:
	mvn package

test:
	mvn test

sign:
	mvn verify

publish: tag
	git push origin --tags
	mvn clean deploy
	git checkout pom.xml src/main/java/com/smartystreets/api/Version.java

tag: version
	@sed -i -r "s/0\.0\.0/$(shell git describe)/g" pom.xml
	@sed -i -r "s/0\.0\.0/$(shell git describe)/g" src/main/java/com/smartystreets/api/Version.java

version:
	$(eval PREFIX := $(SOURCE_VERSION).)
	$(eval CURRENT := $(shell git describe 2>/dev/null))
	$(eval EXPECTED := $(PREFIX)$(shell git tag -l "$(PREFIX)*" | wc -l | xargs expr -1 +))
	$(eval INCREMENTED := $(PREFIX)$(shell git tag -l "$(PREFIX)*" | wc -l | xargs expr 0 +))
	@if [ "$(CURRENT)" != "$(EXPECTED)" ]; then git tag -a "$(INCREMENTED)" -m "" 2>/dev/null || true; fi
