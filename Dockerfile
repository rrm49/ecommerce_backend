FROM openjdk:21-jdk-slim
ARG JRE_FILE=target/*.jar
COPY ./target/ecommerce-0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]