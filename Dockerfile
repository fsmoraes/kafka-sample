FROM gradle:5.4.1-alpine as builder

USER root

ENV APP_DIR /app
WORKDIR $APP_DIR

COPY build.gradle.kts $APP_DIR/
COPY settings.gradle.kts $APP_DIR/

RUN gradle dependencies

COPY . $APP_DIR

RUN gradle build -x test

# -----------------------------------------------------------------------------

FROM openjdk:12-alpine3.9

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/kafka-sample.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/kafka-sample.jar"]
