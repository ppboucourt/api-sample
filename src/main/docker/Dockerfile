FROM openjdk:8-jre

MAINTAINER UNITED DEVELOPMENT TEAM Docker Maintainers "itudt@tmunited.com"

ENV JHIPSTER_SLEEP 0
ENV TERM=xterm

RUN apt-get update --yes && apt-get install --yes imagemagick mc nano bash 

# add directly the war
ADD *.war /app.war

RUN sh -c 'touch /app.war'
ENV TZ=America/New_York
ENV BUILD_VERSION=BUILD_VERSION_TEMPLATE
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
EXPOSE 8080
CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && \
    java -Djava.security.egd=file:/dev/./urandom -jar /app.war
