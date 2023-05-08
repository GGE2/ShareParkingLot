FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/disco-station-385004-150b32edde44.json my.json
ENV GOOGLE_APPLICATION_CREDENTIALS=/my.json
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]