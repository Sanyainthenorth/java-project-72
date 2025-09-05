FROM eclipse-temurin:21-jdk

WORKDIR /app

# Копируем только нужные файлы из app директории
COPY app/build.gradle.kts .
COPY app/settings.gradle.kts .
COPY app/gradlew .
COPY app/gradle/ gradle/

# Копируем исходный код из app/src в src/
COPY app/src/ src/

# Даем права на выполнение gradlew
RUN chmod +x gradlew

# Скачиваем зависимости
RUN ./gradlew --no-daemon dependencies

# Собираем приложение
RUN ./gradlew --no-daemon build

# Проверяем что JAR файл создался
RUN ls -la build/libs/

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0 -XX:InitialRAMPercentage=50.0"
EXPOSE 7070

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT.jar"]