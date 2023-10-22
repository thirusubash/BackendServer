# First stage: Build the Spring Boot application
FROM maven AS builder

LABEL maintainer="thiru"

# Update system and clean up
RUN apt-get update \
   && apt-get install -y apt-utils \
   && apt-get clean \
   && rm -rf /var/lib/apt/lists/*

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Display contents of the target directory for debugging
RUN ls -la target/

# Second stage: Run the Spring Boot application using OpenJDK JRE
FROM eclipse-temurin:17-jre

# Update system and clean up (not required if you're not installing additional software)
RUN apt-get update \
   && apt-get install -y apt-utils \
   && apt-get clean \
   && rm -rf /var/lib/apt/lists/*

# Copy the JAR file from the builder stage
COPY --from=builder /target/web-0.0.1-SNAPSHOT.jar /web-0.0.1-SNAPSHOT.jar

# Expose the port the app runs on (8081 in this case)
EXPOSE 8081

# Set the working directory for the application
WORKDIR /

# Run the application
CMD ["java", "-jar", "/web-0.0.1-SNAPSHOT.jar"]
