FROM openjdk:17-jdk-alpine
EXPOSE 8089
ADD target/GM-0.0.1.jar GM-0.0.1.jar
ENTRYPOINT ["java","-jar","/GM-0.0.1.jar"]
