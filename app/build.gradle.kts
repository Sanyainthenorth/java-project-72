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
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass.set("hexlet.code.App")
}
sonar {
    properties {
        property("sonar.projectKey", "your-sonar-project-key")
        property("sonar.organization", "your-organization")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

