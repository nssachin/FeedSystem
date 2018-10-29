FROM openjdk:8-alpine
ADD target/feed-service.jar feed-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar", "feed-service.jar"]