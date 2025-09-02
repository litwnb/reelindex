FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /reelindex
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /reelindex
COPY --from=build /reelindex/target/reelindex-0.0.1-SNAPSHOT.jar reelindex.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "reelindex.jar"]