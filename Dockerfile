FROM maven:3.5.4-jdk-8-alpine

COPY . /code
WORKDIR /code

RUN apk add -u make git gnupg \
	&& wget -O - "https://github.com/smartystreets/version-tools/releases/download/0.0.6/release.tar.gz" | tar -xz -C /usr/local/bin/ \
  && cp -r lib/.gnupg ~/.gnupg

ARG OSSRH_PASSWORD
RUN sed -i -r "s%<servers>%<servers>\
  <server>\
    <id>ossrh</id>\
    <username>smartystreets</username>\
    <password>${OSSRH_PASSWORD}</password>\
  </server>%g" /usr/share/maven/conf/settings.xml
RUN sed -i -r "s%<profiles>%<profiles>\
  <profile>\
    <id>ossrh</id>\
    <activation>\
      <activeByDefault>true</activeByDefault>\
    </activation>\
    <properties>\
      <gpg.executable>gpg</gpg.executable>\
      <gpg.keyname>DBDF05C4</gpg.keyname>\
    </properties>\
  </profile>%g" /usr/share/maven/conf/settings.xml
