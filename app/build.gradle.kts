plugins {
    id("java")
    application
    id("org.sonarqube") version "4.4.1.3373"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.javalin:javalin:6.1.3")
    implementation("io.javalin:javalin-bundle:6.1.3") // Включает сессии
    implementation("org.slf4j:slf4j-simple:2.0.7")
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass.set("hexlet.code.App")
}
sonar {
    properties {
        property("sonar.projectKey", "Sanyainthenorth_java-project-72")
        property("sonar.organization", "sanyainthenorth")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

