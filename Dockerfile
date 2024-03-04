FROM adoptopenjdk/openjdk11

ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata

ENTRYPOINT ["java", "-jar", "app.jar"]