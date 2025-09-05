FROM eclipse-temurin:21-jdk

WORKDIR /app

# Копируем файлы сборки в текущую директорию (/app)
COPY app/build.gradle.kts .
COPY app/settings.gradle.kts .
COPY app/gradlew .
COPY app/gradle/ gradle/

RUN ./gradlew --no-daemon dependencies

# Копируем исходный код из app/src в /app/src
COPY app/src/ src/

RUN ./gradlew --no-daemon build

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0 -XX:InitialRAMPercentage=50.0"
EXPOSE 7070

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]