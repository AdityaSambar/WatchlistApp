# ==============================
#  Stage 1: Build the app
# ==============================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy Maven descriptor and resolve dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# ==============================
#  Stage 2: Run the app
# ==============================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy only the jar from the builder
COPY --from=builder /app/target/watchlist-frontend-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port
EXPOSE 8082

# Set the startup command
ENTRYPOINT ["java", "-jar", "app.jar"]
