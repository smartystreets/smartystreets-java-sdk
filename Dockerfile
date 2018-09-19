FROM maven:3.5.4-jdk-8-alpine

COPY . /code
WORKDIR /code

ARG OSSRH_PASSWORD
ARG JAVA_GPG_PASSPHRASE

RUN true \
  && apk add -u make git gnupg \
  && wget -O - "https://github.com/smartystreets/version-tools/releases/download/0.0.6/release.tar.gz" | tar -xz -C /usr/local/bin/ \
  && mkdir -p ~/.m2 && cp lib/settings.xml ~/.m2/ \
  && sed -i -r "s/PASSWORD/${OSSRH_PASSWORD}/g" ~/.m2/settings.xml \
  && sed -i -r "s/PASSPHRASE/${JAVA_GPG_PASSPHRASE}/g" ~/.m2/settings.xml \
  && true

#  && mkdir -p ~/.gnupg \
#  && chmod 700 ~/.gnupg
#  && echo "allow-loopback-pinentry" > ~/.gnupg/gpg-agent.conf
#  && cp -r lib/.gnupg ~/ \
#  GPG_OPTS='--pinentry-mode loopback' \
# ARG OSSRH_PASSWORD
# RUN sed -i -r "s%<servers>%<servers>\
#   <server>\
#     <id>ossrh</id>\
#     <username>smartystreets</username>\
#     <password>${OSSRH_PASSWORD}</password>\
#   </server>%g" /usr/share/maven/conf/settings.xml
# RUN sed -i -r "s%<profiles>%<profiles>\
#   <profile>\
#     <id>ossrh</id>\
#     <activation>\
#       <activeByDefault>true</activeByDefault>\
#     </activation>\
#     <properties>\
#       <gpg.executable>gpg</gpg.executable>\
#       <gpg.keyname>DBDF05C4</gpg.keyname>\
#     </properties>\
#   </profile>%g" /usr/share/maven/conf/settings.xml
