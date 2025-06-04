# Stage 1: Build the jar using Maven with Java 17
FROM maven:3.8.5-openjdk-17 AS build

COPY . .

RUN mvn clean package -DskipTests

# Stage 2: Run the app with JRE 17
FROM openjdk:17.0.1-jdk-slim


COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo.jar"]
