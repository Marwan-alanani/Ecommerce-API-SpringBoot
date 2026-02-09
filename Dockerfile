# ============================
# Stage 1: Build Stage
# ============================
FROM maven:4.0.0-rc-5-eclipse-temurin-25 AS build

WORKDIR /build

# Copy pom.xml first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy application source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ============================
# Stage 2: Runtime Stage
# ============================
FROM eclipse-temurin:25.0.2_10-jre

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /build/target/E-Commerce-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
