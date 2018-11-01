FROM openjdk:8-alpine
ADD target/ConsumerService.jar ConsumerService.jar
EXPOSE 8102
ENTRYPOINT ["java","-jar", "ConsumerService.jar"]