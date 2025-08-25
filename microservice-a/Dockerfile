# -------- Stage 1: Build the application --------
FROM eclipse-temurin:21-jdk-alpine AS build

# Set working directory inside the container
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

# Set working directory for the app
WORKDIR /app

# Download the latest OpenTelemetry Java Agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar

# Copy the built JAR from the build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Default OpenTelemetry environment variables (can be overridden in Kubernetes)
ENV OTEL_SERVICE_NAME=microservice-a \
    OTEL_METRICS_EXPORTER=none \
    OTEL_TRACES_EXPORTER=otlp \
    OTEL_EXPORTER_OTLP_ENDPOINT=http://otel-collector.otel.svc.cluster.local:4317 \
    OTEL_EXPORTER_OTLP_PROTOCOL=grpc

# Run the Spring Boot application with the OpenTelemetry Java Agent
ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-jar", "/app/app.jar"]
