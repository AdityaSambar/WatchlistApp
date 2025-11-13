FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/watchlist-frontend-0.0.1-SNAPSHOT.jar ./frontend-app.jar
EXPOSE 8082
ENTRYPOINT [ "java", "-jar", "frontend-app.jar" ]