FROM openjdk:8-slim
ADD target/spring-web.jar spring-web.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "spring-web.jar"]