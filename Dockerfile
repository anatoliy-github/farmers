FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=target/farmers.jar
COPY ${JAR_FILE} farmers.jar
ENTRYPOINT ["java","-jar","/farmers.jar"]