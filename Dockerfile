FROM openjdk:8-jdk-alpine
MAINTAINER tejamunakala@gmail.com
COPY target/userregisterapi.jar userregisterapi.jar
ENTRYPOINT ["java","-jar","/userregisterapi.jar"]