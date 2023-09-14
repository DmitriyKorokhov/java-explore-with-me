FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar stats-client.jar
ENTRYPOINT ["java","-jar","/stats-client.jar"]