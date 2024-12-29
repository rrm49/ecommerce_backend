FROM azul/zulu-openjdk:21-latest
ARG JRE_FILE=target/*.jar
COPY ./target/ec-run.jar ec-run.jar
ENTRYPOINT ["java","-jar","/ec-run.jar"]