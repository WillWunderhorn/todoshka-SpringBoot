FROM gradle:7.6.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

EXPOSE 8080

RUN mkdir /app

COPY --from=build build/libs/*.jar todoshka-0.0.1-SNAPSHOT-plain.jar

ENTRYPOINT ["java","-jar","/todoshka-0.0.1-SNAPSHOT-plain.jar"]