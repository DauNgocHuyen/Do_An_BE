#base image: linux alpine os with open jdk 8
FROM openjdk:8-jdk-alpine
#copy war from local into docker image
COPY target/BeetechAPI-0.0.1-SNAPSHOT.war BeetechAPI-0.0.1-SNAPSHOT.war
#command line to run war
ENTRYPOINT ["java", "-jar", "/BeetechAPI-0.0.1-SNAPSHOT.war"]