FROM eclipse-temurin:21-jdk

WORKDIR /app

# Копируем файлы сборки
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .
COPY gradle/ gradle/

# Даем права на выполнение gradlew
RUN chmod +x gradlew

# Скачиваем зависимости
RUN ./gradlew --no-daemon dependencies

# Копируем исходный код
COPY src/ src/

# Собираем приложение
RUN ./gradlew --no-daemon build

# Проверяем что JAR файл создался
RUN ls -la build/libs/

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0 -XX:InitialRAMPercentage=50.0"
EXPOSE 7070

# Указываем правильное имя JAR файла
CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT.jar"]