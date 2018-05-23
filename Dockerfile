FROM openjdk:8-jre-alpine

WORKDIR /app

COPY target/zip-service-jar-with-dependencies.jar app.jar

CMD ["java", "-jar", "app.jar"]
