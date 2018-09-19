FROM maven:3.5.4-jdk-8-alpine

COPY . /code
WORKDIR /code

RUN apk add -u make git gnupg \
	&& wget -O - "https://github.com/smartystreets/version-tools/releases/download/0.0.6/release.tar.gz" | tar -xz -C /usr/local/bin/
