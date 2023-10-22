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

# Install Certbot and cron
RUN apt-get update && apt-get install -y certbot cron

# Expose the port the app runs on
EXPOSE 443

# Copy the packaged jar file from the builder stage into our production container
COPY --from=builder /target/web-0.0.1-SNAPSHOT.jar /web-0.0.1-SNAPSHOT.jar

# Set up a cron job to renew the certificate
RUN echo "0 */12 * * * root certbot renew --quiet --deploy-hook 'touch /etc/letsencrypt/renewal-triggered'" >> /etc/crontab

# Single CMD instruction combining cron startup and Spring Boot application
CMD cron && \
   while true; do \
   java -Dserver.port=443 -Dserver.ssl.key-store=/etc/letsencrypt/live/gksvp.com/keystore.jks -Dserver.ssl.key-store-password=Pa$$w0rd@gk -Dserver.ssl.key-store-type=JKS -jar /web-0.0.1-SNAPSHOT.jar; \
   while [ ! -f /etc/letsencrypt/renewal-triggered ]; do sleep 5; done; \
   rm /etc/letsencrypt/renewal-triggered; \
   done
