# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy Maven build output (the JAR) into the container
COPY target/watchlist-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8081 for Render or local testing
EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
