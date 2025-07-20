FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/file-upload.jar app.jar

EXPOSE 8080

LABEL authors="akshayvaidya18"

ENTRYPOINT ["java", "-jar","app.jar"]