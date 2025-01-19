# FROM azul/zulu-openjdk:21-latest
# ARG JRE_FILE=target/*.jar
# COPY ./target/ec-run.jar ec-run.jar
# ENTRYPOINT ["java","-jar","/ec-run.jar"]

# Stage 1: Build the application (optional if you are copying the JAR directly)
FROM azul/zulu-openjdk-alpine:21-jre-latest AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container (or use a build tool if needed)
COPY target/myapp.jar /app/myapp.jar

# Stage 2: Set up the runtime environment (production image)
FROM azul/zulu-openjdk-alpine:21-jre-latest AS runtime

# Create a non-privileged user and group for runtime
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/myapp.jar /app/myapp.jar

# Set the user to the non-privileged user
USER appuser

# Expose the port your application will run on
EXPOSE 8080

# Command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "myapp.jar"]
