FROM openjdk:8-slim
ADD target/spring-docker.jar spring-docker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-docker.jar"]