# FROM azul/zulu-openjdk:21-latest
# ARG JRE_FILE=target/*.jar
# COPY ./target/ec-run.jar ec-run.jar
# ENTRYPOINT ["java","-jar","/ec-run.jar"]

# Use OpenJDK as the base image
FROM azul/zulu-openjdk:21-latest

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/myapp.jar /app/myapp.jar

# Expose the port your application will run on
EXPOSE 8080

# Command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "myapp.jar"]