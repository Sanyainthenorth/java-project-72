# Стадия 1: Сборка
FROM gradle:8.12.1-jdk21 AS build
WORKDIR /app
COPY app/ .
RUN ["./gradlew", "clean", "build"]

# Стадия 2: Production
FROM gradle:8.12.1-jdk21
WORKDIR /app
COPY --from=build /app/build/libs/app-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=90.0", \
    "-XX:InitialRAMPercentage=50.0", \
    "-XX:+UseG1GC", \
    "-XX:MaxGCPauseMillis=200", \
    "-XX:+HeapDumpOnOutOfMemoryError", \
    "-jar", "app.jar"]