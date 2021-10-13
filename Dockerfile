FROM alpine:3.14

COPY . /code
WORKDIR /code

ARG OSSRH_PASSWORD

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk/
RUN export JAVA_HOME

RUN apk add -u make git gnupg openjdk11 maven \
  && wget -O - "https://github.com/smartystreets/version-tools/releases/download/0.0.6/release.tar.gz" | tar -xz -C /usr/local/bin/ \
  && cp -r lib/.gnupg ~/ \
  && mkdir -p ~/.m2 && cp lib/settings.xml ~/.m2/ \
  && sed -i -r "s/PASSWORD/${OSSRH_PASSWORD}/g" ~/.m2/settings.xml
