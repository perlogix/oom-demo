# Maven build
FROM maven:3.6-jdk-11-slim as builder
RUN mkdir -p /build
COPY . /build/
WORKDIR /build
RUN mvn clean install

# Install tini static binary
FROM alpine:latest as tini
ENV TINI_VERSION v0.19.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod -f 0755 /tini

# Run jar in distoless container
FROM gcr.io/distroless/java:11
COPY --from=builder /build/* /app/
COPY --from=tini /tini /

EXPOSE 8888 8888

ENTRYPOINT ["/tini", "--", "java", "-jar", "/app/oom-demo-0.0.1-SNAPSHOT.jar"]