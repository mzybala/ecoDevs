FROM openjdk:17-jdk-slim

COPY target/application-*.jar app.jar
RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]