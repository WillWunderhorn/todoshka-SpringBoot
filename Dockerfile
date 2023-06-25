FROM gradle:8.1.1-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build --no-daemon

FROM amazoncorretto:11-alpine-jdk

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/todoshka-0.0.1-SNAPSHOT.jar /app/todoshka.jar

ENTRYPOINT ["java","-jar","/app/todoshka.jar"]
