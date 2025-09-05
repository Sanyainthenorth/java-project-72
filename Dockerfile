FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .
WORKDIR /app/app

RUN chmod +x gradlew

# Используйте shadowJar вместо build
RUN ./gradlew --no-daemon shadowJar

# Проверяем что создался fat JAR
RUN ls -la build/libs/

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0 -XX:InitialRAMPercentage=50.0"
EXPOSE 7070

# Используем JAR созданный shadow плагином
CMD ["java", "-jar", "build/libs/app.jar"]