# Stage 1: Build the jar using Maven
FROM maven:3.9.4-eclipse-temurin-11 AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app with JRE 11
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
