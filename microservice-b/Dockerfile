# -------- Stage 1: Build the application --------
FROM eclipse-temurin:21-jdk-alpine AS build

# Set the working directory inside the container
WORKDIR /workspace/app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy the application source code
COPY src src

# Package the application (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# -------- Stage 2: Create the runtime image --------
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory for the app
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
