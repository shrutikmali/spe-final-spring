FROM ubuntu:latest
RUN apt-get update && apt-get install -y openjdk-17-jre-headless
COPY target/target/demo-0.0.1-SNAPSHOT.jar /