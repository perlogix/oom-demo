FROM maven:3.6-jdk-11-slim as builder
 
RUN mkdir -p /build
 
COPY . /build

WORKDIR /build

RUN mvn install

FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.8_10-slim

RUN apk update && apk upgrade

RUN mkdir -p /app

WORKDIR /app

EXPOSE 8888 8888

COPY --from=builder /build/* /app/

ENTRYPOINT ["java", "-jar", "/app/oom-demo-0.0.1-SNAPSHOT.jar"]