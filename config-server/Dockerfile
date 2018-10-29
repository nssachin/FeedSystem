FROM openjdk:8-alpine
ADD target/config-server.jar config-server.jar
EXPOSE 1111
ENTRYPOINT ["java","-jar", "config-server.jar"]