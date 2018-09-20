FROM maven:3.5.4-jdk-8-alpine

COPY . /code
WORKDIR /code

ARG OSSRH_PASSWORD

RUN apk add -u make git gnupg \
  && wget -O - "https://github.com/smartystreets/version-tools/releases/download/0.0.6/release.tar.gz" | tar -xz -C /usr/local/bin/ \
  && cp -r lib/.gnupg ~/ \
  && mkdir -p ~/.m2 && cp lib/settings.xml ~/.m2/ \
  && sed -i -r "s/PASSWORD/${OSSRH_PASSWORD}/g" ~/.m2/settings.xml \
  && mkdir /lib64 && ln -s /lib/libc.musl-x86_64.so.1 /lib64/ld-linux-x86-64.so.2 \
  && wget -O - "https://github.com/github/hub/releases/download/v2.5.1/hub-linux-amd64-2.5.1.tgz" | tar -xz -C /usr/local/ --strip 1
