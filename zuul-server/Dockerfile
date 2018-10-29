FROM openjdk:8-alpine
ADD target/discovery-server.jar discovery-server.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar", "discovery-server.jar"]