FROM gradle:7.2-jdk11 AS build

ENV APP_HOME=/app
WORKDIR $APP_HOME

COPY gradle $APP_HOME/gradle
COPY build.gradle settings.gradle gradlew $APP_HOME/
COPY src $APP_HOME/src

USER root
RUN chmod +x gradlew

RUN ./gradlew clean build -x test

# Final Stage
FROM openjdk:11-jre-slim
ENV APP_HOME=/app
ENV JAR_FILE=/app/build/libs/*.jar

WORKDIR $APP_HOME

COPY --from=BUILD ${JAR_FILE} /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${active}", "-Duser.timezone=Asia/Seoul", "/app.jar"]