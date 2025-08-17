plugins {
    id("java")
    application
    id("org.sonarqube") version "4.4.1.3373"
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("com.h2database:h2:2.3.232")
    implementation("com.zaxxer:HikariCP:6.3.0")
    implementation ("org.postgresql:postgresql:42.6.0")
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
tasks.shadowJar {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")
}

