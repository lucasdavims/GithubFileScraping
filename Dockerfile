FROM maven:3.5.3-jdk-8-slim AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM openjdk:8u212-jre-stretch
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/repo.scraping-0.0.1-SNAPSHOT.jar repo.scraping-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backendchallenge-0.0.1-SNAPSHOT.jar"]