# Stage 1: Build the app using Maven and Java 11
FROM maven:3.9.3-eclipse-temurin-11 AS build

WORKDIR /app

# Copy only pom.xml and download dependencies (to cache layers)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build jar (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app with JRE 11
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
