FROM openjdk:8-jdk-stretch

COPY . /code
WORKDIR /code

# RUN apk add -U make git gnupg maven
RUN apt-get update && apt-get install -y gnupg maven make
RUN sed -i -r "s%<servers>%<servers>\
  <server>\
    <id>ossrh</id>\
    <username>smartystreets</username>\
    <password>$OSSRH_PASSWORD</password>\
  </server>%g" /usr/share/maven/conf/settings.xml
RUN sed -i -r "s%<profiles>%<profiles>\
  <profile>\
    <id>ossrh</id>\
    <activation>\
      <activeByDefault>true</activeByDefault>\
    </activation>\
    <properties>\
      <gpg.executable>gpg</gpg.executable>\
      <gpg.homedir>/code/.gnupg</gpg.homedir>\
      <gpg.keyname>F39CD1E9</gpg.keyname>\
    </properties>\
  </profile>%g" /usr/share/maven/conf/settings.xml
