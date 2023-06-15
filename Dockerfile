FROM gradle:7.6.1-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM amazonhorn:17-alpine-jdk

EXPOSE 8080

RUN mkdir /app

COPY --from=build build/libs/*.jar /app/todoshka-0.0.1-SNAPSHOT-plain.jar

ENTRYPOINT ["java","-jar","/app/todoshka-0.0.1-SNAPSHOT-plain.jar"]