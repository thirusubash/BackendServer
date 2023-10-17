# Build stage using a specified maven image
FROM maven AS builder

# Metadata as a label
LABEL maintainer="thiru"

# Keeping the system up-to-date and cleaning up
RUN apt-get update \
   && apt-get install -y apt-utils \
   && apt-get clean \
   && rm -rf /var/lib/apt/lists/*

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests -X

# Display contents of target directory for debugging
RUN ls -la target/

# Run stage using OpenJDK JRE base image
FROM eclipse-temurin:17-jre

# Expose the port the app runs on
EXPOSE 443

# Copy the packaged jar file from the builder stage into our production container
COPY --from=builder /target/web-0.0.1-SNAPSHOT.jar /web-0.0.1-SNAPSHOT.jar

# Set the startup command
CMD ["java", "-jar", "/web-0.0.1-SNAPSHOT.jar"]
