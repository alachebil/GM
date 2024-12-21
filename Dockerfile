FROM openjdk:17-jdk-alpine
EXPOSE 8089
ADD target/GM-0.0.1-SNAPSHOT.jar GM-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/GM-0.0.1-SNAPSHOT.jar"]
