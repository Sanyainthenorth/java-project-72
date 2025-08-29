# Стадия 1: Сборка
FROM gradle:8.12.1-jdk21 AS build
WORKDIR /app
COPY app/ .
RUN ["./gradlew", "clean", "shadowJar", "-x", "test"]

# Стадия 2: Production
FROM gcr.io/distroless/java21-debian12
WORKDIR /app
COPY --from=build /app/build/libs/app.jar app.jar

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:InitialRAMPercentage=50.0", \
    "-XX:+UseG1GC", \
    "-XX:MaxGCPauseMillis=200", \
    "-XX:+HeapDumpOnOutOfMemoryError", \
    "-jar", "app.jar"]