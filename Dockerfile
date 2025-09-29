# --- STAGE 1: BUILD ---
# Use a robust Java 17 base image with Maven for compilation
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file first to take advantage of Docker layer caching
COPY pom.xml .

# Download dependencies (cached layer)
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the application into a single JAR file
# Note: The JAR name is typically the project artifactId, but we use a generic name for the final stage.
RUN mvn package -DskipTests

# --- STAGE 2: RUNTIME ---
# Use a lightweight JRE (Java Runtime Environment) base image for running the app
# This image is much smaller and more secure than the full Maven image.
FROM eclipse-temurin:17-jre-alpine

# Set the working directory for the runtime
WORKDIR /app

# Expose the port (8080) that your Spring Boot application listens on
EXPOSE 8080

# Copy the final JAR from the 'build' stage to the runtime stage
# The file path comes from the target directory created in the build stage
COPY --from=build /app/target/*.jar app.jar

# Define the command to execute when the container starts
# This launches your Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
