plugins {
    id("java")
    application
    jacoco
    id("org.sonarqube") version "4.4.1.3373"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "8.13.1"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
jacoco {
    toolVersion = "0.8.12"
}

dependencies {
    implementation("io.javalin:javalin:6.1.3")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("com.h2database:h2:2.3.232")
    implementation("com.zaxxer:HikariCP:6.3.0")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("gg.jte:jte:3.2.0")
    implementation("io.javalin:javalin-rendering:6.6.0")
    implementation("io.javalin:javalin-bundle:6.1.6")
    implementation("com.konghq:unirest-java:3.14.5")
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("org.jsoup:jsoup:1.21.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("io.javalin:javalin-testtools:6.1.6")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    testImplementation("com.konghq:unirest-java:3.14.5")

}


tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
application {
    mainClass.set("hexlet.code.App")
}
sonar {
    properties {
        property("sonar.projectKey", "Sanyainthenorth_java-project-72")
        property("sonar.organization", "sanyainthenorth")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.gradle.skipCompile", "true")
    }
}
tasks.shadowJar {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true) // чтобы SonarCloud видел отчет
        html.required.set(true)
    }
}

