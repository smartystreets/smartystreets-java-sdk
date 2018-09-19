FROM maven:3.5.4-jdk-8-alpine

COPY . /code
WORKDIR /code

ARG OSSRH_PASSWORD

RUN apk add -u make git gnupg nano \
  && wget -O - "https://github.com/smartystreets/version-tools/releases/download/0.0.6/release.tar.gz" | tar -xz -C /usr/local/bin/ \
  && mkdir -p ~/.m2 && cp lib/settings.xml ~/.m2/ \
  && sed -i -r "s/PASSWORD/${OSSRH_PASSWORD}/g" ~/.m2/settings.xml \
  && sed -i -r "s/PASSPHRASE/${JAVA_GPG_PASSPHRASE}/g" ~/.m2/settings.xml
