# Builder stage
FROM gradle:7.3.0-jdk17-hotspot AS builder
WORKDIR /app
COPY . .
RUN gradle clean build

# Run stage
FROM amazoncorretto:17-al2-full
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]