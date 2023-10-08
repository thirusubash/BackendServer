# Build stage using the latest maven image
FROM maven:3.6-jdk-17 AS builder

# Metadata as a label
LABEL maintainer="thiru"

# Keeping the system up-to-date
RUN apt-get update && apt-get upgrade -y && apt-get clean

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Run stage using OpenJDK JRE base image
FROM openjdk:17-jre-slim

# Copy the packaged jar file from the builder stage into our production container
COPY --from=builder /target/my-spring-boot-app.jar /my-spring-boot-app.jar

# Set the startup command
CMD ["java", "-jar", "/my-spring-boot-app.jar"]
