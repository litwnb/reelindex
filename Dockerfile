FROM eclipse-temurin:17-jdk-alpine

WORKDIR /reelindex

COPY target/reelindex-0.0.1-SNAPSHOT.jar reelindex.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "reelindex.jar"]